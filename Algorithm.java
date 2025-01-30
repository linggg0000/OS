import java.util.*;

abstract public class Algorithm {
    protected List<Process> processList;
    protected Queue<Process> readyQueue;
    protected List<String> completedProcesses;  // To be printed in Gantt Chart
    protected StringBuilder timeLine;   // Timeline under Gantt Chart
    
    // Constructor
    public Algorithm (List<Process> processList) {
        this.processList = processList;
        this.readyQueue = new LinkedList<>();
        this.completedProcesses = new ArrayList<>();
        this.timeLine = new StringBuilder();
    }
    
    // Abstract method to be overriden by algorithm classes
    abstract public void runAlgorithm();
    
    public void printGanttChart(int lastEndTime) {
        StringBuilder ganttTop = new StringBuilder();
        StringBuilder ganttMiddle = new StringBuilder();
        StringBuilder ganttBottom = new StringBuilder();
        
        for (String process : completedProcesses) {
            ganttTop.append("+--------");
            ganttMiddle.append("|   ").append(process).append("   ");
            ganttBottom.append("+--------");
        }
        
        // Closing lines (right-most edge)
        ganttTop.append("+");
        ganttMiddle.append("|");
        ganttBottom.append("+");
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("                          GANTT CHART                        ");
        System.out.println("═══════════════════════════════════════════════════════\n");
            
        System.out.println(ganttTop);
        System.out.println(ganttMiddle);
        System.out.println(ganttBottom);
        System.out.println(timeLine.append(lastEndTime));   // Print timeline
        System.out.println();
    }
        

    public void printSummaryTable() {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("                         SUMMARY TABLE                       ");
        System.out.println("═══════════════════════════════════════════════════════\n");
            
        System.out.println("+------+--------------+--------------+----------+--------------+-------------------+----------------+");
        System.out.println("|      | Arrival Time |  Burst Time  | Priority |  Finish Time |  Turnaround Time  |  Waiting Time  |");
        System.out.println("+------+--------------+--------------+----------+--------------+-------------------+----------------+");
        
        
        double totalTurnaroundTime = 0 ; // accumulator for total turnaround time
        double totalWaitingTime = 0 ; // accumulator for total waiting time
        int n = 0;  // track process count
        
        for (Process p : processList ){
            // Calculate turnaround time
            int turnaroundTime = p.getFinishTime() - p.getArrivalTime();
            
            // Calculate waiting time
            int waitingTime = turnaroundTime - p.getBurstTime();
            
            System.out.println(String.format(
                "| %-4s | %-12d | %-12d | %-8d | %-12d | %-17d | %-14d |", 
                p.getProcessName(), 
                p.getArrivalTime(), 
                p.getBurstTime(), 
                p.getPriority(), 
                p.getFinishTime(), 
                turnaroundTime, 
                waitingTime
            ));
        
            // Accumulate totals and increment process count
            totalTurnaroundTime += turnaroundTime;  
            totalWaitingTime += waitingTime;   
            n++;    
        }
        
        // Closing line of summary table
        System.out.println("+------+--------------+--------------+----------+--------------+-------------------+----------------+");

        // Print totals and averages below the table
        System.out.println("\n");
        System.out.printf("Total turnaround time: %.2f%n", totalTurnaroundTime);
        System.out.printf("Average turnaround time: %.2f%n", totalTurnaroundTime / n);
        
        System.out.println("\n");
        System.out.printf("Total waiting time: %.2f%n", totalWaitingTime);
        System.out.printf("Average waiting time: %.2f%n", totalWaitingTime / n);
    }
}
