package guru.hakandurmaz.day1.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Trebuchet {

    private static final String FILE_PATH = "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day1\\part2\\calibration"
        + ".txt";
    private static final Set<String> set = new HashSet<>(
        Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
            "nine"));

    public static void main(String[] args) {
        int sum = calculateSumFromFile();
        System.out.println("Result: " + sum);
    }

    private static int calculateSumFromFile() {
        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int n = processFirstAndLastNumericValue(line);
                if (n != -1) {
                    sum += n;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
        return sum;
    }

    private static int processFirstAndLastNumericValue(String text) {
        AtomicInteger firstNumericValue = new AtomicInteger(-1);
        AtomicInteger lastNumericValue = new AtomicInteger(-1);

        for (int i = 0; i < text.length() + 1; i++) {
            String substring = text.substring(0, i);
            if (getContainedNumber(firstNumericValue, substring)) {
                break;
            }
        }

        for (int i = text.length() - 1; i >= 0; i--) {
            String substring = text.substring(i);
            if (getContainedNumber(lastNumericValue, substring)) {
                break;
            }
        }
        return firstNumericValue.get() * 10 + lastNumericValue.get();
    }

    private static boolean getContainedNumber(AtomicInteger firstNumericValue, String substring) {
        Optional<String> number = set.stream().filter(substring::contains).findFirst();
        if (number.isPresent()) {
            try {
                firstNumericValue.set(Integer.parseInt(number.get()));
                return true;
            } catch (NumberFormatException e) {
                firstNumericValue.set(parseEnglishNumber(number.get()));
                return true;
            }
        }
        return false;
    }

    public static int parseEnglishNumber(String numberText) {
        try {
            return findNumericValue(numberText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Not a number: " + numberText);
        }
    }

    public static int findNumericValue(String numberRepresentation) {
        numberRepresentation = numberRepresentation.toLowerCase();

        switch (numberRepresentation) {
            case "zero":
                return 0;
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
            case "ten":
                return 10;
            case "eleven":
                return 11;
            case "twelve":
                return 12;
            case "thirteen":
                return 13;

            default:
                return -1;
        }
    }
}
