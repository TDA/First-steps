import java.util.HashMap;

/**
 * Created by schandramouli on 1/10/16.
 */
public class DefaultHashMap<K, V> extends HashMap {
    // this is to simulate the default hashmaps of ruby/python
    protected V defaultValue;

    public DefaultHashMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Object get(Object key) {
        // return the value if it exists, else, return default value
        return containsKey(key)? super.get(key) : defaultValue;
    }
}
