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

    public boolean insertAtHead(T data) {
        //TODO: have to insert into the queue
        queue[activePointer++] = data;
        if (activePointer >= size) {
            // reset the active pointer to beginning
            activePointer = 0;
        }
        return true;
    }

    public boolean insertAt(int position, T data) {
        //TODO: have to insert into the queue
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public static void main(String[] args) {

    }
}
