package GoldmanSachs;

import java.util.ArrayList;
import java.util.Collections;

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

        // need 3 hashmaps to answer each of the three questions
        // 1. Whose messages got forwarded most
        // 2. Which time interval had most messages
        // 3. Which message is sent very frequently
        // good thing is all these can be finished in 1 loop :O

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
