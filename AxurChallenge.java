import src.ConnectionManager;
import src.KeywordManager;
import src.URLVerifier;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;

public class AxurChallenge {
    public static void main(String[] args) throws Exception {
        String keywordsFile = "keywords.txt";
        KeywordManager keywordManager = new KeywordManager(keywordsFile);
        for (String s : args) {
            if (URLVerifier.verify(s)) {
                URL url = new URL(s);
                String content = getPageContents(url);
                if (keywordManager.checkKeywords(content)) {
                    System.out.println("> suspicious");
                }
            } else {
                System.err.println(String.format("%s does not look like a URL", s));
            }
        }
    }

    private static String getPageContents(URL url) {
        ConnectionManager manager = new ConnectionManager();
        if (!manager.setupConnection(url)) {
            System.err.println(String.format("Error getting contents from '%s'", url.toString()));
        }

        return manager.getContents();
    }
}
