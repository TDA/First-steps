import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

public class SQFirstInterview {
    public static void main(String[] args){
        SQFirstInterview sqFirstInterview = new SQFirstInterview();
        Deck deck =  new Deck();
        System.out.println(deck.cards);
        System.out.println(deck.cards.size());
//        System.out.println(sqFirstInterview.buildDeck());
        List<Card> validCards = new ArrayList<>();
        validCards.add(deck.cards.get(0));
        validCards.add(deck.cards.get(13));
        validCards.add(deck.cards.get(26));
        System.out.println(deck.isValidSetOfCards(validCards));

        List<Card> invalidCards = new ArrayList<>();
        invalidCards.add(deck.cards.get(3));
        invalidCards.add(deck.cards.get(0));
        invalidCards.add(deck.cards.get(23));
        System.out.println(deck.isValidSetOfCards(invalidCards));


        List<Card> cards1 = Arrays.asList(deck.cards.get(0), deck.cards.get(3), deck.cards.get(23), deck.cards.get(1), deck.cards.get(24));
        List<Card> cards2 = Arrays.asList(deck.cards.get(0), deck.cards.get(13), deck.cards.get(26), deck.cards.get(1), deck.cards.get(24));
        List<Card> cards3 = Arrays.asList(deck.cards.get(21), deck.cards.get(3), deck.cards.get(12), deck.cards.get(6), deck.cards.get(0));

        System.out.println(deck.countValidSets(cards1, 3));
        System.out.println(deck.countValidSets(cards2, 3));
        System.out.println(deck.countValidSets(cards3, 3));

    }
}

class Deck {
    List<Card> cards = new ArrayList<>();
    static final String[] NUMBERS = {"ONE", "TWO", "THREE"};
    static final String[] BORDERS = {"THIN", "THICK", "DOUBLE"};
    static final String[] SHAPES = {"SQUARE", "STAIR", "PLUS"};

    public Deck() {
        if (!(NUMBERS.length == BORDERS.length && BORDERS.length == SHAPES.length)) {
            throw new InputMismatchException("Configs are mismatching in number of categories per property.");
        }
        for (String number: NUMBERS) {
            for (String border: BORDERS) {
                for (String shape: SHAPES) {
                    Card card = new Card(number, border, shape);
                    cards.add(card);
                }
            }
        }
    }

    boolean isValidSetOfCards(List<Card> listOfCards) {
        Set<String> numbers = new HashSet<>();
        Set<String> borders = new HashSet<>();
        Set<String> shapes = new HashSet<>();
        for (Card card: listOfCards) {
             numbers.add(card.number);
             borders.add(card.border);
             shapes.add(card.shape);
        }

        return  (isStringSetValid(numbers) && isStringSetValid(borders) && isStringSetValid(shapes));
    }

    int countValidSets(List<Card> cards, int numberOfCardsInSet) {
        int counter = 0;
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                for (int k = j + 1; k < cards.size(); k++) {
                    boolean validSetOfCards = isValidSetOfCards(Arrays.asList(cards.get(i), cards.get(j), cards.get(k)));
                    if (validSetOfCards) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

//    int countValidSetsRecursive(List<Card> cards, int numberOfCardsInSet) {
//        int counter = 0;
//        List<Card> cardsToCheck = new ArrayList<>();
//        return countValidSetsRecursiveHelper(cards, numberOfCardsInSet, counter);
////        for (int i = 0; i < cards.size(); i++) {
////            for (int j = i + 1; j < cards.size(); j++) {
////                for (int k = j + 1; k < cards.size(); k++) {
////                    boolean validSetOfCards = isValidSetOfCards(Arrays.asList(cards.get(i), cards.get(j), cards.get(k)));
////                    if (validSetOfCards) {
////                        counter++;
////                    }
////                }
////            }
////        }
//    }
//
//    private int countValidSetsRecursiveHelper(List<Card> cards, int numberOfCardsInSet, int counter, List<Card> cardsToCheck) {
//        Card firstCard = cards.remove(0);
//        cardsToCheck.add(firstCard);
//        if (cardsToCheck.size() == numberOfCardsInSet) {
//            if (isValidSetOfCards(cardsToCheck)) {
//                counter++;
//            }
//            Card removedCard = cardsToCheck.remove(cardsToCheck.size() - 1);
//            cards.add(removedCard);
//        } else {
//
//            return (countValidSetsRecursiveHelper(cards, numberOfCardsInSet, counter, cardsToCheck));
//        }
//
//        return counter;
//    }

    boolean isStringSetValid(Set<String> strings) {
        return strings.size() == 1 || strings.size() == NUMBERS.length;
    }
}

class Card {
    String number;
    String border;
    String shape;

    public Card(String number, String border, String shape) {
        this.number = number;
        this.border = border;
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number='" + number + '\'' +
                ", border='" + border + '\'' +
                ", shape='" + shape + '\'' +
                '}';
    }
}
