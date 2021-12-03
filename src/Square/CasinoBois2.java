package Square;

import java.util.ArrayList;
import java.util.List;

public class CasinoBois2 {

    static class CardDrawer {
        Casino casino;
        List<String> cards = new ArrayList<>();
        int indexOfPeek;

        public CardDrawer() {
            casino = new Casino();
        }

        public CardDrawer(Casino casino, List<String> cards, int indexOfPeek) {
            this.casino = casino;
            this.cards = cards;
            this.indexOfPeek = indexOfPeek;
        }

        public String peek() {
            if (cards.size() == 0) {
                cards.add(casino.draw());
            }
            return cards.get(indexOfPeek);
        }

        public String draw() {
            String card = cards.get(indexOfPeek);
            cards.add(casino.draw());
            indexOfPeek++;
            return card;
        }

        public CardDrawer copy() {
            return new CardDrawer(casino, cards, indexOfPeek);
        }

        @Override
        public String toString() {
            return "CardDrawer{" +
                    "casino=" + casino +
                    ", cards=" + cards +
                    ", indexOfPeek=" + indexOfPeek +
                    '}';
        }
    }

    public static void main(String[] args){
        CardDrawer cardDrawer1 = new CardDrawer();
        CardDrawer cardDrawer2 = cardDrawer1.copy();
        System.out.println(cardDrawer1.peek()); // => B2
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1);
        System.out.println(cardDrawer2);
        System.out.println(cardDrawer1.peek());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer1.peek());
        System.out.println(cardDrawer1.draw());
        System.out.println(cardDrawer2.peek()); // => B2
        System.out.println(cardDrawer2.draw()); // => B2
        System.out.println(cardDrawer2.draw()); // => C3
        System.out.println(cardDrawer2.draw()); // => D4
        System.out.println(cardDrawer1.draw()); // => B2
        System.out.println(cardDrawer1);
        System.out.println(cardDrawer2);
        CardDrawer cardDrawer3 = cardDrawer1.copy();
        System.out.println(cardDrawer3.peek()); // => C3
        System.out.println(cardDrawer3.draw()); // => C3
        System.out.println(cardDrawer3);

    }
}
