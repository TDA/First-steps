package Square;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class CasinoBois {
    static class CardDrawer implements Cloneable {
        ArrayDeque<String> cards;

        public CardDrawer() {
            Casino casino = new Casino();
            Set<String> cardSet = new HashSet<>();
            while (cardSet.add(casino.draw()));
            cards = new ArrayDeque<>(cardSet);
        }

        public CardDrawer(ArrayDeque<String> cards) {
            this.cards = cards;
        }

        public String peek() {
            return cards.peek();
        }

        public String draw() {
            String card = cards.pollFirst();
            cards.addLast(card);
            return card;
        }

        public CardDrawer copy() {
            return new CardDrawer(cards.clone());
        }

        @Override
        public CardDrawer clone() {
            try {
                CardDrawer clone = (CardDrawer) super.clone();
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                clone.cards = cards.clone();
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    public static void main(String[] args){
        CardDrawer cardDrawer1 = new CardDrawer();
        CardDrawer cardDrawer2 = cardDrawer1.copy();
        System.out.println(cardDrawer1.peek()); // => B2
        System.out.println(cardDrawer2.peek()); // => B2
        System.out.println(cardDrawer2.draw()); // => B2
        System.out.println(cardDrawer2.draw()); // => C3
        System.out.println(cardDrawer2.draw()); // => D4
        System.out.println(cardDrawer1.draw()); // => B2

    }
}
