import java.util.*;

public class Simulator
{
    private List<Process> processes = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    
    public void getProcessDetails() {
        // Prompt user to enter num of processes
        int numberOfProcesses = 0;
        
        while (true) {
            try {
                System.out.print("Enter the number of processes (3-10 ): ");
                numberOfProcesses = scanner.nextInt();
                scanner.nextLine(); // Clear invalid input from the scanner
                
                if (numberOfProcesses >= 3 && numberOfProcesses <= 10) {
                    break; // Exit the loop
                }
                else { // if exceed the range
                    System.out.println("\n--> Invalid number of processes. Please enter a number between 3 and 10. \n");
                }
            }
            catch (InputMismatchException e) { // Handle non integer input: string
                System.out.println("\n--> Invalid input. Please enter a valid number. \n");
                scanner.nextLine(); // Clear invalid input from the scanner
            }
        }

        
        while (true) {
            for (int num = 0; num < numberOfProcesses; num++)
            {
            
                System.out.println("-----------------------------------------");
                System.out.println("Details for process P" + num);
                System.out.println("-----------------------------------------");
            
                try {
                    System.out.print("Process Arrival Time: ");
                    int arrivalTime = scanner.nextInt();
                    scanner.nextLine(); // Clear invalid input from the scanner
                    
                    System.out.print("Process Burst Time: ");
                    int burstTime = scanner.nextInt();
                    scanner.nextLine(); // Clear invalid input from the scanner
                    
                    System.out.print("Process Priority: ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Clear invalid input from the scanner
    
                    processes.add(new Process("P"+num, arrivalTime, burstTime, priority));
                }
                catch (InputMismatchException e) { // Handle non integer input: string
                    System.out.println("\n--> Invalid input. Please enter a valid number. \n");
                    scanner.nextLine(); // Clear invalid input from the scanner
                    num--; // Retrieve details of the same process
                }
            }
            break; // Exit the loop once done get all processes details
        }
    }
    
    public void algorithmMenu() {
        System.out.println("--------------------------");
        System.out.println("Chose an scheduling algorithm: ");
        System.out.println("1. Round Robin with Quantum 3");
        System.out.println("2. Shortest Remaining Time (SRT)");
        System.out.println("3. Shortest Job Next (SJN)");
        System.out.println("4. Non-Preemptive Priority");
        System.out.println("--------------------------");
        System.out.print("Please enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear invalid input from the scanner
        
        Algorithm algorithm;
        
        switch (choice) {
            case 1: 
                algorithm = new RoundRobin(processes);
                break;
            case 2:
                algorithm = new SJN(processes);
                break;
            case 3:
                algorithm = new SJN(processes);
                break;
            case 4:
                algorithm = new SJN(processes);
                break;
            default: 
                System.out.println("Invalid choice");
                return;
        }
        
        if (algorithm != null)
        {
            algorithm.runAlgorithm();
        }
    }
    

    /*
    public static void main (String args[]){
        List<Process> processes = new ArrayList<>();
        
        processes.add(new Process ("P0", 0, 6, 3));
        processes.add(new Process ("P1", 1, 4, 3));
        processes.add(new Process ("P2", 5 , 6, 1));
        processes.add(new Process ("P3", 6, 6, 1));
        processes.add(new Process ("P4", 7, 6, 5));
        processes.add(new Process ("P5", 8, 6, 6));
        
  
        int timeQuantum = 3;
        
        RoundRobin rr = new RoundRobin(processes, timeQuantum);
        rr.runAlgorithm();
        
        SJN sjn = new SJN(processes);
        sjn.runAlgorithm();
    }
    */
}
