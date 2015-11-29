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

    public void insertAtHead(T data) {
        //TODO: have to insert into the queue
        queue[activePointer++] = data;
        if (activePointer >= size) {
            // reset the active pointer to beginning
            activePointer = 0;
        }
    }

    public void insertAt(int position, T data) {
        //TODO: have to insert into the queue
        queue[position] = data;
        if (++activePointer >= size) {
            // reset the active pointer to beginning
            activePointer = 0;
        }
    }

    public T get(int position) {
        return this.queue[position];
    }

    @Override
    public int compareTo(Object o) {
        if (this.size > ((CircularQueue) o).size){
            // this is greater
            return 1;
        } else if (this.size < ((CircularQueue) o).size) {
            return -1;
        } else {
            for (int i = 0; i < this.size; i++) {
                if (this.get(i) == ((CircularQueue) o).get(i)) {
                    // continue
                    continue;
                } else {
                    // let this be greater
                    return 1;
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        // driver pgm

    }
}
