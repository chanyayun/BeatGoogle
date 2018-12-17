import java.util.ArrayList;

public class Keyword {
	public String name;
	public double weight;
	public String kind;
	ArrayList<Keyword> keywords;

	public Keyword(String name, double weight, String kind) {
		this.name = name;
		this.weight = weight;
		this.kind = kind;
	}

	public ArrayList<Keyword> addKeyword(Keyword k) {
		keywords = new ArrayList<>();
		keywords.add(k);
		switch (kind) {
		case "NBA":
			keywords.add(new Keyword("NBA", 7, "NBA"));
			break;

		case "MLB":
			keywords.add(new Keyword("MLB", 7, "MLB"));
			break;

		case "NFL":
			keywords.add(new Keyword("NFL", 7, "NFL"));
			break;

		case "NHL":
			keywords.add(new Keyword("NHL", 7, "NHL"));
			break;

		case "PGA":
			keywords.add(new Keyword("PGA", 7, "PGA"));
			break;

		case "Soccer":
			keywords.add(new Keyword("Soccer", 7, "Soccer"));
			break;

		default:
			break;
		}

		return keywords;
	}

	@Override
	public String toString() {
		return "[" + name + ", " + weight + ", " + kind + "]";
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
			this.score += counter.countKeyword(k.name) * k.weight;
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

	public WebNewsList() {
		newsList = new ArrayList<>();
		WebPage foxNews = new WebPage("http://www.sportingnews.com/us/nfl", "sportingNewsNFL");
		newsList.add(foxNews);
		WebPage bbcSports = new WebPage("https://bleacherreport.com/nfl", "BrNFL");
		newsList.add(bbcSports);
		WebPage espnNFL = new WebPage("http://www.espn.com/nfl/", "espnNFL");
		newsList.add(espnNFL);
		WebPage example = new WebPage("http://www.example.com", "example");
		newsList.add(example);
		WebPage soslab = new WebPage("http://soslab.nccu.edu.tw/Courses.html", "soslab");
		newsList.add(soslab);
	}

	public void setAllScore(ArrayList<Keyword> keywords) throws IOException {
		for (int i = 0; i < newsList.size(); i++) {
			newsList.get(i).setScore(keywords);
		}
	}

	public void sort() {
		newsList = doQuickSort(newsList);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < newsList.size(); i++) {
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

		if (result.size() > 10) {
			result = (ArrayList<WebPage>) result.subList(0, 9);
		}
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
		switch (kind) {
		case "NBA":
			WebPage nbaVedio = new WebPage("https://www.youtube.com/user/NBA/videos.html", "nbaVedio");
			videoList.add(nbaVedio);
			break;

		case "MLB":
			WebPage mlbVedio = new WebPage("https://www.youtube.com/user/MLB/videos.html", "mlbVedio");
			videoList.add(mlbVedio);
			break;

		case "NFL":
			WebPage nflVideo = new WebPage("https://www.youtube.com/user/NFL/videos.html", "nflVideo");
			videoList.add(nflVideo);
			break;

		case "NHL":
			WebPage nhlVideo = new WebPage("https://www.youtube.com/user/NHLVideo/videos.html", "nhlVideo");
			videoList.add(nhlVideo);
			break;

		case "PGA":
			WebPage pgaVideo = new WebPage("https://www.youtube.com/user/pgatour/videos.html", "pgaVideo");
			videoList.add(pgaVideo);
			break;

		case "Soccer":
			WebPage soccerVideo = new WebPage("https://www.youtube.com/results?search_query=soccer.html", "soccerVideo");
			videoList.add(soccerVideo);
			break;

		default:
			break;
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
		for (int i = 0; i < videoList.size(); i++) {
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

		if (result.size() > 5) {
			result = (ArrayList<WebPage>) result.subList(0, 4);
		}
		return result;
	}
}


public class GameScoreList {
	public GameScoreList(String kind) {
		switch (kind) {
		case "NBA":
			break;

		case "MLB":
			break;

		case "NFL":
			break;

		case "NHL":
			break;

		case "PGA":
			break;

		case "Soccer":
			break;

		default:
			break;
		}
	}
}


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		double weight = 10;
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

			WebNewsList web = new WebNewsList();
			WebVideoList video = new WebVideoList(kind);

			web.setAllScore(keywords);
			System.out.println("News:");
			web.sort();

			video.setAllScore(keywords);
			System.out.println("Videos:");
			video.sort();
		}

		sc.close();
	}
}
