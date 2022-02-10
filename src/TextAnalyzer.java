import java.io.IOException;
import java.util.*;

public class TextAnalyzer {
    private ArrayList<Character> listOfCharsSource;
    private ArrayList<Character> listOfCharsStat;
    private StringBuilder result = new StringBuilder();
    private HashMap<Character, Integer> sourceMap = new HashMap();
    private HashMap<Character, Integer> statsMap = new HashMap();
    private List<String> sourceFile;
    private FileService fileService;
    private Scanner scanner;

    public TextAnalyzer(FileService fileService, Scanner scanner) {
        this.fileService = fileService;
        this.scanner = scanner;
        try {
            sourceFile = fileService.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void analyzeText() throws IOException {

        fillAndSortMaps();

        for (String string : sourceFile) {
            if (string.length() == 0) {
                result.append(System.lineSeparator());
            }
            analyseSingleString(string);
        }

        fileService.convertAndWriteToFile(result.toString());
    }

    private void analyseSingleString(String string) {
        for (int i = 0; i < string.length(); i++) {
            for (int j = 0; j < listOfCharsSource.size(); j++) {
                if (string.charAt(i) == listOfCharsSource.get(j)) {
                    result.append(listOfCharsStat.get(j));
                    if (i == string.length() - 1) {
                        result.append(System.lineSeparator());
                    }
                }
            }
        }
    }

    private void fillAndSortMaps() throws IOException {
        List<String> statsFile = fileService.readFromFile();

        char[] chars = FileService.encryptionKey.toCharArray();
        for (char aChar : chars) {
            sourceMap.put(aChar, 0);
            statsMap.put(aChar, 0);
        }

        fillStatChars(sourceFile, sourceMap);
        fillStatChars(statsFile, statsMap);
        listOfCharsSource = convertSortedMapToList(sourceMap);
        listOfCharsStat = convertSortedMapToList(statsMap);
    }

    private void fillStatChars(List<String> strings, HashMap<Character, Integer> map) {
        for (String string : strings) {
            for (int i = 0; i < string.length(); i++) {
                fillMap(string, map, i);
            }
        }
    }

    private void fillMap(String string, HashMap<Character, Integer> map, int i) {
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            if (entry.getKey() == string.charAt(i)) {
                int countChars = entry.getValue();
                map.put(string.charAt(i), (countChars + 1));
            }
    }

    private ArrayList<Character> convertSortedMapToList(HashMap<Character, Integer> map) {
        Map<Character, Integer> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);
        return new ArrayList<>(sortedMap.keySet());
    }
}