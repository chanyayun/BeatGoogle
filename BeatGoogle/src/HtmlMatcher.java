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

public class HtmlMatcher {
	private String url;
	private String content;
	private String searchKeyword;
	public static ArrayList<WebPage> web;

	public HtmlMatcher(String kind, String searchKeyword) throws IOException {
		this.searchKeyword = searchKeyword;

		web = new ArrayList<>();

		query();

		switch (kind) {
		case "NBA":
			nbaMatch();
			break;
		case "MLB":
			mlbMatch();
			break;
		case "NFL":
			nflMatch();
			break;
		case "NHL":
			nhlMatch();
			break;
		default:
			break;
		}
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.url);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) "
				+ "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)");
		conn.connect();
		InputStream in = conn.getInputStream();
		InputStreamReader ir = new InputStreamReader(in, "UTF8");
		BufferedReader br = new BufferedReader(ir);

		String retVal = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			retVal += line;
		}

		return retVal;
	}

	public void query() throws IOException {
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword + "&oe=utf8num=30";
		if (this.content == null) {
			this.content = fetchContent();
		}

		Document document = Jsoup.parse(this.content);
		Elements lis = document.select("div.g");
		for (Element li : lis) {
			try {
				Element cite = li.select("cite").get(0);
				String citeUrl = cite.text();
				Element h3 = li.select("h3.r").get(0);
				String title = h3.text();

				web.add(new WebPage(citeUrl, title));
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	public void nbaMatch() throws IOException {
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

		this.url = "https://www.foxnews.com/category/sports/nba.html";
		if (content == null) {
			content = fetchContent();
		}

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		String html = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, "foxNBAnews"));
			}
		}

		this.url = "http://www.sportingnews.com/ca/nba/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "sportingnewsNBAnews"));
		}
	}

	public void mlbMatch() throws IOException {
		this.url = "https://www.foxnews.com/category/sports/mlb.html";
		if (content == null) {
			content = fetchContent();
		}

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		String html = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, "foxMLBnews"));
			}
		}

		this.url = "http://www.sportingnews.com/ca/mlb/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "sportingnewsMLBnews"));
		}
	}

	public void nflMatch() throws IOException {
		this.url = "https://www.foxnews.com/category/sports/nfl.html";
		if (content == null) {
			content = fetchContent();
		}

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		String html = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, "foxNFLnews"));
			}
		}

		this.url = "http://www.sportingnews.com/ca/nfl/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "sportingnewsNFLnews"));
		}
	}

	public void nhlMatch() throws IOException {
		this.url = "https://www.foxnews.com/category/sports/nhl.html";
		if (content == null) {
			content = fetchContent();
		}

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		String html = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, "foxNHLnews"));
			}
		}

		this.url = "http://www.sportingnews.com/ca/nhl/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "sportingnewsNHLnews"));
		}
	}
}
