public class Process {

    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int remainingTime;  // initialized to = burstTime后来慢慢扣
    private int finishTime;
    
    // constructor
    public Process (String processName, int arrivalTime, int burstTime, int priority) {
        // Information from user input
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        
        // remainingTime initialized to burstTime at creation of process object
        this.remainingTime = burstTime; 
    }
    
    // Getters
    public String getProcessName() { 
        return processName; 
    }
    
    public int getArrivalTime() { 
        return arrivalTime; 
    }
    
    public int getBurstTime() { 
        return burstTime; 
    }
    
    public int getPriority() { 
        return priority; 
    }
    
    public int getRemainingTime() { return remainingTime; }
    
    public int getFinishTime() { return finishTime; }
    
    
    // Setters
    public void setRemainingTime(int remainingTime) { 
        this.remainingTime = remainingTime; 
    }
    
    public void setFinishTime(int finishTime) { 
        this.finishTime = finishTime;
    }
}
