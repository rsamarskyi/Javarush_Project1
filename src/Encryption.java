import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;


public class Encryption {
    private final Scanner scanner;
    private final FileService fileService;

    public Encryption(Scanner scanner, FileService fileService) {
        this.scanner = scanner;
        this.fileService = fileService;
    }

    public void readAndEncrypt() throws IOException {
        System.out.println("Enter encryption key");
        int key = Integer.parseInt(scanner.nextLine());
        List<String> strings = fileService.readFromFile();
        String output = fileService.applyLogic(strings, key);
        fileService.convertAndWriteToFile(output);
    }

}
