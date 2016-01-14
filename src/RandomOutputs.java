/**
 * Created by schandramouli on 1/14/16.
 */
public class RandomOutputs {
    public static void main(String[] args) {
        // this will return random letters using a hashmap
        DefaultHashMap<Integer, Integer> defaultHashMap = new DefaultHashMap<>(0);
        for (int i = 0; i < 10; i++) {
            defaultHashMap.put(i, Math.random() * 10);
        }

        System.out.println(defaultHashMap);
    }
}
