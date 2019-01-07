import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WebNode {
	public WebPage webPage;
	public ArrayList<WebPage> children;
	public double nodeScore;
	private String url;
	public String content;

	public WebNode(WebPage webPage) throws IOException {
		this.webPage = webPage;
		this.children = new ArrayList<WebPage>();
		addChild();
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		webPage.setScore(keywords);
		for (WebPage child : children) {
			child.setScore(keywords);
			this.nodeScore += child.score;
		}
		webPage.score += this.nodeScore;
	}

	//addChild用
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

	//抓子網頁
	public void addChild() throws IOException {
		children.add(new WebPage("http://www.sportingnews.com/ca/nba/news", "test1"));
		children.add(new WebPage("http://www.sportingnews.com/ca/nfl/news", "test2"));
		children.add(new WebPage("http://www.sportingnews.com/ca/nhl/news", "test3"));

//		this.url = "http://www.sportingnews.com/ca/nba/news";	
//		if (this.content == null) {
//			this.content = fetchContent();
//		}
//		
//		Document document = Jsoup.parse(this.content);
//		Elements lis = document.select("li.media");
//		for (Element li : lis) {
//			try {
//				String citeUrl = "http://www.sportingnews.com" + li.attr("abs:href");
//				Element h3 = li.select("h3.r").get(0);
//				String title = h3.text();
//
//				web.add(new WebPage(citeUrl, title));
//			} catch (IndexOutOfBoundsException e) {
//			}
//		}
	}
}
