import java.util.Comparator;

/**
 * Created by schandramouli on 11/28/15.
 */
public class CircularQueue<T> implements Comparable {

    T[] queue;
    int size;
    int activePointer;

    public CircularQueue(int size) {
        this.size = size;
        this.activePointer = 0;
        this.queue = (T[]) new Object[size];
    }

    public void insertAtHead(T data) {
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

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && this.compareTo(obj) == 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (T t : queue) {
            s += t + " ";
        }
        return s;
    }

    public static void main(String[] args) {
        // driver pgm
        CircularQueue<Integer> circularQueue = new CircularQueue<>(5);
        CircularQueue<Integer> circularQueue2 = new CircularQueue<>(5);

        for (int i = 0; i < 7; i++) {
            circularQueue.insertAtHead(i);
        }

        for (int i = 0; i < 6; i++) {
            circularQueue2.insertAtHead(i);
        }

        System.out.println(circularQueue);
        System.out.println(circularQueue2);
        System.out.println(circularQueue.equals(circularQueue2));

    }
}
