/**
 * Created by schandramouli on 11/29/15.
 */
public class Car {
    String manufacturer;
    String make;
    String year;

    public Car(String manufacturer, String make, String year) {
        this.manufacturer = manufacturer;
        this.make = make;
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
