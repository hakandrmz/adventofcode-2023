package guru.hakandurmaz.day2.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CubeConundrumPart2 {

    public static final String FILE_PATH = "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day2\\part2\\input.txt";
    static HashMap<String, Integer> hashMap = new HashMap<>();

    public static void main(String[] args) {
        int result = calculateSumOfCubes();
        System.out.println("Result: " + result);
    }

    private static int calculateSumOfCubes() {
        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            var pattern = Pattern.compile("\\b\\d+\\s+\\w+\\b");

            while ((line = reader.readLine()) != null) {
                var matcher = pattern.matcher(line);
                while (matcher.find()) {
                    var grouped = matcher.group();
                    var color = grouped.split(" ")[1];
                    var number = grouped.split(" ")[0];
                    if (!hashMap.containsKey(color)) {
                        hashMap.put(color, Integer.parseInt(number));
                    } else {
                        hashMap.put(color, hashMap.get(color) > Integer.parseInt(number) ? hashMap.get(color) : Integer.parseInt(number));
                    }
                }
                Integer sumOfLine = hashMap.values()
                    .stream()
                    .reduce(1, (a, b) -> a * b);
                sum += sumOfLine;
                hashMap.clear();
            }
            return sum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}