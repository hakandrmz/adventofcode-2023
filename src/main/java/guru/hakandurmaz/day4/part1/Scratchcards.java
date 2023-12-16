package guru.hakandurmaz.day4.part1;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scratchcards {
  public static final String FILE_PATH =
      "D:\\projects\\adventofcode-2023\\src\\main\\java\\guru\\hakandurmaz\\day4\\part1\\input.txt";
  private static final AtomicDouble result = new AtomicDouble(0);
  public static HashMap<String, String> map = new LinkedHashMap<>();

  public static void main(String[] args) {
    fillMap();
    calculateResult();
    System.out.println("Result: " + result.get());
  }

  private static void calculateResult() {
    map.forEach(Scratchcards::calculateLine);
  }

  private static void calculateLine(String key, String value) {
    String[] split = value.split("\\|");
    String winningNumber = split[0];
    String allNumber = split[1];
    Set<Integer> winningNumbers = new HashSet<>();
    Set<Integer> allNumbers = new HashSet<>();
    String pattern = "\\d+";
    Pattern compiledPattern = Pattern.compile(pattern);
    Matcher winsMatcher = compiledPattern.matcher(winningNumber);
    Matcher losesMatcher = compiledPattern.matcher(allNumber);
    while (winsMatcher.find()) {
      winningNumbers.add(Integer.parseInt(winsMatcher.group()));
    }
    while (losesMatcher.find()) {
      allNumbers.add(Integer.parseInt(losesMatcher.group()));
    }
    int retainCount = getRetainCount(allNumbers, winningNumbers);
    if (retainCount == 0) {
      return;
    }
    result.addAndGet(Math.pow(2, retainCount - 1));
  }

  private static void fillMap() {
    try (var reader = new java.io.BufferedReader(new java.io.FileReader(FILE_PATH))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(":");
        map.put(split[0].trim() + ":", split[1].trim());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static int getRetainCount(Set<Integer> all, Set<Integer> win) {
    Set<Integer> retain = new HashSet<>(all);
    retain.retainAll(win);
    return retain.size();
  }
}
