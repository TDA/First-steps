package fb_recent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class RandomPick {
    List<Integer> randomizedQueue = new ArrayList<>(100);
    int currentPointer = 0;


    public RandomPick(int[] w) {
        int sum = 0;
        for(int x: w) {
            sum += x;
        }
        if (sum == 0) throw new NumberFormatException("Cannot be zero denominator");
        for (int i = 0; i < w.length; i++) {
            double percentage = ((double) w[i]) / sum;
            int occurrences = (int) Math.round(percentage * 100);
            System.out.println("occurrences for i " + occurrences + " " + i);
            for (int j = 0; j < occurrences; j++) {
                randomizedQueue.add(i);
            }
        }
        System.out.println(randomizedQueue.size());
        Collections.shuffle(randomizedQueue);
        System.out.println(randomizedQueue);
    }

    public int pickIndex() {
        if (++currentPointer > 99) currentPointer = 0;
        return randomizedQueue.get(currentPointer);
    }

    public static void main(String[] args){
        RandomPick randomPick = new RandomPick(Stream.of(1).mapToInt(i -> i).toArray());
        System.out.println(randomPick.pickIndex());

        RandomPick randomPick2 = new RandomPick(Stream.of(1, 3).mapToInt(i -> i).toArray());
        System.out.println(randomPick2.pickIndex());
        System.out.println(randomPick2.pickIndex());
        System.out.println(randomPick2.pickIndex());
        System.out.println(randomPick2.pickIndex());
        System.out.println(randomPick2.pickIndex());
        System.out.println(randomPick2.pickIndex());

        RandomPick randomPick3 = new RandomPick(Stream.of(10, 7, 8, 10).mapToInt(i -> i).toArray());
        RandomPick randomPick4 = new RandomPick(Stream.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10000000).mapToInt(i -> i).toArray());
    }
}
