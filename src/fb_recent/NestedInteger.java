package fb_recent;

import java.util.ArrayList;
import java.util.List;

public class NestedInteger {
    // Constructor initializes an empty nested list.
    List<NestedInteger> nestedIntegers = new ArrayList<>();
    Integer integer;

    public NestedInteger() {
    }

    // Constructor initializes a single integer.
    public NestedInteger(int value) {
        integer = value;
    }

    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger() {
        return nestedIntegers.size() == 0 && integer != null;
    }

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger() {
        return integer;
    }

    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value) {
        integer = value;
    }

    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni) {
        nestedIntegers.add(ni);
    }

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList() {
        return nestedIntegers;
    }

    @Override
    public String toString() {
        return "NestedInteger{\n" +
                "\tnestedIntegers=" + nestedIntegers +
                "\t, integer=" + integer +
                "\n}";
    }
}
