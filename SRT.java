import java.util.*;

// Preemptive SJF
public class SRT extends Algorithm {
    public SRT(List<Process> processList) {
        super(processList);
    }

    @Override
    public void runAlgorithm() {
        sort(); // sort process list

        int currentTime = 0;
        int minTime = Integer.MAX_VALUE;
        int totalCompleted = 0;
        boolean[] completed = new boolean[processList.size()];
        Process selected = null; // store selected process' index
        int selectedIndex = -1;

        // there are incomplete processes
        while (totalCompleted != processList.size()) {

            if (selected != null) {
                int remainingTime = selected.getRemainingTime();
                selected.setRemainingTime(--remainingTime);

                // if process completed
                if (selected.getRemainingTime() == 0) {
                    selected.setFinishTime(currentTime);
                    completed[selectedIndex] = true;
                    totalCompleted++;
                    selected = null;
                    selectedIndex = -1;
                    minTime = Integer.MAX_VALUE; // reset minTime
                }

                if(totalCompleted == processList.size())
                    break;
            }

            boolean changed = false; // reset changed for each turn

            // find process that arrived first and has the shortest job
            for (int i = 0; i < processList.size(); i++) {
                Process p = processList.get(i);

                // if process completed or not arrived yet
                if (completed[i] || p.getArrivalTime() > currentTime)
                    continue;

                // if shorter job
                if (p.getRemainingTime() < minTime) {
                    minTime = p.getRemainingTime(); // new job time

                    if (p != selected) // new process selected
                    {
                        selected = p; // new selected process index
                        selectedIndex = i;
                        changed = true; // process changed
                    }

                }
            }

            // a new process is selected
            if (changed) {
                completedProcesses.add(selected.getProcessName());
                timeLine.append(String.format("%-9s", currentTime));
            }

            currentTime++;
        }

        printGanttChart(currentTime);
        printSummaryTable();
    }

    public void sort() {
        List<Process> p = new ArrayList<>();
        p.addAll(processList);

        // Sort processes by arrival time, then burst time
        for (int i = 0; i < p.size(); i++) {
            for (int j = i + 1; j < p.size(); j++) {
                Process p1 = p.get(i);
                Process p2 = p.get(j);

                if (p1.getArrivalTime() > p2.getArrivalTime() || // FCFS
                        (p1.getArrivalTime() == p2.getArrivalTime() && p1.getBurstTime() > p2.getBurstTime())) // smaller burst time first
                {
                    // Swap processes
                    p.set(i, p2);
                    p.set(j, p1);
                }
            }
        }

        processList = p;
    }
}


