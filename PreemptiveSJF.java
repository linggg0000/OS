import java.util.*;

public class PreemptiveSJF extends Algorithm {
    public PreemptiveSJF(List<Process> processList) {
        super(processList);
        System.out.println("construc");
    }

    @Override
    public void runAlgorithm() {
        sort();

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
//                System.out.println("-- " + currentTime + ": " + selected.getProcessName() + ", " + selected.getRemainingTime());


                // if process completed
                if (selected.getRemainingTime() == 0) {
//                    completedProcesses.add(selected.getProcessName());
                    selected.setFinishTime(currentTime);
//                    timeLine.append(String.format("%-9s", currentTime));
                    completed[selectedIndex] = true;
                    totalCompleted++;
//                    System.out.println("completed " + currentTime + ": " + selected.getProcessName() + ", " + selectedIndex + ", " + completed[selectedIndex]);
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
//                    System.out.println("check " + currentTime + ": " + p.getProcessName() + ", " + p.getRemainingTime() + ", " + completed[i]);
                    minTime = p.getRemainingTime(); // new job time
//                    if(selected!=null)
//                        System.out.println("selected: " + selected.getProcessName());
//                    System.out.println("p: " + p.getProcessName());
                    if (p != selected) // new process selected
                    {
//                        if(selected != null)
//                            System.out.println("ori: " + selected.getProcessName());
                        selected = p; // new selected process index
                        selectedIndex = i;
                        changed = true; // process changed
                    }

                }
            }

            // a new process is selected
            if (changed) {
//                System.out.println("chged: " + selected.getProcessName());
                completedProcesses.add(selected.getProcessName());
                timeLine.append(String.format("%-9s", currentTime));
            }

            currentTime++;
            //p0, p1, p3, p4, p0, p2, p5
            //0 1 5 8 9 14 20 27
        }

        timeLine.append(String.format("%-9s", currentTime));
//        System.out.println("print");
        printGantt();
//        System.out.println("end");
    }

    public void sort() {
        // Print unsorted processes
        System.out.println("Unsorted: "); // flw rr************
        for (Process p : processList) {
            System.out.print(p.getProcessName() + ", ");
        }
        System.out.println("\n\n");

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

        // Print sorted processes
        System.out.println("Sorted: ");
        for (Process process : processList) {
            System.out.print(process.getProcessName() + ", ");
        }
        System.out.println("\n\n");
    }

    public void printGantt() {
        System.out.println("entered");
        StringBuilder ganttTop = new StringBuilder();
        StringBuilder ganttMiddle = new StringBuilder();
        StringBuilder ganttBottom = new StringBuilder();

        for (String process : completedProcesses) {
            ganttTop.append("+--------");
            ganttMiddle.append("|   ").append(process).append("   ");
            ganttBottom.append("+--------");
        }

        // Closing lines
        ganttTop.append("+");
        ganttMiddle.append("|");
        ganttBottom.append("+");

        System.out.println("Gantt Chart:");
        System.out.println(ganttTop);
        System.out.println(ganttMiddle);
        System.out.println(ganttBottom);
        System.out.println(timeLine);   // Print timeline
        System.out.println("\n\n");
    }
}

//    public int partition(int l, int h)
//    {
//        int pivot = h/2; // take middle element as pivot
//        int i = l;
//        int j = h;
////        List<Process> p = new ArrayList<Process>(); // create a copy of processList
////        p.addAll(processList);
//        List<Process> p = processList;
//
//        while(i<j)
//        {
//            while(p.get(i).getArrivalTime() < p.get(pivot).getArrivalTime())
//            {
//                i++;
//            }
//
//            while(p.get(j).getArrivalTime() > p.get(pivot).getArrivalTime())
//            {
//                j--;
//            }
//
//            if(i<j)
//            {
//                Process temp = p.get(i);
//                p.set(i, p.get(j));
//                p.set(j, temp);
//            }
//        }
//        Process temp = p.get(j);
//
//        return j;
//    }
//}
