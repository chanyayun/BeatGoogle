package ballballgo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebNode {
	public WebPage webPage;
	public ArrayList<WebPage> children;
	public double nodeScore;
	private String url;
	public String content;
	public String subPage = "";

	public WebNode(WebPage webPage) throws IOException {
		this.webPage = webPage;
		this.children = new ArrayList<WebPage>();
		addChild();
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		webPage.setScore(keywords);
		for (WebPage child : children) {
			child.setScore(keywords);
			this.nodeScore += child.score * 0.3;
			subPage = subPage + " | " + child.url;
		}
		webPage.score += this.nodeScore;
		if (children.size() < 3) {
			subPage = subPage + " | (Not found)";
		}
	}

	private String fetchContent() throws IOException {
		String retVal = "";

		try {
			URL url = new URL(this.url);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0(Macintosh;U;Intel Mac OS X 10.4; en-US;rv:1.9.2.2)Gecko/20100316 Firefox/3.6.2");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				retVal += line;
			}
			return retVal;

		} catch (IOException e) {
			retVal = "  ";
			return retVal;

		} catch (IllegalArgumentException e) {
			retVal = "  ";
			return retVal;
		}

	}

	public void addChild() throws IOException {
		this.url = webPage.url;
		this.content = fetchContent();

		Document document = Jsoup.connect(url).get();
		Elements lis = document.select("a[href]");
		ArrayList<String> urls = new ArrayList();

		for (Element li : lis) {
			try {
				String citeUrl = li.attr("href");
				if (citeUrl != null) {
					if (citeUrl.startsWith("/") && citeUrl.length() > 40) {
						citeUrl = li.attr("abs:href");
						String title = li.text();
						if (urls.contains(citeUrl)) {
							continue;
						}
						urls.add(citeUrl);
						children.add(new WebPage(citeUrl, title));
					}
				}

				if (children.size() == 3) {
					break;
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}
}