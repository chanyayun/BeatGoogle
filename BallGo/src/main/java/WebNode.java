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
	public String subPage = "\tSubPages:\n";

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
			subPage = subPage + "\t" + child.name + " [" + child.score + "]\n\t" + child.url + "\n";
		}
		webPage.score += this.nodeScore;
		if (children.size() < 3) {
			subPage = subPage + "\t(No fit SubPages)\n";
		}
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

	public void addChild() throws IOException {
//		this.url = webPage.url;
//		this.content = fetchContent();
//
//		Document document = Jsoup.parse(this.content);
//		Elements lis = document.select("div.g");
//		for (Element li : lis) {
//			try {
//				Element cite = li.select("cite").get(0);
//				String citeUrl = cite.text();
//				Element h3 = li.select("h3.r").get(0);
//				String title = h3.text();
		for (int i = 0; i < 3; i++) {
			String citeUrl = "https://www.foxnews.com/category/sports/nba.html";
			String title = "sub";

				if (citeUrl.length() > 40) {
					children.add(new WebPage(citeUrl, title));
				}
				if (children.size() >= 3) {
					break;
				}
		}
//			} catch (IndexOutOfBoundsException e) {
//			}
//		}
	}
}
