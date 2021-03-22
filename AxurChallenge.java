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
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setRequestProperty("Content-Type", "text/html");
                int status = con.getResponseCode();
                System.out.println(String.format("Got status %d", status));
                BufferedReader in = new BufferedReader(
                  new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    String processedLine = inputLine.toLowerCase(); // canonize the input
                    content.append(processedLine);
                }
                in.close();
                con.disconnect();
                // System.out.println(content);
                for (String keyword : keywords) {
                    if (content.indexOf(keyword) != -1 ? true : false) {
                        System.out.println(String.format("Found keyword '%s'", keyword));
                    }
                }
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
