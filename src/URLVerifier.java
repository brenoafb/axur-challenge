package src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLVerifier {

  public static boolean verify(String url) {
    String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    return match(url, regex);
  }

  private static boolean match(String s, String pattern) {
    try {
      Pattern patt = Pattern.compile(pattern);
      Matcher matcher = patt.matcher(s);
      return matcher.matches();
    } catch (RuntimeException e) {
      return false;
    }
  }
}
