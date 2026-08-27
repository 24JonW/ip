import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath= Path.of(filePath);
    }

    public void save(Task[] tasks, int itemCount) throws IOException {
        Files.createDirectories(filePath.getParent());

        StringBuilder fileContents = new StringBuilder();
        for (int i = 0; i < itemCount; i++) {
            fileContents.append(tasks[i].toFileString()).append(System.lineSeparator());
        }
        Files.writeString(filePath, fileContents.toString(), StandardCharsets.UTF_8);
    }

    /**
     * Reads tasks from the file and returns them as a List of raw strings.
     * If the file doesn't exist, it returns an empty list.
     */
    public List<String> load() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>(); // Return empty list if no save file exists yet
        }
        return Files.readAllLines(filePath, StandardCharsets.UTF_8);
    }
}
