package arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Player {
    String name;
    public long totalPoints;


    public List<Item> items = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String toString() {
        return name + " " + items + " total: " + totalPoints;
    }
}


class Item {
    String name;
    long count;

    public Item(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String toString() {
        return name + " " + count;
    }
}

class Solution {
    public static void main(String[] args) {
        // Player   Cherries            Oranges       Total
        // Clara       3 (1)             2 (2)       1+2 => 3
        // Ben         5 (3)             1 (1)       3+1 => 4
        // Linda       4 (2)             4 (3)              5

        ArrayList<Player> players = new ArrayList<>();

        Player clara = new Player("Clara");
        clara.items.add(new Item("Cherries", 3));
        clara.items.add(new Item("Oranges", 2));

        Player ben = new Player("Ben");
        ben.items.add(new Item("Cherries", 5));
        ben.items.add(new Item("Oranges", 1));

        Player linda = new Player("Linda");
        linda.items.add(new Item("Cherries", 4));
        linda.items.add(new Item("Oranges", 4));

        players.add(clara);
        players.add(ben);
        players.add(linda);
        System.out.println(players);

        List<Item> itemList = players.get(0).items;
        System.out.println(itemList);
        // validate null and count of 0
        for (Item item : itemList) {
            List<Tuple> playerItemCounts = new ArrayList<>();
            String itemName = item.name;
            for (Player player: players) {
                for (Item playerItem : player.items) {
                    if (playerItem.name.equals(itemName)) {
                        playerItemCounts.add(new Tuple(player, playerItem.count));
                    }
                }
            }

            System.out.println("before sort");
            System.out.println(playerItemCounts);
            System.out.println("------------------");
            Collections.sort(playerItemCounts);
            System.out.println("after sort");
            System.out.println(playerItemCounts);
            System.out.println("------------------");

            int rank = 1;
            for (Tuple t: playerItemCounts) {
                t.player.totalPoints += rank;
                rank++;
            }
        }

        System.out.println(players);
    }
}

class Tuple implements Comparable<Tuple> {
    public Player player;
    public long count;

    public Tuple(Player player, long count) {
        this.player = player;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "player=" + player +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(Tuple tuple) {
        if (this.count == tuple.count) {
            return 0;
        } else if (this.count > tuple.count) {
            return 1;
        } else {
            return -1;
        }
    }
}