package Square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;


class Query {
    String relation;
    String field;
    String value;

    public Query(String field, String relation, String value) {
        this.relation = relation;
        this.field = field;
        this.value = value;
    }
}

class Cat implements Comparable<Cat> {
    int height;
    int weight;
    String name;

    public Cat(String name, int height, int weight) {
        this.height = height;
        this.weight = weight;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "height=" + height +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Cat cat) {
        int heightComparison = Long.compare(this.height, cat.height);
        if (heightComparison == 0 || this.height == 0 || cat.height == 0) {
            int weightComparison = Long.compare(this.weight, cat.weight);
            if (weightComparison == 0 || this.weight == 0 || cat.weight == 0) {
                return this.name.compareTo(cat.name);
            } else {
                return weightComparison;
            }
        } else {
            return heightComparison;
        }
    }
}
public class Cats {
    TreeMap<Integer, Set<String>> catsByHeight = new TreeMap<>();
    TreeMap<Integer, Set<String>> catsByWeight = new TreeMap<>();
    Map<String, Cat> cats = new HashMap<>();

    public void addCat(Cat cat) {
        this.cats.put(cat.name, cat);
        Set<String> orDefault = catsByWeight.getOrDefault(cat.weight, new HashSet<>());
        orDefault.add(cat.name);
        this.catsByWeight.put(cat.weight, orDefault);
        Set<String> orDefault2 = catsByWeight.getOrDefault(cat.height, new HashSet<>());
        orDefault2.add(cat.name);
        this.catsByHeight.put(cat.height, orDefault2);
    }

    public SortedSet<Cat> complexQuery(List<Query> queries, String operation) throws Exception {
        List<SortedSet<Cat>> allQueryResults = new ArrayList<>();
        for (Query q : queries) {
            allQueryResults.add(this.query(q));
        }
        SortedSet<Cat> results = Collections.emptySortedSet();

        if (operation.equals("AND")) {
            results = allQueryResults.stream().reduce((a, b) -> {
                a.retainAll(b);
                return a;
            }).orElse(results);
        } else if (operation.equals("OR")) {
            results = allQueryResults.stream().reduce((a, b) -> {
                a.addAll(b);
                return a;
            }).orElse(results);
        }

        return results;
    }

    public SortedSet<Cat> query(Query query) throws Exception {
        String relation = query.relation;
        String field = query.field;
        int value = Integer.parseInt(query.value);
        TreeMap<Integer, Set<String>> mapToSearch;
        switch (field) {
            case "height":
                mapToSearch = catsByHeight;
                break;
            case "weight":
                mapToSearch = catsByWeight;
                break;
            default:
                throw new Exception("Unknown field");
        }

        switch (relation) {
            case "<":
                return mapToSearch.headMap(value, false)
                        .values()
                        .stream()
                        .flatMap(Collection::stream)
                        .map(s -> this.cats.get(s))
                        .collect(Collectors.toCollection(TreeSet::new));
            case ">":
                return mapToSearch.tailMap(value, false)
                        .values()
                        .stream()
                        .flatMap(Collection::stream)
                        .map(s -> this.cats.get(s))
                        .collect(Collectors.toCollection(TreeSet::new));
            case "=":
                return mapToSearch.get(value).stream().map(s -> this.cats.get(s)).collect(Collectors.toCollection(TreeSet::new));
            default:
                throw new Exception("Unsupported operation");
        }
    }

    public static void main(String[] args) throws Exception {
        Cats cats = new Cats();
        cats.addCat(new Cat("SaiSai", 4200, 210));
        cats.addCat(new Cat("Liya", 3400, 115));
        cats.addCat(new Cat("Jingo", 3700, 145));
        cats.addCat(new Cat("Tongyu", 4500, 135));

//        System.out.println(cats.cats);

        System.out.println("Cats of height > 4100 " +  cats.query(new Query("height",">",  "4100")));
        System.out.println("Cats of weight >  150 " + cats.query(new Query("weight",">",  "150")));
        System.out.println("Cats of height <  3500 " + cats.query(new Query("height","<",  "3500")));
        System.out.println("Cats of weight <  150 " + cats.query(new Query("weight","<",  "150")));
        System.out.println("Cats of height =  3700 " + cats.query(new Query("height","=",  "3700")));
        // height > 4100 && weight > 150
        System.out.println(
                cats.complexQuery(
                        List.of(new Query("height",">",  "4100"),
                                new Query("weight",">",  "150"))
                , "AND")
        );

        System.out.println(
                cats.complexQuery(
                        List.of(new Query("height",">",  "3400"),
                                new Query("weight",">",  "150"))
                        , "OR")
        );
    }
}
