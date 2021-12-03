package Square;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

class Casino {
    String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    String[] faces = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
    ArrayDeque<String> deck;

    public Casino() {
        List<String> tempDeck = new ArrayList<>();
        for (String suit : suits) {
            for (String face : faces) {
                tempDeck.add(face + " " + suit);
            }
        }
        deck = new ArrayDeque<>(tempDeck);
    }

//    public Casino(ArrayDeque<String> deck) {
//        this.deck = deck;
//    }

    public String draw() {
        String card = deck.pollFirst();
        deck.addLast(card);
        return card;
    }
}
