import java.util.LinkedList;
import java.util.Queue;

class Lane {
    private String name;
    private Queue<String> cars;
    private TrafficLight trafficLight;
    private boolean isEmergency;

    public Lane(String name) {
        this.name = name;
        this.cars = new LinkedList<>();
        this.trafficLight = new TrafficLight(name);
        this.isEmergency = false;
    }

    public String getName() {
        return name;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void addCar() {
        cars.add("car");
    }

    public int getCarCount() {
        return cars.size();
    }

    public void setEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
        if (isEmergency) cars.add("car"); // ensure at least one car exists
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public int processTraffic(int carsToProcess) {
        int processedCount = 0;
        while (!cars.isEmpty() && processedCount < carsToProcess) {
            cars.poll();
            processedCount++;
        }
        return processedCount;
    }
}