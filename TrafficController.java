import java.util.PriorityQueue;

/**
 * The core controller for the traffic system.
 * Uses a PriorityQueue to manage lanes, prioritizing emergency vehicles
 * and then the number of cars. It dynamically calculates the green light
 * duration based on the number of cars in the highest-priority lane.
 */
public class TrafficController {
    private PriorityQueue<Lane> lanePriorityQueue;

    public TrafficController() {
        this.lanePriorityQueue = new PriorityQueue<>(
            (lane1, lane2) -> {
                if (lane1.isEmergency() && !lane2.isEmergency()) {
                    return -1;
                }
                if (!lane1.isEmergency() && lane2.isEmergency()) {
                    return 1;
                }
                return Integer.compare(lane2.getCarCount(), lane1.getCarCount());
            }
        );
    }

    /** Adds a lane to the priority queue. */
    public void addLane(Lane lane) {
        lanePriorityQueue.add(lane);
    }

    /**
     * Runs one complete cycle of the traffic controller with dynamic timing.
     * The number of cars to process is now calculated based on the lane's car count.
     * @return The number of cars processed in this cycle.
     */
    public int updateAndRunCycle() {
        // Rebuild the queue to ensure priorities are up-to-date.
        PriorityQueue<Lane> tempQueue = new PriorityQueue<>(
            (lane1, lane2) -> {
                if (lane1.isEmergency() && !lane2.isEmergency()) {
                    return -1;
                }
                if (!lane1.isEmergency() && lane2.isEmergency()) {
                    return 1;
                }
                return Integer.compare(lane2.getCarCount(), lane1.getCarCount());
            }
        );
        while (!lanePriorityQueue.isEmpty()) {
            tempQueue.add(lanePriorityQueue.poll());
        }
        lanePriorityQueue = tempQueue;
        
        Lane currentLane = lanePriorityQueue.poll();
        
        if (currentLane != null) {
            System.out.println("\n---- Granting green light to " + currentLane.getName() + " ----");
            if (currentLane.isEmergency()) {
                System.out.println("  *** Emergency vehicle detected! Priority override. ***");
            }
            
            currentLane.getTrafficLight().setColor("GREEN");
            
            // DYNAMIC TIMING: Calculate the number of cars to process based on the car count.
            int carsToProcess = 2 + (currentLane.getCarCount() / 2);
            carsToProcess = Math.min(carsToProcess, 10);
            
            int processedCount = currentLane.processTraffic(carsToProcess);
            System.out.println("Processed " + processedCount + " cars from " + currentLane.getName() + ".");
            if (currentLane.isEmergency()) {
                currentLane.setEmergency(false);
            }

            currentLane.getTrafficLight().setColor("RED");
            lanePriorityQueue.add(currentLane);
            return processedCount;
        }
        return 0;
    }
}
