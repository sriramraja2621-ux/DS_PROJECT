// The TrafficLight class is straightforward, representing the state of a single light.
class TrafficLight {
    private String name;
    private String colour;

    public TrafficLight(String name) {
        this.name = name;
        this.colour = "RED";
    }

    public String getColor() {
        return colour;
    }

    public void setColor(String colour) {
        this.colour = colour;
    }

    public String getName() {
        return name;
    }
}

