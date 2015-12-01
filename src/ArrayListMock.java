/**
 * Created by schandramouli on 11/30/15.
 */
public class ArrayListMock<T> {
    T[] array;
    int currentPointer = 0;
    int size;

    public ArrayListMock(int size) {
        this.size = size;
        this.array = (T[]) new Object[this.size];
    }

    public void add(T data) {
        array[currentPointer++] = data;
        if (currentPointer >= size) {
            // double the size
            this.size = this.size * 2;
            // create a temp holder
            T[] temp = (T[]) new Object[this.size];

            // copy contents into temp, and then set array to this
            System.arraycopy(array, 0, temp, 0, array.length);
            array = temp;
        }
    }

    public static void main(String[] args) {
        // driver pgm

    }
}
