package Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// First Choice: 3 points
// Second Choice: 2 points
// Third Choice: 1 point
//
// All remaining choices are assigned 0 points.
//
//
// Ballot 1: Toy Story, Tangled, Cars, Frozen, Mulan
// Ballot 2: Frozen, Toy Story, Mulan, Cars, Tangled
//
// Final Scores: Toy Story: 5, Frozen: 3, Tangled: 2, Cars: 1,  Mulan: 1
//
// Output: Toy Story
//
//______________________________________________
//
//Ballot 1: A, B, C, D
//Ballot 2: A, B, C, D
//Ballot 3: D, C, B, A
//
//Final scores:  A: 6, B: 5, C: 4, D: 3
//
//Output: B
//
//
//_____________________________________________
//
//Ballot 1: A, B, C, D, E
//Ballot 2: A, B, C, D, E
//Ballot 3: C, B, D, E, A
//
//Final scores:  A: 6, B: 6, C: 5, D: 1, E: 0
//
//Output: B, C
public class BallotCounter {

    static class Ballot {
        List<String> items;

        public Ballot(List<String> items) {
            this.items = items;
        }
    }

    static class Item implements Comparable<Item> {
        String name;
        int noOfFirstPlaces;
        int noOfSecondPlaces;
        int noOfThirdPlaces;
        int totalPoints = 0;

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", noOfFirstPlaces=" + noOfFirstPlaces +
                    ", noOfSecondPlaces=" + noOfSecondPlaces +
                    ", noOfThirdPlaces=" + noOfThirdPlaces +
                    ", totalPoints=" + totalPoints +
                    '}';
        }

        @Override
        public int compareTo(Item item) {
            int count = noOfFirstPlaces - item.noOfFirstPlaces;
            count += (noOfSecondPlaces - item.noOfSecondPlaces);
            count += (noOfThirdPlaces - item.noOfThirdPlaces);
            if (count == 0) return totalPoints - item.totalPoints;
            return count;
        }
    }

    public static void main(String[] args){
        BallotCounter ballotCounter = new BallotCounter();
        List<Ballot> ballotList = new ArrayList<>();
        ballotList.add(new Ballot(Arrays.asList("Toy Story", "Tangled", "Cars", "Frozen", "Mulan")));
        ballotList.add(new Ballot(Arrays.asList("Frozen", "Toy Story", "Mulan", "Cars", "Tangled")));
        System.out.println(ballotCounter.countBallots(ballotList));

        List<Ballot> ballotList2 = new ArrayList<>();
        ballotList2.add(new Ballot(Arrays.asList("A", "B", "C", "D")));
        ballotList2.add(new Ballot(Arrays.asList("A", "B", "C", "D")));
        ballotList2.add(new Ballot(Arrays.asList("D", "C", "B", "A")));
        System.out.println(ballotCounter.countBallots(ballotList2));

        List<Ballot> ballotList3 = new ArrayList<>();

        ballotList3.add(new Ballot(Arrays.asList("A", "B", "C", "D", "E")));
        ballotList3.add(new Ballot(Arrays.asList("A", "B", "C", "D", "E")));
        ballotList3.add(new Ballot(Arrays.asList("C", "B", "D", "E", "A")));
        System.out.println(ballotCounter.countBallots(ballotList3));

    }

    private String countBallots(List<Ballot> ballotList) {
        Map<String, Item> finalScores = new HashMap<>();;
        for (Ballot ballot : ballotList) {
            int i = 3;
            for (String name: ballot.items) {
                Item currentItemValue = finalScores.getOrDefault(name, new Item());

                switch (i) {
                    case 3: currentItemValue.noOfFirstPlaces++; break;
                    case 2: currentItemValue.noOfSecondPlaces++; break;
                    case 1: currentItemValue.noOfThirdPlaces++; break;
                    default: break;
                }
                currentItemValue.totalPoints += i;
                currentItemValue.name = name;
                finalScores.put(name, currentItemValue);
                i--;
            }
            System.out.println(finalScores);
        }
        List<Item> values = new ArrayList<>(finalScores.values());
        Collections.sort(values);
        Collections.reverse(values);
        System.out.println(values);
        return values.get(0).name;
    }
}
