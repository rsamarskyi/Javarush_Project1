import java.io.IOException;
import java.util.Scanner;

public class Meny {
    Scanner scanner = new Scanner(System.in);

    public void startMeny() throws IOException {
        boolean cont = true;
        while (cont) {
            System.out.println("Please choose the operation");
            System.out.println("Press 1 to decrypt file");
            System.out.println("Press 2 to encrypt file");
            System.out.println("Press 3 to exit");
            int i = scanner.nextInt();
            switch (i) {
                case 1:
                    System.out.println("Press 1 if you have a decryption key or press 2 if you want to crack an encryption");
                    int j = scanner.nextInt();
                    if (j == 1) {
                        Decryption decryption = new Decryption();
                        decryption.readAndDecrypt();
                        break;
                    } else if (j == 2) {
                        System.out.println("Press 1 if you want to use BruteForce to encrypt or press 2 if you want to use text analysis");
                        int k = scanner.nextInt();
                        if (k == 1) {
                            Decryption decryption = new Decryption();
                            decryption.bruteForce();
                        } else if (k == 2) {
                            TextAnalyzer textAnalyzer = new TextAnalyzer();
                            textAnalyzer.analyzeText();
                        } else
                            System.out.println("Wrong input. Please Try again");
                        break;
                    } else
                        System.out.println("Wrong input. Please Try again");
                    break;
                case 2:
                    Encryption encryption = new Encryption();
                    encryption.readAndEncrypt();
                    break;
                case 3:
                    cont = false;
                    break;
                default:
                    System.out.println("Wrong input. Please Try again");
            }
        }
    }
}
