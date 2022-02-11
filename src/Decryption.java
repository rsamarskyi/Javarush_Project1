import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Decryption {
    private final Scanner scanner;
    private List<String> inputFile;
    private final FileService fileService;

    public Decryption(Scanner scanner, FileService fileService) {
        this.scanner = scanner;
        this.fileService = fileService;
        try {
            inputFile = fileService.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readAndDecrypt() throws IOException {
        System.out.println("Enter encryption key");
        int offset = Integer.parseInt(scanner.nextLine());
        int key = (FileService.encryptionKey.length()) - (offset % FileService.encryptionKey.length());
        String output = fileService.applyLogic(inputFile, key);
        return fileService.convertAndWriteToFile(output);
    }

    public void bruteForce() throws IOException {
        for (int i = 1; i < FileService.encryptionKey.length(); i++) {
            int numberOfAttempts = 0;
            int countValidity = 0;
            var convertedString = fileService.applyLogic(inputFile, (FileService.encryptionKey.length())
                    - (i % FileService.encryptionKey.length()));
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
                fileService.convertAndWriteToFile(sb.toString());
                break;
            }
        }
    }
}