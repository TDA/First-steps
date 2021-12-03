package arrays;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class ArrayListIterator {
    public List<List<Integer>> array;
    private Integer i;
    private Integer j;
    private int currentI;
    private int currentJ;

    @Override
    public String toString() {
        return "ArrayListIterator{" +
                "array=" + array +
                '}';
    }

    public ArrayListIterator(List<List<Integer>> array) {
        this.array = array;
        this.currentI = 0;
        this.currentJ = 0;
        while (currentI < array.size() && this.array.get(currentI).isEmpty()) {
            currentI++;
        }
    }

    boolean hasNext() {
        return (currentI < array.size() && currentJ < array.get(currentI).size());
    }

    int next() {
        if (hasNext()) {
            int nextNumber = array.get(currentI).get(currentJ);
            i = currentI;
            j = currentJ;
            if (currentJ < array.get(currentI).size() - 1) {
                currentJ += 1;
            } else {
                while (++currentI < array.size()) {
                    if (!array.get(currentI).isEmpty()) {
                        // System.out.println("I got here");
                        currentJ = 0;
                        break;
                    }
                }
            }
            return nextNumber;
        } else {
            throw new RuntimeException("No element exists");
        }

    }

    void remove() {
        if ((i == null) || (j == null)) {
            throw new RuntimeException("No element to remove");
        }

        System.out.println("to remove: i = " + i + ", j = " + j);
        this.array.get(i).remove(this.array.get(i).get(j));
        if (i == currentI) {
            currentJ--;
        }
        this.i = null;
        this.j = null;
    }

//    public static void main(String[] args){
//        List<List<Integer>> lists = new ArrayList<>();
//        lists.add(Arrays.asList(1, 2, 3));
//        lists.add(Arrays.asList(4, 5, 6));
//        lists.add(Arrays.asList());
//        lists.add(Arrays.asList(7, 8, 9));
//
//        ArrayListIterator arrayListIterator = new ArrayListIterator(lists);
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        System.out.println(arrayListIterator.next());
//        arrayListIterator.remove();
//        System.out.println(arrayListIterator);
//    }

    public static List<List<Integer>> getInput() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<>(asList()));
        list.add((new ArrayList<>(asList(1,2,3))));
        list.add((new ArrayList<>(asList(4,5))));
        list.add(new ArrayList<>(asList()));
        list.add(new ArrayList<>(asList()));
        list.add((new ArrayList<>(asList(6))));
        list.add((new ArrayList<>(asList(7,8))));
        list.add(new ArrayList<>(asList()));
        list.add((new ArrayList<>(asList(9))));
        list.add((new ArrayList<>(asList(10))));
        list.add(new ArrayList<>(asList()));
        return list;
    }

    public static void main(String[] args) {
        ArrayListIterator iterator = new ArrayListIterator(getInput());
        for (int i = 1; i <= 10; i++) {
            System.out.println(iterator.hasNext());
            System.out.println(iterator.next());
            if (i == 2) {
                System.out.println(iterator);
                iterator.remove();
                System.out.println(iterator);
                iterator.remove();
            }
        }

        //         System.out.println(iterator.hasNext());
        // System.out.println(iterator.next());
        //         System.out.println(iterator.hasNext());
        // System.out.println(iterator.next());
    }
}
