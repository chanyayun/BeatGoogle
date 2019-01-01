import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class KeywordCounter {
	private String url;
	private String content;

	public KeywordCounter(String url) {
		this.url = url;
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.url);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			retVal += line;
		}

		return retVal;
	}

	public int countKeyword(String keyword) throws IOException {
		if (content == null) {
			content = fetchContent();
		}

		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int freq = 0;
		int num = content.indexOf(keyword, 0);
		while (num >= 0) {
			num = content.indexOf(keyword, num + keyword.length());
			freq++;
		}
		return freq;
	}
}
