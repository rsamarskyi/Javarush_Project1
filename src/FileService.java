import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileService {
    private static Scanner scanner = new Scanner(System.in);
    public static void writeToFile(String fileName, String text) {

        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readFromFile() throws IOException {
        System.out.println("Enter source file path");
        String input = scanner.nextLine();

        InputStream reader = Files.newInputStream(Path.of(input));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));

        Stream<String> lines = bufferedReader.lines();

        return lines.toList();
    }
}
