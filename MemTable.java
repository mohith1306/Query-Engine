import java.util.Map;
import java.util.TreeMap;

public class MemTable {

    private static final String TOMBSTONE = "__TOMBSTONE__";
    private static final int MAX_SIZE = 5;

    private final TreeMap<String, String> map = new TreeMap<>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        String val = map.get(key);
        if (val == null || val.equals(TOMBSTONE)) {
            return null;
        }
        return val;
    }

    public void delete(String key) {
        map.put(key, TOMBSTONE);
    }

    public String getRaw(String key) {
        return map.get(key);
    }

    public boolean isFull() {
        return map.size() >= MAX_SIZE;
    }

    public void clear() {
        map.clear();
    }

    public Map<String, String> getAll() {
        return new TreeMap<>(map);
    }
}
