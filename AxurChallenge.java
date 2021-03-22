import src.ConnectionManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;
import java.util.stream.Stream;

public class AxurChallenge {
    public static void main(String[] args) throws Exception {
        String keywordsFile = "keywords.txt";
        List<String> keywords = readKeywords(keywordsFile);
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

    private static List<String> readKeywords(String filename) {
        List<String> keywords = new LinkedList<String>();
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEach(keyword -> { System.out.println(keyword); keywords.add(keyword); } );
        } catch (IOException e) {
            System.out.println("Error reading keywords file");
        }
        return keywords;
    }

    private static boolean checkKeywords(String content,  List<String> keywords) {
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
