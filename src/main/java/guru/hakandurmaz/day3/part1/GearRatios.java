package guru.hakandurmaz.day3.part1;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GearRatios {
  public static final String FILE_PATH =
      "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day3\\part1\\input.txt";
  private static final AtomicInteger result = new AtomicInteger(0);
  public static HashMap<Integer, String> map = new HashMap<>();

  public static void main(String[] args) {
    fillMap();
    calculateSumOfGearRatios();
    System.out.println("Result: " + result.get());
  }

  private static void fillMap() {
    try (var reader = new java.io.BufferedReader(new java.io.FileReader(FILE_PATH))) {
      int currentLine = 1;
      String line;
      while ((line = reader.readLine()) != null) {
        map.put(currentLine, line);
        currentLine++;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void calculateSumOfGearRatios() {
    map.forEach((lineNumber, lineString) -> checkLine(lineString, lineNumber));
  }

  private static void checkLine(String line, int currentLine) {
    String pattern = "\\d+";
    Pattern compiledPattern = Pattern.compile(pattern);
    Matcher matcher = compiledPattern.matcher(line);
    while (matcher.find()) {
      String foundNumber = matcher.group();
      int index = line.indexOf(foundNumber);
      line =
          line.replaceFirst(
              foundNumber, repeatCharacter(countDigits(Integer.parseInt(foundNumber)), '0'));
      int digits = countDigits(Integer.parseInt(foundNumber));
      checkNumberIsValid(index, digits, currentLine, Integer.parseInt(foundNumber));
    }
  }

  private static void checkNumberIsValid(
      int index, int digitsCount, int currentLine, int foundNumber) {
    String topRow = readCharacterFromFile(currentLine - 1, index - 1, index + digitsCount + 1);
    String rightChar =
        readCharacterFromFile(currentLine, index + digitsCount, index + digitsCount + 1);
    String leftChar = readCharacterFromFile(currentLine, index - 1, index);
    String underLine = readCharacterFromFile(currentLine + 1, index - 1, index + digitsCount + 1);
    boolean valid = isValid(topRow + rightChar + leftChar + underLine);
    if (valid) {
      result.addAndGet(foundNumber);
    }
  }

  public static int countDigits(int num) {
    if (num == 0) {
      return 1;
    }
    int count = 0;
    while (num != 0) {
      num /= 10;
      count++;
    }
    return count;
  }

  public static String readCharacterFromFile(
      int targetRow, int targetColumn, int targetLastColumn) {
    if (targetRow <= 0) {
      return "";
    }
    if (targetRow > map.size()) {
      return "";
    }
    String line = map.get(targetRow);
    if (targetColumn < 0) {
      return line.substring(targetColumn + 1, targetLastColumn);
    }
    if (targetLastColumn >= line.length()) {
      return line.substring(targetColumn);
    }

    return line.substring(targetColumn, targetLastColumn);
  }

  public static boolean isValid(String check) {
    return !check.matches("[0-9.]+");
  }

  public static String repeatCharacter(int count, char character) {
    return String.valueOf(character).repeat(Math.max(0, count));
  }
}
