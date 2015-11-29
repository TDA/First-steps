import java.util.Comparator;

/**
 * Created by schandramouli on 11/28/15.
 */
public class CircularQueue<T> implements Comparable {

    T[] queue;
    int size;
    int activePointer;

    public CircularQueue(int size, int activePointer) {
        this.size = size;
        this.activePointer = 0;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public static void main(String[] args) {

    }
}
