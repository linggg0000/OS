import java.util.*;

abstract public class Algorithm {
    protected List<Process> processList;
    protected Queue<Process> readyQueue;
    protected List<String> completedProcesses;  // To be printed in Gantt Chart
    protected StringBuilder timeLine;   // Timeline under Gantt Chart
    
    // Constructor
    public Algorithm (List<Process> processList) {
        // Sort unsorted processes & store as processList
        this.processList = processList;
        this.readyQueue = new LinkedList<>();
        //this.ganttChart = new StringBuilder();
        this.completedProcesses = new ArrayList<>();
        this.timeLine = new StringBuilder();
    }
    
    
    // Abstract method to be overriden by algorithm classes
    abstract public void runAlgorithm();
    
    //public void printGanttChart()
        
    
}
