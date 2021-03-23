package src;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class URLManager {

  public static Optional<URL> getURL(String urlString) {
    URL url = null;
    try {
      url = new URL(urlString);
      return Optional.of(url);
    } catch (MalformedURLException e) {
      return Optional.empty();
    }
  }
}
