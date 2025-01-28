import java.util.*;

public class Simulator
{
    public static void main (String args[]){
        List<Process> processes = new ArrayList<>();
        
        processes.add(new Process ("P0", 0, 6, 1));
        processes.add(new Process ("P1", 1, 4, 1));
        processes.add(new Process ("P2", 5, 6, 1));
        processes.add(new Process ("P3", 6, 6, 1));
        processes.add(new Process ("P4", 7, 6, 1));
        processes.add(new Process ("P5", 8, 6, 1));
        
        RoundRobin rr = new RoundRobin(processes);
        rr.runAlgorithm();
    }
}
