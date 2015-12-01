import java.util.Arrays;

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

    public T get(int index) {
        if (index < size) {
            return array[index];
        } else {
            System.out.println("Array index out of bounds");
            return (T) null;
        }
    }

    public int length() {
        return currentPointer;
    }

    @Override
    public String toString() {
        return "ArrayListMock{" +
                "array=" + Arrays.toString(array) +
                ", currentPointer=" + currentPointer +
                ", size=" + size +
                ", length=" + length() +
                '}';
    }

    public static void main(String[] args) {
        // driver pgm
        ArrayListMock<Integer> arrayListMock = new ArrayListMock<>(3);
        arrayListMock.add(1);
        arrayListMock.add(2);
        arrayListMock.add(3);
        arrayListMock.add(4);
        arrayListMock.add(5);
        arrayListMock.add(5);
        System.out.println(arrayListMock);
        System.out.println(arrayListMock.get(2));

    }
}
