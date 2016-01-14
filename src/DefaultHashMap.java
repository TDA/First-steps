import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by schandramouli on 1/10/16.
 */
//"Implementation of default hashmaps like from ruby"

public class DefaultHashMap<K, V> extends HashMap {
    // this is to simulate the default hashmaps of ruby/python
    protected V defaultValue;

    public DefaultHashMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Object get(Object key) {
        // return the value if it exists, else, return default value
        return  containsKey(key)? super.get(key) : defaultValue;
    }

    public static void main(String[] args) {
        // test driver for defaulthashmaps
        DefaultHashMap<Character, Integer> defaultHashMap = new DefaultHashMap<>(0);

        String s = "abcdefghijklmnopqrstuvabcdef";

        //seems to work
        for (Character c: s.toCharArray()) {
            defaultHashMap.put(c, (int) defaultHashMap.get(c) + 1);
        }

        System.out.println(defaultHashMap);

    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return (Set<Entry<K, V>>) super.entrySet();
    }
}
