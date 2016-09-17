package GoldmanSachs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.jar.Pack200;

/**
 * Created by schandramouli on 9/13/16.
 */
public class SocialMedia implements Comparable {
    String name, time, message;

    public SocialMedia(String name, String time, String message) {
        this.name = name;
        this.time = time;
        this.message = message;
    }

    public static void main(String[] args) {
        // given a list of (name, time, message), find the person
        // whose messages get forwarded the most (aka) most influential
        ArrayList<SocialMedia> messages = new ArrayList<>();
        messages.add(new SocialMedia("sai", "081520" , "hi"));
        messages.add(new SocialMedia("sanjana", "091520" , "hi"));
        messages.add(new SocialMedia("sai", "101520" , "hi"));
        messages.add(new SocialMedia("seema", "111520" , "hello"));
        messages.add(new SocialMedia("sai", "121520" , "hi"));
        messages.add(new SocialMedia("seema", "131520" , "hi"));
        messages.add(new SocialMedia("swathi", "141520" , "hi"));
        messages.add(new SocialMedia("sai", "151520" , "hi"));
        messages.add(new SocialMedia("pc", "161520" , "hola"));
        messages.add(new SocialMedia("manasa", "171520" , "hi"));
        messages.add(new SocialMedia("sai", "181520" , "hi"));
        messages.add(new SocialMedia("sai", "191520" , "hi"));
        messages.add(new SocialMedia("sanjana", "101520" , "hello"));
        messages.add(new SocialMedia("ankit", "091520" , "hola"));

        System.out.println(messages);
        Collections.sort(messages);
        System.out.println(messages);

        // need a hashmaps to answer each of the three questions
        // 1. Whose messages got forwarded most
        // 2. Which time interval had most messages
        // 3. Which buzzword is sent very frequently
        // good thing is all these can be finished in 1 loop :O
        HashMap<String, ArrayList<String>> messagesForwarded = new HashMap<>();
        HashMap<String, Integer> messageFrequency = new HashMap<>();
        // trick: using good-ole array is sufficient for timeIntervals :D
        int[] timeIntervals = new int[24];
        for (SocialMedia sm : messages) {
            // first time :D easiest
            String time = sm.time;
            String message = sm.message;
            String name = sm.name;

            String hours = time.substring(0,2);
            timeIntervals[Integer.parseInt(hours)]++;
            // next messages, second-easiest
            if (messageFrequency.containsKey(message)) {
                messageFrequency.put(message, messageFrequency.get(message) + 1);
            } else {
                messageFrequency.put(message, 0);
            }

            // now the slightly more complicated one - most influential person
            // wkt, the messages are arranged by time --> we did this
            // which means, (name, message) is the only thing that matters now
            // cuz if (sai, hi) comes before (swathi, hi), it counts as a point for sai
            // so we go through messages and if not found earlier, we credit 1
            // to this person, if found earlier, then credit one to that person
            // .... BUUUUT, we just did that above!!!! but we didnt keep track of who sent the message
            // how do we address that? easy peesy, Hashmaps to the rescue
            ArrayList<String> nameList = new ArrayList<>();
            if (messagesForwarded.containsKey(message)) {
                nameList = messagesForwarded.get(message);
                nameList.add(name);
                messagesForwarded.put(message, nameList);
            } else {
                nameList.add(name);
                messagesForwarded.put(message, nameList);
            }


        }

        // still O(n), print the top three time intervals
        // we cannot sort this, cuz we will lose the hour info
        int max1 = timeIntervals[0],
            max2 = timeIntervals[0],
            max3 = timeIntervals[0],
            index1 = 0,
            index2 = 0,
            index3 = 0;
        // we could add a binary search after this for log n search and retrieving the keys
        // but i prefer doing it here, its just 3 variables extra
        for (int i = 1; i < timeIntervals.length; i++) {
            int val = timeIntervals[i];
            if (max1 >= val) {
                // no change for max1, but is max2 or 3 affected?
                if (max2 >= val) {
                    // no change for max2, but is max3 affected?
                    if (!(max3 >= val)) {
                        // reset only max3
                        max3 = val;
                        index3 = i;
                    }
                } else {
                    // uh-oh, shift max2->3
                    max3 = max2;
                    index3 = index2;
                    max2 = val;
                    index2 = i;
                }
            } else {
                // uh - ohhhhh, shift all three
                max3 = max2;
                index3 = index2;
                max2 = max1;
                index2 = index1;
                max1 = val;
                index1 = i;
            }
        }

        System.out.println(Arrays.toString(timeIntervals));
        System.out.println(max1 + " " + max2 + " " + max3);
        System.out.println("The three most frequent time intervals are");
        System.out.println(index1 + " hrs to " + (index1+1) + "hrs with " + max1 + " messages");
        System.out.println(index2 + " hrs to " + (index2+1) + "hrs with " + max2 + " messages");
        System.out.println(index3 + " hrs to " + (index3+1) + "hrs with " + max3 + " messages");
        System.out.println(messageFrequency);
        System.out.println(messagesForwarded);

    }

    @Override
    public int compareTo(Object o) {
        int x = Integer.parseInt(time);
        int y = Integer.parseInt(((SocialMedia) o).time);
        if (x == y) {
            return 0;
        } else if (x < y) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "SocialMedia{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
