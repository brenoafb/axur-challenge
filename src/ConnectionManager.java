package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {
  private HttpURLConnection con;

  public boolean setupConnection(URL url) {
    try {
      this.con = (HttpURLConnection) url.openConnection();
      this.con.setConnectTimeout(5000);
      this.con.setReadTimeout(5000);
      this.con.setRequestProperty("Accept-Charset", "UTF-8");
      this.con.setRequestProperty("Content-Type", "text/html");
      return (this.con.getResponseCode() == HttpURLConnection.HTTP_OK);
    } catch (java.net.SocketTimeoutException e) {
      System.out.println("Timeout");
      return false;
    } catch (java.io.IOException e) {
      System.out.println("IOException");
      return false;
    }
  }

  public String getContents() {
    try {
      InputStreamReader reader = new InputStreamReader(con.getInputStream());
      System.out.println(String.format("Encoding: %s", reader.getEncoding()));
      BufferedReader in = new BufferedReader(reader);
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        String processedLine = inputLine.toLowerCase(); // canonize the input
        content.append(processedLine);
      }
      in.close();
      con.disconnect();
      return content.toString();
    } catch (IOException e) {
      System.out.println("Error fetching page contents");
      return "";
    }
  }
}