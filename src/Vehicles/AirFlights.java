package Vehicles;

import javafx.geometry.Dimension2D;

/**
 * Created by schandramouli on 1/16/16.
 */
public class AirFlights {
    // lets define a flight class
    private Engine engine;
    // my argument is that max speed is determined by
    // more than just the engine, so it needs to be
    // declared here instead of in engine :)
    private float maxSpeed;
    private String flightName;
    private String to;
    private String from;

    // we should NOT be able to change capacity
    // after setting, after all thats not possible
    // unless we remodel the flight right?
    private final int seatCapacity;
    // same for type of the flight
    private final String type;
    private final int width;
    private final int height;

    public AirFlights(Engine engine, int seatCapacity, String flightName, String type, Dimension2D d) {
        // these fours things are not dependent on anything
        // other than creation
        this.engine = engine;
        this.seatCapacity = seatCapacity;
        this.flightName = flightName;
        this.type = type;
        // width and height never change, and are prolly
        // never used as well :D
        this.width = ((int) d.getWidth());
        this.height = ((int) d.getHeight());
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        // we should be able to replace a engine
        this.engine = engine;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public String getType() {
        return type;
    }

    public void prepareFlight(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void prepareFlight(String from, String to, String flightName) {
        this.from = from;
        this.to = to;
        this.flightName = flightName;
    }

    @Override
    public String toString() {
        return "AirFlights{" +
                "engine=" + engine +
                ", maxSpeed=" + maxSpeed +
                ", flightName='" + flightName + '\'' +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", seatCapacity=" + seatCapacity +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public static void main(String[] args) {
        AirFlights airFlight = new AirFlights(new Engine("Jinjuks", 200000), 102, "LH709", "Boeing747", new Dimension2D(300.0F, 100.0F));
        System.out.println(airFlight);
        airFlight.prepareFlight("Chennai", "Frankfurt");
        System.out.println(airFlight);
    }
}
