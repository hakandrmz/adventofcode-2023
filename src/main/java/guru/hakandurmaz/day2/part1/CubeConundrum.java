package guru.hakandurmaz.day2.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CubeConundrum {

    public static final String FILE_PATH = "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day2\\part1\\input.txt";
    static HashMap<String, Integer> hashMap = new HashMap<>();

    public static void main(String[] args) {
        int result = calculateSumOfGameIds();
        System.out.println("Result: " + result);
    }

    private static int calculateSumOfGameIds() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            var pattern = Pattern.compile("\\b\\d+\\s+\\w+\\b");

            while ((line = reader.readLine()) != null) {
                var matcher = pattern.matcher(line);
                while (matcher.find()) {
                    var grouped = matcher.group();
                    var color = grouped.split(" ")[1];
                    var number = grouped.split(" ")[0];
                    //TODO
                    if (!hashMap.containsKey(color)) {
                        hashMap.put(color, Integer.parseInt(number));
                    } else {
                        hashMap.put(color, hashMap.get(color) + Integer.parseInt(number));
                    }
                }

                var red = hashMap.get("red");
                var green = hashMap.get("green");
                var blue = hashMap.get("blue");
                boolean checked = checkIfGameIsPossible(red, green, blue);
                hashMap.clear();
                if (checked) {
                    int i = extractGameNumber(line);
                    count += i;
                }
            }
            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkIfGameIsPossible(Integer red, Integer green, Integer blue) {
        return red <= 12 && green <= 13 && blue <= 14;
    }

    private static int extractGameNumber(String input) {
        var pattern = Pattern.compile("Game (\\d+):");
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}