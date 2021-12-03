package Square;

// 2x this qn
// Electric surface two rounds +vo
//Electric side 1: A very simple thing, mainly in clarify requirements, and then write a few classes
//Electric face 2: A set of buyer and seller bids, how to match...match algorithm, the interviewer will prescribe it, and then the implementation will be OK...It's also very simple...
/*
 * buyers and sellers are lists of Person objects with a price (int) and an id (string)
 * buyers price is the price they're willing to pay
 * sellers price is the price they will accept
 * Assumptions:
 *  buyers.length == sellers.length
 *
 * Requirements:
 * All buyers must be matched to a seller and vice-versa
 * For this method buyers should be matched with sellers for all i in buyers.length
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* try to implmenet the following two methods
 *   public static List<Match> matchSimple(List<Person> buyers,List<Pers‍‌‌‌‍‌‌‌‍‍‌‌‌‌‍‌‌‍‍on> sellers)
 *   public static int totalProfit(List<Match> matches) method
 *   and return the totalProfit
 */
public class BuyerSellerMatch {
    class Match {
        Person buyer;
        Person seller;

        public Match(Person buyer, Person seller) {
            this.buyer = buyer;
            this.seller = seller;
        }
    }

    static class Person implements Comparable<Person> {
        String id;
        int price;

        public Person(String id, int price) {
            this.id = id;
            this.price = price;
        }


        @Override
        public int compareTo(Person person) {
            return price - person.price;
        }
    }

    public List<Match> matchSimple(List<Person> buyers, List<Person> sellers) {
        Collections.sort(buyers);
        Collections.sort(sellers);
        Collections.reverse(sellers);
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < buyers.size(); i++) {
            matches.add(new Match(buyers.get(i), sellers.get(i)));
        }
        return matches;
    }

    public int totalProfit(List<Match> matches) {
        int totalProfit = 0;
        for (Match m : matches) {
            totalProfit += (m.buyer.price - m.seller.price);
        }
        return totalProfit;
    }

    public static void main(String[] args){
        BuyerSellerMatch buyerSellerMatch = new BuyerSellerMatch();
        List<Person> buyers = Arrays.asList(new Person("Sai", 100), new Person("Li", 70), new Person("adam", 30));
        List<Person> sellers = Arrays.asList(new Person("Ivan", 50), new Person("Yang", 20), new Person("Wen", 40));

        List<Match> matches = buyerSellerMatch.matchSimple(buyers, sellers);
        System.out.println(matches);
        int totalProfit = buyerSellerMatch.totalProfit(matches);
        System.out.println(totalProfit);
    }
}
