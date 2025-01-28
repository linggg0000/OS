import java.util.*;

public class RoundRobin extends Algorithm {
    private int timeQuantum;
    
    // Constructor
    public RoundRobin (List<Process> processList){
        super(processList); // initialise with unsorted processList first
        this.timeQuantum = 3;   // predefined to 3
    }
    
    @Override
    public void runAlgorithm(){
        
        int numberOfProcesses = processList.size();
        
        // Print unsorted processes
        System.out.println("Unsorted: ");
        for (Process p : processList){
            System.out.print(p.getProcessName() + ", ");
        }
        System.out.println("\n\n");
        
        // Sort processes by arrival time, then priority, then burst time
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = i + 1; j < numberOfProcesses; j++) {
                Process p1 = processList.get(i);
                Process p2 = processList.get(j);
                
                if (p1.getArrivalTime() > p2.getArrivalTime() || // FCFS first
                    (p1.getArrivalTime() == p2.getArrivalTime() && p1.getPriority() > p2.getPriority()) || // Lower priority number first
                    (p1.getArrivalTime() == p2.getArrivalTime() && p1.getPriority() == p2.getPriority() && p1.getBurstTime() > p2.getBurstTime())) { // Smaller burst time first
                    
                    // Swap processes
                    processList.set(i, p2);
                    processList.set(j, p1);
                }
            }
        }
        
        // Print sorted processes
        System.out.println("Sorted: ");
        for (Process p : processList){
            System.out.print(p.getProcessName() + ", ");
        }
        System.out.println("\n\n");
        
        
        int currentTime = 0;   // Timer
        int index = 0;         // Pointer for sorted processList
        Queue<Process> newArrivals = new LinkedList<>(); // Queue for new arrivals during time quantum        
        
        // End condition: no processes left in processList or readyQueue
        while (index < numberOfProcesses || !readyQueue.isEmpty()){
            
            // Add processes that arrived at or before currentTime to newArrivals queue
            while ( index < numberOfProcesses && processList.get(index).getArrivalTime() <= currentTime) {
                newArrivals.add(processList.get(index));
                index++;
            }
            
            // If no process is ready to execute, increment time and continue checking for new arrivals
            // e.g. if 1st process arrival time is not 0... must increment timer to proceed
            if (readyQueue.isEmpty() && newArrivals.isEmpty()) {
                currentTime++;
                continue;
            }
            
            // Transfer processes in the temporary newArrivals queue into the readyQueue
            while (!newArrivals.isEmpty()) {
                readyQueue.add(newArrivals.poll());
            }
            
            // Process execution : execute the first process in the readyQueue
            // 1. remove it from the ready queue
            Process currentProcess = readyQueue.poll();
            
            // 2. Append it to completedProcesses list & record starting time
            completedProcesses.add(currentProcess.getProcessName());
            timeLine.append(String.format("%-9s", currentTime)); 
            
            // 3. Process execution time = takes the smaller value between time quantum and remaining time
            int executionTime = Math.min(timeQuantum, currentProcess.getRemainingTime());
            
            // 4. Update current time
            currentTime += executionTime;
            
            // 5. Update remaining time
            int newRemainingTime = currentProcess.getRemainingTime() - executionTime;
            currentProcess.setRemainingTime(newRemainingTime);
            
            // Preparing for next execution:
            // 1. Check for processes arriving during this execution
            while (index < numberOfProcesses && processList.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(processList.get(index));
                index++;
            }
            
            // If the process executed is unfinished, add it to the back of the new arrivals
            // The nature of Round Robin that always alternates processes
            // ensures that "hungry" processes automatically get priority to execute
            if (currentProcess.getRemainingTime() > 0) {
                readyQueue.add(currentProcess);
            } else {
                currentProcess.setFinishTime(currentTime);
            }
        }
        
        // Print the Gantt Chart
        printGanttChart(currentTime);   // pass the last process's end time
    }
    
}
