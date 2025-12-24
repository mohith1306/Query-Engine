import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteAheadLog {

    private final File walFile;
    private BufferedWriter writer;

    public WriteAheadLog(String path) {
        try {
            this.walFile = new File(path);
            if (!walFile.exists()) {
                walFile.createNewFile();
            }
            this.writer = new BufferedWriter(new FileWriter(walFile, true));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize WAL", e);
        }
    }
    public synchronized void log(String entry) {
        try {
            writer.write(entry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to WAL", e);
        }
    }
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
           
        }
    }
}
