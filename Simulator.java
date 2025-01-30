import java.util.*;

public class Simulator
{
    private List<Process> processes = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    
    public void getProcessDetails() {
        // Prompt user to enter num of processes
        int numberOfProcesses = 0;
        
        while (true) {
            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("                    CPU SCHEDULING SIMULATOR                 ");
            System.out.println("═══════════════════════════════════════════════════════");
            
            try {
                System.out.print("Enter the number of processes (3-10): ");
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

        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("                     PROCESS DETAILS INPUT                   ");
        System.out.println("═══════════════════════════════════════════════════════");
        
        while (true) {
            for (int num = 0; num < numberOfProcesses; num++)
            {
                System.out.println("\nProcess P" + num);
            
                try {
                    System.out.print("  Arrival Time    : ");
                    int arrivalTime = scanner.nextInt();
                    scanner.nextLine(); // Clear invalid input from the scanner
                    
                    System.out.print("  Burst Time      : ");
                    int burstTime = scanner.nextInt();
                    scanner.nextLine(); // Clear invalid input from the scanner
                    
                    System.out.print("  Priority        : ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Clear invalid input from the scanner
    
                    System.out.println("\n-------------------------------------------------------------");

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
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("                   CHOOSE SCHEDULING ALGORITHM               ");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("1. Round Robin with Quantum 3");
        System.out.println("2. Shortest Remaining Time (SRT)");
        System.out.println("3. Shortest Job Next (SJN)");
        System.out.println("4. Non-Preemptive Priority");
        System.out.println("-------------------------------------------------------------");
        System.out.print("Please enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear invalid input from the scanner
        
        Algorithm algorithm;
        
        switch (choice) {
            case 1: 
                algorithm = new RoundRobin(processes);
                break;
            case 2:
                algorithm = new SRT(processes);
                break;
            case 3:
                algorithm = new SJN(processes);
                break;
            case 4:
                algorithm = new NPP(processes);
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
}
