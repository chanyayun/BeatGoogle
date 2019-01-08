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
	private String searchKeyword2;
	public static ArrayList<WebPage> web;
	public static String relatedKeyword = "[Related keywords] \n";

	public HtmlMatcher(String kind, String searchKeyword) throws IOException {
		this.searchKeyword2 = searchKeyword;
		web = new ArrayList<>();

		switch (kind) {
		case "NBA":
			if (searchKeyword != "NBA") {
				this.searchKeyword = searchKeyword + "+NBA+news";
			} else {
				this.searchKeyword = searchKeyword + "+news";
			}
			nbaMatch();
			break;
		case "MLB":
			if (searchKeyword != "MLB") {
				this.searchKeyword = searchKeyword + "+MLB+news";
			} else {
				this.searchKeyword = searchKeyword + "+news";
			}
			mlbMatch();
			break;
		case "NFL":
			if (searchKeyword != "NFL") {
				this.searchKeyword = searchKeyword + "+NFL+news";
			} else {
				this.searchKeyword = searchKeyword + "+news";
			}
			nflMatch();
			break;
		case "NHL":
			if (searchKeyword != "NHL") {
				this.searchKeyword = searchKeyword + "+NHL+news";
			} else {
				this.searchKeyword = searchKeyword + "+news";
			}
			nhlMatch();
			break;
		default:
			if (searchKeyword != "sport") {
				this.searchKeyword = searchKeyword + "+sport+news";
			} else {
				this.searchKeyword = searchKeyword + "+news";
			}
			break;
		}
		query();
		fetchRelatedKeyword();
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
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword + "&oe=utf8num=15";
		this.content = fetchContent();

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
		this.url = "https://www.foxnews.com/category/sports/nba.html";
		content = fetchContent();

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		int indexOfTitleClose = -1;
		String html = null;
		String title = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose - 1).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, title));
			}
		}

		this.url = "http://www.sportingnews.com/ca/nba/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			web.add(new WebPage(html, title));
		}
	}

	public void mlbMatch() throws IOException {
		this.url = "https://www.foxnews.com/category/sports/mlb.html";
		content = fetchContent();

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		int indexOfTitleClose = -1;
		String html = null;
		String title = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose - 1).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, title));
			}
		}

		this.url = "http://www.sportingnews.com/ca/mlb/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			web.add(new WebPage(html, title));
		}
	}

	public void nflMatch() throws IOException {
		this.url = "https://www.foxnews.com/category/sports/nfl.html";
		content = fetchContent();

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		int indexOfTitleClose = -1;
		String html = null;
		String title = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose - 1).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, title));
			}
		}

		this.url = "http://www.sportingnews.com/ca/nfl/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			web.add(new WebPage(html, title));
		}
	}

	public void nhlMatch() throws IOException {
		this.url = "https://www.foxnews.com/category/sports/nhl.html";
		content = fetchContent();

		int indexOfOpen = content.indexOf("collection collection-article-list has-load-more");
		int indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		int indexOfHtml = -1;
		int indexOfTitleClose = -1;
		String html = null;
		String title = null;
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose - 1).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, title));
			}
		}

		this.url = "http://www.sportingnews.com/ca/nhl/news";
		content = fetchContent();

		indexOfOpen = content.indexOf("latest-news-module module infinite");
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml);
			indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
			html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose - 1);
			title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
			web.add(new WebPage(html, title));
		}
	}

	public void fetchRelatedKeyword() throws IOException {
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword2 + "&oe=utf8num=10";
		content = fetchContent();
		int indexOfOpen = content.indexOf("clear:");
		if (indexOfOpen == -1) {
			relatedKeyword = relatedKeyword + "(Not Found) \n";
		} else {
			int indexOfHtmlClose = -1;
			int indexOfHtml = -1;
			int indexOfTitleClose = -1;
			String title = "";
			indexOfHtmlClose = content.indexOf(">", indexOfOpen);
			for (int i = 0; i < 5; i++) {
				indexOfHtml = content.indexOf("a href=", indexOfHtmlClose);
				indexOfHtmlClose = content.indexOf(">", indexOfHtml);
				indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
				title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
				relatedKeyword = relatedKeyword + title + "\n";
			}
		}
	}
}
