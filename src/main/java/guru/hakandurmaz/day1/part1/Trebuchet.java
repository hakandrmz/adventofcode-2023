package guru.hakandurmaz.day1.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Trebuchet {

    private static final String FILE_PATH = "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day1\\part1"
        + "\\calibration.txt";

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
        int firstNumericValue = -1;
        int lastNumericValue = -1;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isDigit(currentChar)) {
                firstNumericValue = Character.getNumericValue(currentChar);
                break;
            }
        }
        for (int i = text.length() - 1; i >= 0; i--) {
            char currentChar = text.charAt(i);
            if (Character.isDigit(currentChar)) {
                lastNumericValue = Character.getNumericValue(currentChar);
                break;
            }
        }
        if (firstNumericValue != -1 && lastNumericValue != -1) {
            return firstNumericValue * 10 + lastNumericValue;
        } else {
            throw new RuntimeException("No numeric value found");
        }
    }

}
