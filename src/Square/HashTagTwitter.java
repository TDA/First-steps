package Square;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

public class HashTagTwitter {
    String HASHTAG_PRECEDED_BY_SPACE = "\\s*?(?=#)";

    Set<String> getHashTags(String tweet) {
        Set<String> hashtags = new HashSet<>();
        String[] allTags = tweet.split(HASHTAG_PRECEDED_BY_SPACE);
        System.out.println(Arrays.toString(allTags));
        for (String tag : allTags) {
            if (tag.startsWith("#")) {
                String aHashTag = tag.split(" ")[0];
                hashtags.add(aHashTag.substring(1));
            }
        }
        return hashtags;
    }

    public static void main(String[] args){
        HashTagTwitter hashTagTwitter = new HashTagTwitter();
        Map<String, Integer> hashtagCounter = new HashMap<>();
        TreeMap<Integer, Set<String>> tagStream = new TreeMap<>((a, b) -> b - a); // store in descending order for top k queries

        String[] tweets = {
                "I went to slap sai sai today. #ownership hahaha #saisai #lili",
                "I went to slap sai sai today. #ownership #ownership #ownership",
                "I went to slap sai sai today. #li #sai #saisai",
                "I went to slap sai sai today. #sai",
                "I went to slap sai sai today. #saisai"
        };
        for (String tweet : tweets) {
            Set<String> hashTags = hashTagTwitter.getHashTags(tweet);
            for (String tag : hashTags) {
                int count = hashtagCounter.getOrDefault(tag, 0) + 1;
                hashtagCounter.put(tag, count);
                Set<String> stringSet = tagStream.getOrDefault(count, new HashSet<>());
                stringSet.add(tag);
                tagStream.put(count, stringSet);
            }
        }

        System.out.println(hashtagCounter);
        System.out.println(hashTagTwitter.getTopK(tagStream, 2));

    }

    private Set<String> getTopK(TreeMap<Integer, Set<String>> tagStream, int k) {
        Set<String> hashtags = new HashSet<>();
        int count = 0;
        NavigableSet<Integer> integers = tagStream.navigableKeySet();
        System.out.println(integers);
        for (Integer key: integers) {
            count++;
            hashtags.addAll(tagStream.get(key));
            if (count >= k) break;
        }
        return hashtags;
    }

    class HashTag {
        String tag;
        int count;
    }
}
