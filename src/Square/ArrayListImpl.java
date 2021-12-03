package Square;


import java.util.Arrays;
import java.util.InputMismatchException;

// Two: implement an array, two methods: append and get(index). You cannot use Python's native automatic resize.
// You must manually specify an initial size and copy it to a new space each time you resize. It’s also very simple, let’s do whatever you do...
public class ArrayListImpl<T> {
    private static final int INITIAL_CAPACITY = 16;
    T[] internalArray;
    int currentIndex;

    public ArrayListImpl() {
        internalArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    void append(T i) {
        if (currentIndex == internalArray.length) {
            // doible the arrrray
            T[] tempArray = (T[]) new Object[internalArray.length * 2];
            System.arraycopy(internalArray, 0, tempArray, 0, internalArray.length);
            internalArray = tempArray;
        }
        internalArray[currentIndex++] = i;
    }

    T get(int index) {
        if (index < internalArray.length)
            return internalArray[index];
        else {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    public static void main(String[] args){
        ArrayListImpl<Integer> arrayListImpl = new ArrayListImpl<>();
        arrayListImpl.append(1);
        arrayListImpl.append(2);
        arrayListImpl.append(3);
        arrayListImpl.append(4);
        arrayListImpl.append(5);
        arrayListImpl.append(6);
        arrayListImpl.append(7);arrayListImpl.append(1);
        arrayListImpl.append(2);
        arrayListImpl.append(3);
        arrayListImpl.append(4);
        arrayListImpl.append(5);
        arrayListImpl.append(6);
        arrayListImpl.append(7);arrayListImpl.append(1);
        arrayListImpl.append(2);
        arrayListImpl.append(3);
        arrayListImpl.append(4);
        arrayListImpl.append(5);
        arrayListImpl.append(5);
        arrayListImpl.append(6);
        arrayListImpl.append(7);arrayListImpl.append(1);
        arrayListImpl.append(2);
        arrayListImpl.append(3);
        arrayListImpl.append(4);
        arrayListImpl.append(5);
        arrayListImpl.append(6);
        arrayListImpl.append(7);arrayListImpl.append(1);
        arrayListImpl.append(2);
        arrayListImpl.append(3);
        arrayListImpl.append(4);
        arrayListImpl.append(5);
        arrayListImpl.append(6);
        arrayListImpl.append(7);
        System.out.println(Arrays.toString(arrayListImpl.internalArray));
    }
}
