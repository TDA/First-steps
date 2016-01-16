/**
 * Created by schandramouli on 1/16/16.
 */
public class AirFlights {
    // lets define a flight class
    Engine engine;
    float maxSpeed;
    int width;
    int height;
    String flightName;
    String to;
    String from;

    // we should NOT be able to change capacity
    // after setting, after all thats not possible
    // unless we remodel the flight right?
    final int seatCapacity;
    // same for type of the flight
    final String type;

    public AirFlights(Engine engine, int seatCapacity, String flightName, String type) {
        // these fours things are not dependent on anything
        // other than creation
        this.engine = engine;
        this.seatCapacity = seatCapacity;
        this.flightName = flightName;
        this.type = type;
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

    public static void main(String[] args) {

    }
}
