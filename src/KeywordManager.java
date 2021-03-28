package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class KeywordManager {

  private List<String> keywords;

  public KeywordManager(String keywordsFilename) {
    try {
      keywords = readKeywords(keywordsFilename);
    } catch (IOException e) {
      System.err.println("Error loading keywords file; loading default keywords");
      keywords = Arrays.asList(new String[]{"black friday", "promoção", "senha"});
    }
  }

  public boolean checkKeywords(String content) {
    for (String keyword : keywords) {
      if (content.indexOf(keyword) != -1 ? true : false) {
        return true;
      }
    }
    return false;
  }

  private List<String> readKeywords(String filename) throws IOException {
    List<String> keywordsList = new LinkedList<String>();
    Stream<String> stream = Files.lines(Paths.get(filename));
    stream.filter(keyword -> keyword.length() > 0)
          .forEach(keywordsList::add);
    return keywordsList;
  }
}
