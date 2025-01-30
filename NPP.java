import java.util.*;

public class NPP extends Algorithm {
    
    // Constructor
    public NPP (List<Process> processList){
        super(processList); // initialise with unsorted processList
    }
    
    @Override
    public void runAlgorithm(){
        
        int numberOfProcesses = processList.size();
        int completed = 0;
        int currentTime = 0;
        
        // 1. Sort processes by arrival time
        for (int i = 0; i < numberOfProcesses - 1; i++) {
            for (int j = i + 1; j < numberOfProcesses; j++) {
                if ((processList.get(i).getArrivalTime() > processList.get(j).getArrivalTime())) {
                    Process temp = processList.get(i);
                    processList.set(i, processList.get(j));
                    processList.set(j, temp);
                }
            }
        }
        
        while (completed < numberOfProcesses) {
            List<Process> availableProcesses = new ArrayList<>();
            
            // 2. Get all processes that have arrived
            for (Process p : processList) {
                if (p.getArrivalTime() <= currentTime && p.getFinishTime() == 0) {
                    availableProcesses.add(p);
                }
            }
            
            // Increment time if no process available
            if (availableProcesses.isEmpty()) {
                currentTime++;
                continue;
            }
            
            // 3. Sort processes by higher priority && lower burst time && smaller process id
            Process selectedProcess = availableProcesses.get(0);
            for (Process p : availableProcesses) {
                int pID = Integer.parseInt(p.getProcessName().substring(1));
                int selectedID = Integer.parseInt(selectedProcess.getProcessName().substring(1));
                
                if (p.getPriority() < selectedProcess.getPriority() ||
                (p.getPriority() == selectedProcess.getPriority() && p.getBurstTime() < selectedProcess.getBurstTime()) ||
                (p.getPriority() == selectedProcess.getPriority() && p.getBurstTime() == selectedProcess.getBurstTime() && pID < selectedID)) {
                    selectedProcess = p;
                }
            }
        
            // Execute selected process
            completedProcesses.add(selectedProcess.getProcessName());
            timeLine.append(String.format("%-9s", currentTime));
            
            // Update time and set process as completed
            currentTime += selectedProcess.getBurstTime();
            selectedProcess.setFinishTime(currentTime);
            completed++;
        }
        
        // Print the Gantt Chart
        printGanttChart(currentTime);   // pass the last process's end time
    
        // Print summary table
        printSummaryTable();
    }
    
}
