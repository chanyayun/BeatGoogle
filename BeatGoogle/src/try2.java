import java.util.ArrayList;

public class Keyword {
	public String name;
	public double weight;
	public String kind;
	public ArrayList<Keyword> keywords;

	public Keyword(String name, double weight, String kind) {
		this.name = name;
		this.weight = weight;
		this.kind = kind;
	}

	public ArrayList<Keyword> addKeyword(Keyword k) {
		keywords = new ArrayList<>();
		keywords.add(k);
		return keywords;
	}

	@Override
	public String toString() {
		return "[" + name + ", " + weight + ", " + kind + "]";
	}
}



import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	public String url;
	public String name;
	public KeywordCounter counter;
	public double score;

	public WebPage(String url, String name) {
		this.url = url;
		this.name = name;
		this.counter = new KeywordCounter(url);
	}

	public void setScore(ArrayList<Keyword> keywords) throws IOException {
		this.score = 0;

		for (Keyword k : keywords) {
			if (name.contains("news")) {
				this.score += counter.countKeyword(k.name) * k.weight * 1.3;
			} else {
				this.score += counter.countKeyword(k.name) * k.weight;
			}
		}
	}

	@Override
	public String toString() {
		return "[" + url + ", " + name + ", " + score + "]\n";
	}
}



import java.io.IOException;
import java.util.ArrayList;

public class WebNewsList {
	public ArrayList<WebPage> newsList;
	public WebPage webPage;

	public WebNewsList(String kind) {
		newsList = new ArrayList<>();
		for (int i = 0; i < HtmlMatcher.web.size(); i++) {
			newsList.add(HtmlMatcher.web.get(i));
		}
	}

	public void setAllScore(ArrayList<Keyword> keywords) throws IOException {
		for (int i = 0; i < newsList.size(); i++) {
			newsList.get(i).setScore(keywords);
		}
	}

	public void sort() {
		newsList = doQuickSort(newsList);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < newsList.size() && i < 10; i++) {
			WebPage pages = newsList.get(i);

			if (i > 0) {
				sb.append("");
			}
			sb.append(pages.toString());
		}
		System.out.println(sb.toString());
	}

	private ArrayList<WebPage> doQuickSort(ArrayList<WebPage> list) {
		if (list.size() < 2) {
			return list;
		}

		ArrayList<WebPage> result = new ArrayList<>();
		ArrayList<WebPage> lessList = new ArrayList<>();
		ArrayList<WebPage> equalList = new ArrayList<>();
		ArrayList<WebPage> greatList = new ArrayList<>();

		int pivotIndex = list.size() / 2;
		WebPage pivotPage = list.get(pivotIndex);

		for (int i = 0; i < list.size(); i++) {
			WebPage p = list.get(i);

			if (p.score > pivotPage.score) {
				greatList.add(p);
			} else if (p.score < pivotPage.score) {
				lessList.add(p);
			} else {
				equalList.add(p);
			}
		}

		result.addAll(doQuickSort(greatList));
		result.addAll(equalList);
		result.addAll(doQuickSort(lessList));

		return result;
	}
}



import java.io.IOException;
import java.util.ArrayList;

public class WebVideoList {
	public ArrayList<WebPage> videoList;
	public WebPage webPage;

	public WebVideoList(String kind) {
		videoList = new ArrayList<>();
		for (int i = 0; i < HtmlMatcherV.webV.size(); i++) {
			videoList.add(HtmlMatcherV.webV.get(i));
		}
	}

	public void setAllScore(ArrayList<Keyword> keywords) throws IOException {
		for (int i = 0; i < videoList.size(); i++) {
			videoList.get(i).setScore(keywords);
		}
	}

	public void sort() {
		videoList = doQuickSort(videoList);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < videoList.size() && i < 5; i++) {
			WebPage pages = videoList.get(i);

			if (i > 0) {
				sb.append("");
			}
			sb.append(pages.toString());
		}
		System.out.println(sb.toString());
	}

	private ArrayList<WebPage> doQuickSort(ArrayList<WebPage> list) {
		if (list.size() < 2) {
			return list;
		}

		ArrayList<WebPage> result = new ArrayList<>();
		ArrayList<WebPage> lessList = new ArrayList<>();
		ArrayList<WebPage> equalList = new ArrayList<>();
		ArrayList<WebPage> greatList = new ArrayList<>();

		int pivotIndex = list.size() / 2;
		WebPage pivotPage = list.get(pivotIndex);

		for (int i = 0; i < list.size(); i++) {
			WebPage p = list.get(i);

			if (p.score > pivotPage.score) {
				greatList.add(p);
			} else if (p.score < pivotPage.score) {
				lessList.add(p);
			} else {
				equalList.add(p);
			}
		}

		result.addAll(doQuickSort(greatList));
		result.addAll(equalList);
		result.addAll(doQuickSort(lessList));

		return result;
	}
}



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HtmlMatcher {
	private String urlStr;
	private String content;
	private String kind;
	public static ArrayList<WebPage> web;

	public HtmlMatcher(String kind) throws IOException {
		this.kind = kind;

//		addMatch();

		switch (kind) {
		case "NBA":
			addNBAMatch();
			break;

		case "MLB":
			addMLBMatch();
			break;

		case "NFL":
			addNFLMatch();
			break;

		case "NHL":
			addNHLMatch();
			break;

		default:
			break;
		}
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			retVal = retVal + line + "\n";
		}

		return retVal;
	}

	public ArrayList<WebPage> addMatch() throws IOException {
		// 使用googleQuery （keyword的空格要變成+ 不用加上標題）
		return null;
	}

	public ArrayList<WebPage> addNBAMatch() throws IOException {
		web = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "";
		if (content == null) {
			content = fetchContent();
		}
		indexOfOpen = content.indexOf("", indexOfOpen);
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			String html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "NBAnews"));
			indexOfOpen = content.indexOf("", indexOfHtmlClose);
		}

//		this.urlStr = ""; 
//		indexOfOpen = 0;
//		indexOfHtml = -1;
//		indexOfHtmlClose = -1;

		// repeat;

		return web;
	}

	public ArrayList<WebPage> addMLBMatch() throws IOException {
		web = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "";
		if (content == null) {
			content = fetchContent();
		}
		indexOfOpen = content.indexOf("", indexOfOpen);
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			String html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "MLBnews"));
			indexOfOpen = content.indexOf("", indexOfHtmlClose);
		}

//		this.urlStr = ""; 
//		indexOfOpen = 0;
//		indexOfHtml = -1;
//		indexOfHtmlClose = -1;

		// repeat;

		return web;
	}

	public ArrayList<WebPage> addNFLMatch() throws IOException {
		web = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "https://www.foxnews.com/category/sports/nfl.html";
		if (content == null) {
			content = fetchContent();
		}
		indexOfOpen = content.indexOf("collection collection-article-list has-load-more", indexOfOpen);
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("article class", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			String html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			if (!content.substring(indexOfHtml + 8, indexOfHtmlClose).contains("video")) {
				html = "https://www.foxnews.com" + html;
				web.add(new WebPage(html, "foxNFLnews"));
			}
		}

		this.urlStr = "http://www.sportingnews.com/ca/nfl/news";
		indexOfOpen = 0;
		indexOfHtml = -1;
		indexOfHtmlClose = -1;
		content = fetchContent();
		indexOfOpen = content.indexOf("latest-news-module module infinite", indexOfOpen);
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfOpen = content.indexOf("media-heading", indexOfHtmlClose);
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			String html = "http://www.sportingnews.com" + content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "sportingnewsNFLnews"));
		}

		return web;
	}

	public ArrayList<WebPage> addNHLMatch() throws IOException {
		web = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "";
		if (content == null) {
			content = fetchContent();
		}
		indexOfOpen = content.indexOf("", indexOfOpen);
		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
		for (int i = 0; i < 10; i++) {
			indexOfHtml = content.indexOf("a href=", indexOfOpen);
			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
			String html = content.substring(indexOfHtml + 8, indexOfHtmlClose);
			web.add(new WebPage(html, "NFLnews"));
			indexOfOpen = content.indexOf("", indexOfHtmlClose);
		}

//		this.urlStr = ""; 
//		indexOfOpen = 0;
//		indexOfHtml = -1;
//		indexOfHtmlClose = -1;

		// repeat;

		return web;
	}
}



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HtmlMatcherV {
	private String urlStr;
	private String content;
	private String kind;
	public static ArrayList<WebPage> webV;

	public HtmlMatcherV(String kind) throws IOException {
		this.kind = kind;

//		addMatch();
		switch (kind) {
		case "NBA":
			addNBAMatch();
			break;

		case "MLB":
			addMLBMatch();
			break;

		case "NFL":
			addNFLMatch();
			break;

		case "NHL":
			addNHLMatch();
			break;

		default:
			break;
		}
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			retVal = retVal + line + "\n";
		}

		return retVal;
	}

	public ArrayList<WebPage> addMatch() throws IOException {
		// 使用googleQuery （keyword的空格要變成+ 不用加上標題）
		return null;
	}

	public ArrayList<WebPage> addNBAMatch() throws IOException {
		webV = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "https://www.youtube.com/user/NBA/videos.html";
		if (content == null) {
			content = fetchContent();
		}

		return webV;
	}

	public ArrayList<WebPage> addMLBMatch() throws IOException {
		webV = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "https://www.youtube.com/user/MLB/videos.html";
		if (content == null) {
			content = fetchContent();
		}

		return webV;
	}

	public ArrayList<WebPage> addNFLMatch() throws IOException {
		webV = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfTitle = -1;
		int indexOfTitleClose = -1;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "https://www.youtube.com/user/NFL/videos.html";
		if (content == null) {
			content = fetchContent();
		}
//		indexOfOpen = content.indexOf("style-scope ytd-grid-renderer", indexOfOpen);
//		indexOfHtmlClose = content.indexOf(">", indexOfOpen);
//		for (int i = 0; i < 10; i++) {
//			indexOfOpen = content.indexOf("style-scope ytd-grid-renderer", indexOfHtmlClose);
//			indexOfOpen = content.indexOf("video-title", indexOfOpen);
//			//indexOfTitle = content.indexOf("title=", indexOfOpen) + 7;
//			indexOfHtml = content.indexOf("href=", indexOfOpen);
//			//indexOfTitleClose = indexOfHtml - 2;
//			indexOfHtmlClose = content.indexOf(">", indexOfHtml) - 1;
//			// search whether title contains keyword and then decide whether send the html
//			//no need to count keyword in the html
//			String html = "https://www.youtube.com" + content.substring(indexOfHtml + 6, indexOfHtmlClose);
//			webV.add(new WebPage(html, "foxNFLnews"));
//		}
		return webV;
	}

	public ArrayList<WebPage> addNHLMatch() throws IOException {
		webV = new ArrayList<>();
		int indexOfOpen = 0;
		int indexOfHtml = -1;
		int indexOfHtmlClose = -1;

		this.urlStr = "https://www.youtube.com/user/NHLVideo/videos.html";
		if (content == null) {
			content = fetchContent();
		}

		return webV;
	}
}



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class KeywordCounter {
	private String urlStr;
	private String content;

	public KeywordCounter(String urlStr) {
		this.urlStr = urlStr;
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			retVal = retVal + line + "\n";
		}

		return retVal;
	}

	public int countKeyword(String keyword) throws IOException{
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



import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		double weight = 15;
		String kind = null;
		Keyword k = null;
		
		System.out.println("Enter numOfKeyword, kind, keyword(s):");

		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			ArrayList<Keyword> keywords = new ArrayList<>();

			int numOfKeyword = sc.nextInt();

			kind = sc.next();

			if (numOfKeyword == 1) {
				String name = sc.next();
				k = new Keyword(name, weight, kind);
				keywords = k.addKeyword(k);
			} else {
				for (int i = 0; i < numOfKeyword; i++) {
					String name = sc.next();
					k = new Keyword(name, weight, kind);
					keywords = k.addKeyword(new Keyword(name, weight, ""));
				}
			}

			HtmlMatcher web = new HtmlMatcher(kind);
			WebNewsList news = new WebNewsList(kind);
			HtmlMatcherV webV = new HtmlMatcherV(kind);
			WebVideoList video = new WebVideoList(kind);

			news.setAllScore(keywords);
			System.out.println("News:");
			news.sort();

			video.setAllScore(keywords);
			System.out.println("Videos:");
			video.sort();
		}

		sc.close();
	}
}



