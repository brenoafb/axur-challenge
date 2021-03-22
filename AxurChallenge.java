import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AxurChallenge {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        for (String s : args) {
            if (verifyURL(s)) {
                System.out.println(String.format("%s looks like a URL", s));
            } else {
                System.out.println(String.format("%s does not look like a URL", s));
            }
        }
    }

    private static boolean verifyURL(String url) {
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
