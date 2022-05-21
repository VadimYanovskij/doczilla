import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FilesMerger {

    private final String pathOfRootFolder;
    private final String pathOfResultFile;

    public FilesMerger(String rootFolderPath, String resultFilePath) {
        this.pathOfRootFolder = rootFolderPath;
        this.pathOfResultFile = resultFilePath;
    }

    public void doMerge() {
        List<Path> files = takeSortedFiles();
        Path resultFilePath = takeFileToWrite();
        mergeFilesContent(files, resultFilePath);
    }

    private List<Path> takeSortedFiles() {
        List<Path> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(pathOfRootFolder))
                    .filter(Files::isRegularFile)
                    .sorted()
                    .forEach(files::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    private Path takeFileToWrite() {
        Path resultFilePath = Paths.get(pathOfResultFile);
        File resultFile = new File(pathOfResultFile);
        if (resultFile.isFile() && !resultFile.isDirectory()) {
            try {
                Files.newBufferedWriter(resultFilePath, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createFile(resultFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultFilePath;
    }

    private void mergeFilesContent(List<Path> files, Path resultFilePath) {
        if (files != null && resultFilePath != null) {
            files.forEach(file -> {
                List<String> lines = new ArrayList<>();
                try {
                    lines.addAll(Files.readAllLines(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Files.write(resultFilePath, lines, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Wrong arguments!");
        }

    }
}
