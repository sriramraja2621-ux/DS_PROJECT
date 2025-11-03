import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main class to run the smart traffic light simulation with dynamic timing.
 */
public class Main {
    // Constant for converting cars to seconds
    private static final int SECONDS_PER_CAR = 5;

    public static void main(String[] args) {
        // Initialize lanes for a four-way intersection.
        Lane northLane = new Lane("North Lane");
        Lane southLane = new Lane("South Lane");
        Lane eastLane = new Lane("East Lane");
        Lane westLane = new Lane("West Lane");

        // Add all lanes to a list and the controller
        List<Lane> allLanes = new ArrayList<>();
        allLanes.add(northLane);
        allLanes.add(southLane);
        allLanes.add(eastLane);
        allLanes.add(westLane);
        
        TrafficController controller = new TrafficController();
        allLanes.forEach(controller::addLane);

        System.out.println("Starting Smart Traffic Light Simulation with Dynamic Timing...");
        int totalCycles = 15;
        Random random = new Random();

        for (int cycle = 1; cycle <= totalCycles; cycle++) {
            System.out.println("\n--- Cycle " + cycle + " ---");

            // Simulate new cars arriving at random lanes
            int newCars = random.nextInt(20) + 1;
            for (int i = 0; i < newCars; i++) {
                Lane randomLane = allLanes.get(random.nextInt(allLanes.size()));
                randomLane.addCar();
            }
            
            // Occasionally simulate an emergency vehicle (e.g., every 5 cycles)
            if (cycle % 5 == 0) {
                Lane emergencyLane = allLanes.get(random.nextInt(allLanes.size()));
                emergencyLane.setEmergency(true);
                System.out.println("\n*** Emergency vehicle detected in " + emergencyLane.getName() + " ***");
            }

            // Display current traffic counts
            System.out.println("Current Traffic:");
            for (Lane lane : allLanes) {
                System.out.println("  " + lane.getName() + ": " + lane.getCarCount() + " cars" + (lane.isEmergency() ? " (Emergency)" : ""));
            }

            // Run one cycle of the smart controller and get the number of cars processed
            int processedCars = controller.updateAndRunCycle();
            int durationInSeconds = processedCars * SECONDS_PER_CAR;
            System.out.println("Green light duration: " + durationInSeconds + " seconds.");
        }
        System.out.println("\nSimulation finished.");
    }
}
