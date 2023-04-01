

import fb_recent.NestedInteger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class NestedIterator implements Iterator<Integer> {
    List<Integer> integerList = new ArrayList<>();
    int index = 0;

    public NestedIterator(List<NestedInteger> nestedList) {
        // need to flatten here
        this.flattenLists(nestedList, integerList);
    }

    private void flattenLists(List<NestedInteger> nestedList, List<Integer> integerList) {
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger()) {
                integerList.add(ni.getInteger());
            } else {
                flattenLists(ni.getList(), integerList);
            }
        }
    }

    @Override
    public Integer next() {
        return integerList.get(index++);
    }

    @Override
    public boolean hasNext() {
        return index < integerList.size();
    }

    @Override
    public String toString() {
        return "NestedIterator{" +
                "integerList=" + integerList +
                '}';
    }

    public static void main(String[] args) {
        List<NestedInteger> list = new ArrayList<>();
        list.add(new NestedInteger(1));
        NestedInteger firstDepthItem = new NestedInteger();
        firstDepthItem.add(new NestedInteger(4));
        NestedInteger secondDepthItem = new NestedInteger();
        secondDepthItem.add(new NestedInteger(6));
        firstDepthItem.add(secondDepthItem);
        list.add(firstDepthItem);
        secondDepthItem.add(new NestedInteger(6));
        firstDepthItem.add(secondDepthItem);
        list.add(firstDepthItem);
        NestedIterator nestedIterator = new NestedIterator(list);
//        while (nestedIterator.hasNext()) {
            System.out.println(nestedIterator);
//        }

    }
}