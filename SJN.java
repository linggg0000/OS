import java.util.*;

public class SJN extends Algorithm
{
    public SJN (List<Process> processList){
        super(processList); // initialise with unsorted processList first
    }
    
    @Override
    public void runAlgorithm() {
        
        int numberOfProcesses = processList.size();
        
        // 1. Rearrange the processes according to arrival time and burst time
        // will follow input sequence if SAME arrival time and SAME burst time
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = i + 1; j < numberOfProcesses; j++) {
                Process p1 = processList.get(i);
                Process p2 = processList.get(j);
                if (p1.getArrivalTime() > p2.getArrivalTime() || // FCFS
                    (p1.getArrivalTime() == p2.getArrivalTime() && p1.getBurstTime() > p2.getBurstTime())) { // Smaller burst time
                    // Swap processes
                    Process temp = processList.get(i);
                    processList.set(i, processList.get(j));
                    processList.set(j, temp);
                }
            }
        }
        
        int currentTime = 0;
        
        for (Process process : processList)
        {
            completedProcesses.add(process.getProcessName());
            timeLine.append(String.format("%-9s", currentTime));
            currentTime += process.getBurstTime();
            process.setFinishTime(currentTime);
        }
        
        // Print the Gantt Chart
        printGanttChart(currentTime);   // pass the last process's end time
    
        // Print summary table
        printSummaryTable();
    }
    
}
