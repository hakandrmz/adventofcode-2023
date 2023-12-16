package guru.hakandurmaz.day3.part2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;

public class GearRatios {
  public static final String FILE_PATH =
      "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day3\\part2\\input.txt";
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
    int indexOf = 1;
    while (indexOf > 0) {
      indexOf = line.indexOf("*");
      if (indexOf == -1) {
        return;
      }
      line = line.replaceFirst("\\*", "0");
      String joinedNumbers = getJoinedNumbers(currentLine, indexOf);
      long count = Arrays.stream(joinedNumbers.split("-")).filter(s -> !s.trim().isEmpty()).count();
      int reduce =
          Arrays.stream(joinedNumbers.split("-"))
              .map(String::trim)
              .filter(s -> !s.isEmpty())
              .mapToInt(Integer::parseInt)
              .reduce(1, (a, b) -> a * b);
      if (count > 1) {
        result.addAndGet(reduce);
      }
    }
  }

  public static String getJoinedNumbers(int currentLine, int indexOf) {
    String bottomLeft = "", bottomRight = "", topLeft = "", topRight = "";
    String top = extractNumberAtIndex(map.get(currentLine - 1), indexOf);
    if (StringUtils.isEmpty(top)) {
      topLeft = extractNumberAtIndex(map.get(currentLine - 1), indexOf - 1);
      topRight = extractNumberAtIndex(map.get(currentLine - 1), indexOf + 1);
    }
    String left = extractNumberAtIndex(map.get(currentLine), indexOf - 1);
    String right = extractNumberAtIndex(map.get(currentLine), indexOf + 1);
    String bottom = extractNumberAtIndex(map.get(currentLine + 1), indexOf);
    if (StringUtils.isEmpty(bottom)) {
      bottomLeft = extractNumberAtIndex(map.get(currentLine + 1), indexOf - 1);
      bottomRight = extractNumberAtIndex(map.get(currentLine + 1), indexOf + 1);
    }
    return String.join("-", top, left, right, bottom, topLeft, topRight, bottomLeft, bottomRight);
  }

  private static String extractNumberAtIndex(String inputString, int targetIndex) {
    if (!Character.isDigit(inputString.charAt(targetIndex))) {
      return "";
    }
    StringBuilder numberBuilder = new StringBuilder();
    for (int i = targetIndex; i >= 0; i--) {
      char currentChar = inputString.charAt(i);
      if (Character.isDigit(currentChar)) {
        numberBuilder.insert(0, currentChar);
      } else {
        break;
      }
    }
    for (int i = targetIndex + 1; i < inputString.length(); i++) {
      char currentChar = inputString.charAt(i);
      if (Character.isDigit(currentChar)) {
        numberBuilder.append(currentChar);
      } else {
        break;
      }
    }
    return numberBuilder.toString();
  }
}
