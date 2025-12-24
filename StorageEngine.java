import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StorageEngine {

    private final MemTable memTable = new MemTable();
    private final File dataFile = new File("dataFile.data");
    private final WriteAheadLog wal = new WriteAheadLog("wal.log");

    public void set(String key, String value) {
        wal.log("PUT " + key + " " + value);
        memTable.put(key, value);

        if (memTable.isFull()) {
            flushDisk();
        }
    }

    public String get(String key) {
        String val = memTable.getRaw(key);
        if (val != null) {
            return val.equals(Constants.TOMBSTONE) ? null : val;
        }
        return readFromDisk(key);
    }

    public void delete(String key) {
        wal.log("DELETE " + key);
        memTable.delete(key);
    }

    private void flushDisk() {
        System.out.println("Flushing into DISK");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, true))) {
            for (Map.Entry<String, String> e : memTable.getAll().entrySet()) {
                bw.write(e.getKey() + "=" + e.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        memTable.clear();
    }

    private String readFromDisk(String key) {
        if (!dataFile.exists()) return null;

        String latest = null;

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] kv = line.split("=", 2);
                if (kv[0].equals(key)) {
                    latest = kv[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Constants.TOMBSTONE.equals(latest) ? null : latest;
    }
}
