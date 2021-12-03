package fb_recent;

import java.util.HashMap;
import java.util.Map;

public class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        if (n < 1) return tasks.length;
        // build frequency map and highest frequency item
        int highestFrequencyItem = 0;
        int highestFrequencyRepeats = 0;
        for (char task: tasks) {
            int frequency = frequencyMap.getOrDefault(task, 0) + 1;
            frequencyMap.put(task, frequency);
            highestFrequencyItem = Math.max(highestFrequencyItem, frequency);
        }

        for (Integer integer: frequencyMap.values()) {
            if (highestFrequencyItem == integer) {
                highestFrequencyRepeats++;
            }
        }

        int minNoOfHoles = n * (highestFrequencyItem - 1);

        return Math.max(highestFrequencyItem + minNoOfHoles + highestFrequencyRepeats - 1, tasks.length);
    }

    public static void main(String[] args){
        TaskScheduler taskScheduler = new TaskScheduler();
        char[] tasks = {'A','A','A','B','B','B'};
        System.out.println(taskScheduler.leastInterval(tasks, 2));
        System.out.println(taskScheduler.leastInterval(tasks, 0));

        char[] tasks2 = {'A','A','A','A','A','A','B','C','D','E','F','G'};
        System.out.println(taskScheduler.leastInterval(tasks2, 2));

        char[] tasks3 = {'A','B','C','D','E','A','B','C','D','E'};
        System.out.println(taskScheduler.leastInterval(tasks3, 4));

        char[] tasks4 = {'A','A','A','B','B','B', 'C','C','C', 'D', 'D', 'E'};
        System.out.println(taskScheduler.leastInterval(tasks4, 2));
    }
}
