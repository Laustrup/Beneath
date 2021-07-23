package laustrup.beneath.repositories.cache;

import java.util.HashMap;
import java.util.Map;

public class Wallet {

    private Map<String, Object> map = new HashMap<>();

    public Object getMap(String key) {
        return map.get(key);
    }

    public void setMap(String key, Object value) {
        map.replace(key, value);
    }

    public Map<String, Object> putInMap(String key, Object value) {
        map.put(key, value);
        return map;
    }

    public void has(Object value) {
        map.containsValue(value);
    }

    public void has(String key) {
        map.containsKey(key);
    }

    public void delete(String key) {
        map.remove(key);
    }

}
