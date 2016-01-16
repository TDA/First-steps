/**
 * Created by schandramouli on 1/16/16.
 */
public class AirFlights {
    // lets define a flight class
    Engine engine;
    int seatCapacity;
    String type;
    String flightName;
    int width;
    int height;
    float maxSpeed;
    String to;
    String from;

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
        this.engine = engine;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void main(String[] args) {

    }
}
