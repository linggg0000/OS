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
        
        System.out.println("Gantt Chart:");
        System.out.println(ganttTop);
        System.out.println(ganttMiddle);
        System.out.println(ganttBottom);
        System.out.println(timeLine.append(lastEndTime));   // Print timeline
        System.out.println("\n\n");
    }
        
    
}
