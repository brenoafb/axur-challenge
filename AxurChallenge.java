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
            Optional<URL> url = URLManager.getURL(s);
            if (url.isPresent()) {
                String content = getPageContents(url.get());
                if (keywordManager.checkKeywords(content)) {
                    System.out.println("suspicious");
                } else {
                    System.out.println("safe");
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
