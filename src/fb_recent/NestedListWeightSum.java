package fb_recent;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NestedInteger {
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

public class NestedListWeightSum {
    public int depthSum(List<NestedInteger> nestedList) {
        return calcSum(nestedList, 1);
    }

    int calcSum(List<NestedInteger> list, int depth) {
        int sum = 0;
        for (NestedInteger ni: list) {
            if (ni.isInteger()) {
                sum += depth * ni.getInteger();
            } else {
                sum += calcSum(ni.getList(), depth + 1);
            }
        }
        return sum;
    }

    public static void main(String[] args){
        NestedListWeightSum nestedListWeightSum = new NestedListWeightSum();
        List<NestedInteger> list = new ArrayList<>();
        list.add(new NestedInteger(1));
        NestedInteger firstDepthItem = new NestedInteger();
        firstDepthItem.add(new NestedInteger(4));
        NestedInteger secondDepthItem = new NestedInteger();
        secondDepthItem.add(new NestedInteger(6));
        firstDepthItem.add(secondDepthItem);
        list.add(firstDepthItem);
//        list.add(new NestedInteger(1));


        System.out.println(nestedListWeightSum.depthSum(list));

        //[
        // [1,1],
        // 2,
        // [1,1]
        // ]
        List<NestedInteger> rootList =  new ArrayList<>();
        NestedInteger depth1List = new NestedInteger();
        depth1List.add(new NestedInteger(1));
        depth1List.add(new NestedInteger(1));
        rootList.add(depth1List);
        rootList.add(new NestedInteger(2));
        rootList.add(depth1List);

        System.out.println(nestedListWeightSum.depthSum(rootList));
    }
}
