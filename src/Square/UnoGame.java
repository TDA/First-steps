package Square;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

// To design an uno game, suppose there are three colors of cards, red, blue, and yellow.
// There are 10 cards of each color, and each player has 7 cards in his hand.
// Whoever finishes first wins.
// Red wins blue, blue wins yellow, yellow wins red.
// Large numbers win small ones.
// If nothing can be drawn, the cards are drawn.
public class UnoGame {
    Queue<UnoCard> deck;

    public UnoGame() {
        List<UnoCard> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add(new UnoCard(i, "yellow"));
            list.add(new UnoCard(i, "blue"));
            list.add(new UnoCard(i, "red"));
        }
        Collections.shuffle(list);
        deck = new ArrayDeque<>(list);
    }

    private List<UnoCard> deal(int i) {
        List<UnoCard> list = new ArrayList<>();
        while (i-- > 0) {
            list.add(deck.poll());
        }
        return list;
    }

    private static class UnoCard implements Comparable<UnoCard> {

        int number;
        String color;
        public UnoCard(int number, String color) {
            this.number = number;
            this.color = color;
        }

        @Override
        public int compareTo(UnoCard unoCard) {
            if (color.equals(unoCard.color)) return number - unoCard.number;
            if (color.equals("red") && unoCard.color.equals("blue") || (color.equals("blue") && unoCard.color.equals("yellow") || (color.equals("yellow") && unoCard.color.equals("red")))) {
                return 1;
            }
            return -1;
        }

        @Override
        public String toString() {
            return "UnoCard{" + " " + number + " -- " + color + '}';
        }
    }
    private static String playGame() {
        UnoGame unoGame = new UnoGame();
        List<UnoCard> player1Cards = unoGame.deal(7);
        List<UnoCard> player2Cards = unoGame.deal(7);
        int turn = 0;

        while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
            // New turn, switch players and keep going
            if (turn % 2 == 0) {
                playTurn(unoGame, player1Cards, player2Cards);
            } else {
                playTurn(unoGame, player2Cards, player1Cards);
            }
            turn++;
        }
        return (player2Cards.isEmpty() ? "Player 2" : "Player 1");
    }

    private static void playTurn(UnoGame unoGame, List<UnoCard> activePlayerCards, List<UnoCard> reactivePlayerCards) {
        System.out.println("New turn");
        System.out.println(activePlayerCards.size());
        System.out.println(activePlayerCards);
        System.out.println(reactivePlayerCards.size());
        System.out.println(reactivePlayerCards);

        UnoCard activePlayerCard = getBestCardToPlay(activePlayerCards);
        activePlayerCards.remove(activePlayerCard);
        // Find best card for Player 2
        UnoCard bestCardForPlayer2 = getBestCardToPlay(reactivePlayerCards, activePlayerCard);
        if (bestCardForPlayer2 == null) {
            // player 2 lost, player 1 discards their card, player 2 draws a card
            reactivePlayerCards.addAll(unoGame.deal(1));
        } else {
            // player2 wins this card, put both in p1 hands
            activePlayerCards.add(bestCardForPlayer2);
            reactivePlayerCards.remove(bestCardForPlayer2);
        }
    }

    private static UnoCard getBestCardToPlay(List<UnoCard> cards, UnoCard cardToBeat) {
        List<UnoCard> bestCards = cards.stream().filter(c -> c.compareTo(cardToBeat) > 0).collect(Collectors.toList());
        if (bestCards.isEmpty()) {
            return null;
        } else {
            Collections.sort(bestCards);
            return bestCards.get(0);
        }
    }

    private static UnoCard getBestCardToPlay(List<UnoCard> handToPlay) {
        Collections.sort(handToPlay);
        return handToPlay.get(handToPlay.size() - 1);
    }

    public static void main(String[] args){
        Map<String, Integer> playerMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String winner = playGame();
            playerMap.put(winner, playerMap.getOrDefault(winner, 0) + 1);
        }
        System.out.println(playerMap);
    }
}
