import src.ConnectionManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;

public class AxurChallenge {
    public static void main(String[] args) throws Exception {
        String keywords[] = {"black friday", "promoção", "senha"};
        for (String s : args) {
            if (verifyURL(s)) {
                System.out.println(String.format("%s looks like a URL", s));
                URL url = new URL(s);
                String content = getPageContents(url);
                if (checkKeywords(content, keywords)) {
                    System.out.println("> suspicious");
                }
            } else {
                System.out.println(String.format("%s does not look like a URL", s));
            }
        }
    }

    private static boolean checkKeywords(String content,  String[] keywords) {
        for (String keyword : keywords) {
            if (content.indexOf(keyword) != -1 ? true : false) {
                System.out.println(String.format("Found keyword '%s'", keyword));
                return true;
            }
        }
        return false;
    }

    private static String getPageContents(URL url) {
        ConnectionManager manager = new ConnectionManager();
        if (!manager.setupConnection(url)) {
            System.out.println(String.format("Error getting contents from '%s'", url.toString()));
        }

        return manager.getContents();
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
