package dp_recursion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Knapsack {
    public int coinChange(int[] coins, int amount) {
        final Map<Integer, Integer> cache = new HashMap<>();
        if (amount <= 0 || coins.length == 0) return 0;
        int coinHelper = coinHelper(coins, amount, cache);
        System.out.println(cache);
        return coinHelper;
    }

    private int coinChangeIterative(int[] coins, int amount) {
        final Map<Integer, Integer> cache = new HashMap<>();
        cache.put(0, 0);
        for (int eachAmount = 1; eachAmount <= amount; eachAmount++) {
            for (int coin : coins) {
                Integer minimumForEarlierNumber = cache.getOrDefault(eachAmount - coin, Integer.MAX_VALUE);
                if (eachAmount - coin >= 0 && !minimumForEarlierNumber.equals(Integer.MAX_VALUE)) {
                    Integer eachAmountMinimumSoFar = cache.getOrDefault(eachAmount, Integer.MAX_VALUE);
                    cache.put(eachAmount,  Math.min(eachAmountMinimumSoFar, minimumForEarlierNumber + 1));
                }
            }
        }
        return cache.getOrDefault(amount, -1);
    }

    // Array is extremely fast compared to Hashmap.. but consumes way more space
    private int coinChangeIterativeArray(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] newArray = new int[amount + 1];
        Arrays.fill(newArray, Integer.MAX_VALUE);
        newArray[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if ((i - coin >= 0) && newArray[i - coin] != Integer.MAX_VALUE) {
                    newArray[i] = Math.min(newArray[i], newArray[i - coin] + 1);
                }
            }
        }
        return newArray[amount] == Integer.MAX_VALUE ? -1 : newArray[amount];
    }

    private int coinHelper(int[] coins, int amount, Map<Integer, Integer> cache) {
        System.out.println("Amount " + amount);
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (cache.containsKey(amount)) return cache.get(amount);
        int min = Integer.MAX_VALUE;
        for (int i = coins.length - 1; i >= 0; i--) {
            int coin = coins[i];
            System.out.println("Checking coin " + coin);
            int result = coinHelper(coins, amount - coin, cache);
            if (result >= 0 && result < min)
                min = result + 1;
            if (result == 0) break;
        }
        cache.put(amount, (min == Integer.MAX_VALUE) ? -1 : min);
        System.out.println("Min is " + cache.get(amount));
        return cache.get(amount);
    }



    public int greedyCoinChange(int[] coins, int amount) {
        // As with any flavor of knapsack 0-1, this wont work with greedy either
        Arrays.sort(coins);
        int minCoins = 0;
        int i = coins.length - 1;
        int localCount = 0;
        while (localCount < amount && i >= 0) {
            if (coins[i] > (amount - localCount)) {
                while (i > 0) {
                    if (coins[i] <= (amount - localCount)) break;
                    i--;
                }
            }
            localCount += coins[i];
            minCoins++;

            System.out.println(localCount);
        }
        if (amount == localCount) {
            return minCoins;
        } else {
            return -1;
        }
    }

    public static void main(String[] args){
        Knapsack knapsack = new Knapsack();
        int[] coins = {1, 2, 5};
        int[] coins1 = {1, 3, 5};
        int[] coins2 = {186,419,83,408};
//        System.out.println(knapsack.greedyCoinChange(coins, 11));
//        System.out.println(knapsack.greedyCoinChange(coins1, 3));
//        // failing case for greedy
//        System.out.println(knapsack.greedyCoinChange(coins2, 6249));
        System.out.println(knapsack.coinChange(coins, 11));
        System.out.println("------------------------------");
        System.out.println(knapsack.coinChange(coins1, 3));
//        System.out.println(knapsack.coinChange(coins2, 6249));
        System.out.println(knapsack.coinChangeIterative(coins, 11));
        System.out.println("------------------------------");
        System.out.println(knapsack.coinChangeIterative(coins1, 3));
        System.out.println(knapsack.coinChangeIterative(coins2, 6249));
    }
}
