import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static java.awt.SystemColor.text;

public class Encryption {
    public static String encryptionKey = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,”:-—!?0123456789 ";
    private Scanner scanner = new Scanner(System.in);

    public void readAndEncrypt() throws IOException {
        System.out.println("Enter decryption key");
        int key = scanner.nextInt();
        List<String> strings = FileService.readFromFile();
        convertAndWriteToFile(strings, key);
    }

    public String convertAndWriteToFile(List<String> strings, int key) throws IOException {
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

        File file = new File(String.valueOf((Path.of("C:\\1\\2.txt"))));

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

        FileService.writeToFile(String.valueOf((Path.of("C:\\1\\2.txt"))), sb.toString());
        return sb.toString();
    }
}
