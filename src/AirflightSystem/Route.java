package AirflightSystem;

import java.util.ArrayList;

/**
 * Created by schandramouli on 1/18/16.
 */
public class Route {
    // this needs a collection of Cities in it, right?
    // or more clearly airports
    ArrayList<Airport> airports = new ArrayList<>();

    // needs to add, modify, delete airports on the given route.
    // for now, jsut override toString


    @Override
    public String toString() {
        String s = "";
        for (Airport airport : airports) {
            s += airport + "-->";
        }
        return s;
    }
}
