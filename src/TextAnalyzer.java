import java.io.IOException;
import java.util.*;

public class TextAnalyzer {
    private static ArrayList<Character> listOfCharsSource;
    private static ArrayList<Character> listOfCharsStat;
    private StringBuilder result = new StringBuilder();
    private static HashMap<Character, Integer> sourceMap = new HashMap();
    private static HashMap<Character, Integer> statsMap = new HashMap();
    private static List<String> sourceFile;

    static {
        try {
            sourceFile = FileService.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }cd

    public void analyzeText() throws IOException {

        fillAndSortMaps();

        for (String string : sourceFile) {
            if (string.length() == 0) {
                result.append(System.lineSeparator());
            }
            analyseSingleString(string);
        }
        FileService.writeToFile("C:\\1\\4.txt", result.toString());
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

    private static void fillAndSortMaps() throws IOException {
        List<String> statsFile = FileService.readFromFile();

        char[] chars = Encryption.encryptionKey.toCharArray();
        for (char aChar : chars) {
            sourceMap.put(aChar, 0);
            statsMap.put(aChar, 0);
        }

        fillStatChars(sourceFile, sourceMap);
        fillStatChars(statsFile, statsMap);
        listOfCharsSource = convertSortedMapToList(sourceMap);
        listOfCharsStat = convertSortedMapToList(statsMap);
    }

    private static void fillStatChars(List<String> strings, HashMap<Character, Integer> map) {
        for (String string : strings) {
            for (int i = 0; i < string.length(); i++) {
                fillMap(string, map, i);
            }
        }
    }

    private static void fillMap(String string, HashMap<Character, Integer> map, int i) {
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            if (entry.getKey() == string.charAt(i)) {
                int countChars = entry.getValue();
                map.put(string.charAt(i), (countChars + 1));
            }
    }

    private static ArrayList<Character> convertSortedMapToList(HashMap<Character, Integer> map) {
        Map<Character, Integer> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);
        return new ArrayList<>(sortedMap.keySet());
    }
}