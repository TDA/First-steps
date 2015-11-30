import java.util.ArrayList;

/**
 * Created by schandramouli on 11/29/15.
 */
public class Car {
    public static final int CURRENT_YEAR = 2015;
    String manufacturer;
    String name;
    int year;
    Engine engine;
    ArrayList features = new ArrayList();

    public Car() {
        this.manufacturer = "";
        this.name = "";
        this.year = CURRENT_YEAR;
        this.engine = null;
    }

    public Car(String manufacturer, String name, int year) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.year = year;
    }

    public Car(String manufacturer, String name, int year, Engine engine) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.year = year;
        this.engine = engine;
    }

    public ArrayList getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList features) {
        this.features = features;
    }

    public void addFeature(Object o) {
        this.features.add(o);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMake() {
        return name;
    }

    public void setMake(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + manufacturer + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", engine=" + engine +
                ", features=" + features +
                '}';
    }

    public static void main(String[] args) {
        Car car = new Car("Tesla Motors", "80", 2015, new Engine("electric", 20000));
        car.addFeature("sssss");
        car.addFeature(1234);
        System.out.println(car);
    }
}
