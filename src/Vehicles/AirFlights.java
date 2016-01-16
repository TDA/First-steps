package Vehicles;

import javafx.geometry.Dimension2D;

/**
 * Created by schandramouli on 1/16/16.
 */
public class AirFlights {
    // lets define a flight class
    Engine engine;
    // my argument is that max speed is determined by
    // more than just the engine, so it needs to be
    // declared here instead of in engine :)
    float maxSpeed;
    String flightName;
    String to;
    String from;

    // we should NOT be able to change capacity
    // after setting, after all thats not possible
    // unless we remodel the flight right?
    final int seatCapacity;
    // same for type of the flight
    final String type;
    final int width;
    final int height;

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


    public static void main(String[] args) {

    }
}
