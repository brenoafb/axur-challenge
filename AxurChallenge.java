import src.ConnectionManager;
import src.KeywordManager;
import src.URLManager;

import java.util.Optional;
import java.net.URL;

public class AxurChallenge {

  public static void main(String[] args) throws Exception {
    String keywordsFile = "keywords.txt";
    KeywordManager keywordManager = new KeywordManager(keywordsFile);
    for (String s : args) {
      verifyURL(s, keywordManager);
    }
  }

  private static void verifyURL(String s, KeywordManager keywordManager) {
    Optional<URL> urlMaybe = URLManager.getURL(s);
    if (!urlMaybe.isPresent()) {
      System.err.println("Error parsing URL");
      return;
    }
    urlMaybe.ifPresent(
      (url) -> { handleURL(url, keywordManager); }
    );
  }

  private static void handleURL(URL url, KeywordManager keywordManager) {
    Optional<String> contentsMaybe = getPageContents(url);
    if (!contentsMaybe.isPresent()) {
      System.err.println("Error fetching contents from URL");
      return;
    }
    contentsMaybe.ifPresent(
      (contents) -> { handleContents(contents, keywordManager); }
    );
  }

  private static void handleContents(String contents, KeywordManager keywordManager) {
    if (keywordManager.checkKeywords(contents)) {
      System.out.println("suspicious");
    } else {
      System.out.println("safe");
    }
  }

  private static Optional<String> getPageContents(URL url) {
    ConnectionManager manager = new ConnectionManager();
    if (!manager.setupConnection(url)) {
      return Optional.empty();
    }

    return manager.getContents();
  }
}
