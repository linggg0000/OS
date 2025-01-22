import java.util.*;

public class RoundRobin extends Algorithm {
    private int timeQuantum;
    
    // Constructor
    public RoundRobin (List<Process> processList, int timeQuantum){
        super(processList); // initialise with unsorted processList first
        this.timeQuantum = timeQuantum;
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
        
        // 1. Rearrange the processes according to arrival time and burst time
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = i + 1; j < numberOfProcesses; j++) {
                Process p1 = processList.get(i);
                Process p2 = processList.get(j);
                if (p1.getArrivalTime() > p2.getArrivalTime() || 
                    (p1.getArrivalTime() == p2.getArrivalTime() && p1.getBurstTime() > p2.getBurstTime())) {
                    // Swap processes
                    Process temp = processList.get(i);
                    processList.set(i, processList.get(j));
                    processList.set(j, temp);
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
            // For 1st iteration , it's in charge of adding first process(es) into readyQueue
            // For subsequent iterations, it's to ensure unfinished processes get to reenter readyQueue
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
            if (currentProcess.getRemainingTime() > 0) {
                readyQueue.add(currentProcess);
            } else {
                currentProcess.setFinishTime(currentTime);
            }
        }
        
        // Print Gantt Chart & Time Line after full execution
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
        System.out.println(timeLine.append(currentTime));   // Print timeline
        System.out.println("\n\n");
    }
    
    public static void main (String args[]){
        List<Process> processes = new ArrayList<>();
        
        processes.add(new Process ("P0", 0, 6, 1));
        processes.add(new Process ("P1", 1, 4, 1));
        processes.add(new Process ("P2", 5, 6, 1));
        processes.add(new Process ("P3", 6, 6, 1));
        processes.add(new Process ("P4", 7, 6, 1));
        processes.add(new Process ("P5", 8, 6, 1));
        
  
        int timeQuantum = 3;
        
        RoundRobin rr = new RoundRobin(processes, timeQuantum);
        rr.runAlgorithm();
    }
}
