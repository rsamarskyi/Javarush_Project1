import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Decryption {
    private Scanner scanner = new Scanner(System.in);
    private static List<String> inputFile;

    static {
        try {
            inputFile = FileService.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readAndDecrypt() throws IOException {
        System.out.println("Enter encryption key");
        int offset = scanner.nextInt();
        Encryption encryption = new Encryption();

        return encryption.convertAndWriteToFile(inputFile, (Encryption.encryptionKey.length()) - (offset % Encryption.encryptionKey.length()));
    }

    public void bruteForce() throws IOException {
        for (int i = 1; i < Encryption.encryptionKey.length(); i++) {
            int numberOfAttempts = 0;
            int countValidity = 0;
            Encryption encryption = new Encryption();
            var convertedString = encryption.convertAndWriteToFile(inputFile, (Encryption.encryptionKey.length())
                    - (i % Encryption.encryptionKey.length()));
            StringBuilder sb = new StringBuilder(convertedString);
            char[] chars = sb.toString().toCharArray();

            for (int k = 0; k < chars.length - 1; k++) {
                if (Character.isWhitespace(chars[k]) ||
                        chars[k] == '.' && chars[k + 1] == ' ' ||
                        chars[k] == ',' && chars[k + 1] == ' ')
                    countValidity++;
                numberOfAttempts = i;
            }

            if (countValidity > sb.toString().length() / 10) {
                System.out.println(System.lineSeparator());
                System.out.println(sb.substring(0, 100));
                System.out.println("Number of attempts: " + numberOfAttempts);
                System.out.println(System.lineSeparator());
                break;
            }
        }
    }
}