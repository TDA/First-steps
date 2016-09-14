package GoldmanSachs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.jar.Pack200;

/**
 * Created by schandramouli on 9/13/16.
 */
public class SocialMedia implements Comparable{
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
        // 3. Which message is sent very frequently
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
            String hours = sm.time.substring(0,2);
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

        System.out.println(Arrays.toString(timeIntervals));
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
