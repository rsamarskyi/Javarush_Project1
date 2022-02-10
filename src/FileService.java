import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.awt.SystemColor.text;

public class FileService {
    public static String encryptionKey = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,”:-—!?0123456789 ";
    private final Scanner scanner;

    public FileService(Scanner scanner) {
        this.scanner = scanner;
    }

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

    public List<String> readFromFile() throws IOException {
        System.out.println("Enter source file path");
        String input = scanner.nextLine();

        InputStream reader = Files.newInputStream(Path.of(input));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));

        Stream<String> lines = bufferedReader.lines();

        return lines.toList();
    }

    public String applyLogic(List<String> strings, int key) {

        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            if (string.length() == 0) {
                sb.append(System.lineSeparator());
            }
            for (int i = 0; i < string.length(); i++) {

                int charIndex = encryptionKey.indexOf(string.charAt(i));

                int newCharIndex = (charIndex + key) % encryptionKey.length();
                sb.append(encryptionKey.charAt(newCharIndex));
                if (i == string.length() - 1) {
                    sb.append(System.lineSeparator());
                }
            }
        }
        return sb.toString();
    }

    public String convertAndWriteToFile(String whatToWrite) throws IOException {
        System.out.println("Enter path to save file");
        String path = scanner.nextLine();

        File file = new File(String.valueOf((Path.of(path))));

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

        FileService.writeToFile(String.valueOf((Path.of(path))), whatToWrite);
        System.out.println("File successfully saved");
        return whatToWrite;
    }
}
