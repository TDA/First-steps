package Square;
// We're going to build a game named Zendo.

// The game is played over a series of rounds. In each round, you will be given one or more game pieces and a rule.

// Your task will be to determine if the game pieces follow the rule.

// Each game piece will have a color and a size. The possible colors and sizes are
// colors: red, blue, green
// sizes: small, medium, large

// We will build this game one round at a time, making sure previous rounds continue to pass.

// You can represent the input using any data structures you want.

// Round: 1
// pieces: one large red piece
// rule: exactly one large red piece
// result: pass

// Round: 2
// pieces: two large red pieces
// rule: exactly one large red piece
// result: should return false

// Round: 3
// pieces: two small blue pieces and one small red piece
// rule: less than three small blue pieces
// result: pass

//        -----follow up after above 3 passed, falseed on 5th,  if given out from beginning,  I would have solved differently ----

// Round: 4
// pieces: one medium green, one small blue
// rule: at least one medium piece of any color
// result: pass

// Round: 5
// pieces: one large blue, one medium green, one small green
// rule: exactly two pieces, none of which are red
// result: should return false


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ZendoGame {

    static class Piece {
        String color;
        String size;

        public Piece(String color, String size) {
            this.color = color;
            this.size = size;
        }
    }

    public static void main(String[] args){
        ZendoGame zendoGame = new ZendoGame();
        List<Piece> round1Pieces = Arrays.asList(new Piece("red", "large"));
        Rule round1Rule = new Rule("exactly", 1,  "large", "red");
        System.out.println(zendoGame.isPassing(round1Pieces, round1Rule));

        List<Piece> round2Pieces = Arrays.asList(new Piece("red", "large"), new Piece("red", "large"));
        Rule round2Rule = new Rule("exactly", 1,  "large", "red");
        System.out.println(zendoGame.isPassing(round2Pieces, round2Rule));

        // pieces: two small blue pieces and one small red piece
        // rule: less than three small blue pieces
        List<Piece> round3Pieces = Arrays.asList(new Piece("blue", "small"), new Piece("red", "small"), new Piece("blue", "small"));
        Rule round3Rule = new Rule("less than", 3,  "small", "blue");
        System.out.println(zendoGame.isPassing(round3Pieces, round3Rule));

        // pieces: one medium green, one small blue
        // rule: at least one medium piece of any color
        List<Piece> round4Pieces = Arrays.asList(new Piece("green", "medium"), new Piece("blue", "small"));
        Rule round4Rule = new Rule("at least", 1,  "medium", "any");
        System.out.println(zendoGame.isPassing(round4Pieces, round4Rule));

        // Round: 5
        // pieces: one large blue, one medium green, one small green
        // rule: exactly two pieces, none of which are red
        // result: should return false
        // None match - false case
        List<Piece> round5Pieces = Arrays.asList(new Piece("green", "medium"), new Piece("green", "small"), new Piece("blue", "large"));
        Rule round5Rule = new Rule("exactly", 2,  "any", "not red");
        System.out.println(zendoGame.isPassing(round5Pieces, round5Rule));

        // None match - true case
        List<Piece> round6Pieces = Arrays.asList(new Piece("green", "small"), new Piece("blue", "large"));
        Rule round6Rule = new Rule("exactly", 2,  "any", "not red");
        System.out.println(zendoGame.isPassing(round6Pieces, round6Rule));
    }


    private boolean isPassing(List<Piece> pieces, Rule rule) {
        boolean isPassing = false;

        // not-ing means we need the other colors
        Set<String> allColors = Set.of("red", "blue", "green");
        Set<String> allSizes = Set.of("small", "medium", "large");
        final String colorToExclude;
        final String sizeToExclude;
        if (rule.color.contains("not")) {
            colorToExclude = rule.color.split(" ")[1];
        } else {
            colorToExclude  = "";
        }
        Set<String> colorsToCheck = allColors.stream().filter(c -> !c.equals(colorToExclude)).collect(Collectors.toSet());

        if (rule.size.contains("not")) {
            sizeToExclude = rule.size.split(" ")[1];
        } else {
            sizeToExclude = "";
        }
        Set<String> sizesToCheck = allSizes.stream().filter(c -> !c.equals(sizeToExclude)).collect(Collectors.toSet());

        Function<Rule, Predicate<Piece>> rulePredicateFunction =
                ruleClosure ->
                        (p) -> (p.color.equals(ruleClosure.color) || ruleClosure.color.equals("any") || (colorsToCheck.size() != allColors.size() && colorsToCheck.contains(p.color)))
                                && (p.size.equals(ruleClosure.size) || ruleClosure.size.equals("any") || (sizesToCheck.size() != allSizes.size() && sizesToCheck.contains(p.size)));


        String relation = rule.relation;
        switch (relation) {
            case "at least":
                isPassing = pieces.stream()
                        .filter(rulePredicateFunction.apply(rule)).count() >= rule.count;
                break;
            case "at most":
                isPassing = pieces.stream()
                        .filter(rulePredicateFunction.apply(rule)).count() <= rule.count;
                break;
            case "less than":
                isPassing = pieces.stream()
                        .filter(rulePredicateFunction.apply(rule)).count() < rule.count;
                break;
            case "greater than":
                isPassing = pieces.stream()
                        .filter(rulePredicateFunction.apply(rule)).count() > rule.count;
                break;
            case "exactly":
                isPassing = pieces.stream()
                        .filter(rulePredicateFunction.apply(rule)).count() == rule.count;
                break;
        }

        return isPassing;
    }


    private static class Rule {
        String relation;
        Integer count;
        String color;
        String size;

        public Rule(String relation, Integer count, String size, String color) {
            this.relation = relation;
            this.count = count;
            this.color = color;
            this.size = size;
        }
    }
}
