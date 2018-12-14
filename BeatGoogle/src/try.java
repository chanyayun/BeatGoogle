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

public class WebList {
	public ArrayList<WebPage> list;
	public WebPage webPage;
	
	public WebList() {
		this.list = new ArrayList<>();
		WebPage espn = new WebPage("http://www.espn.com/search/results?q=NFL#gsc.tab=0&gsc.q=NFL.html", "espnNFL");
		list.add(espn);
		WebPage foxNews = new WebPage("https://www.foxnews.com/category/sports/nfl.html", "foxNews");
		list.add(foxNews);
		WebPage bbc = new WebPage("https://www.bbc.co.uk/search?q=nfl&filter=sport&suggid=.html", "bbc");
		list.add(bbc);
		WebPage ex = new WebPage("http://www.example.com", "ex");
		list.add(ex);
		WebPage so = new WebPage("http://soslab.nccu.edu.tw/Courses.html", "so");
		list.add(so);
	}

	public void setAllScore(ArrayList<Keyword> keywords) throws IOException {
		for(int i = 0 ; i < list.size() ; i++){
		list.get(i).setScore(keywords);
		}
	}

	public void sort() {
		this.list = doQuickSort(this.list);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.list.size(); i++) {
			WebPage page = this.list.get(i);

			if (i > 0) {
				sb.append("");
			}
			sb.append(page.toString());
		}
		System.out.println(sb.toString());
	}

	private ArrayList<WebPage> doQuickSort(ArrayList<WebPage> li) {
		if (li.size() < 2) {
			return li;
		}
		ArrayList<WebPage> result = new ArrayList<>();

		int pivotIndex = li.size() / 2;
		WebPage pivotPage = li.get(pivotIndex);

		ArrayList<WebPage> lessList = new ArrayList<>();
		ArrayList<WebPage> equalList = new ArrayList<>();
		ArrayList<WebPage> greatList = new ArrayList<>();

		for (int i = 0; i < li.size(); i++) {
			WebPage p = li.get(i);

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
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		WebList web = new WebList();

		System.out.println("Enter numOfKeyword, kind, keyword(s):");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			ArrayList<Keyword> keywords = new ArrayList<>();
			int numOfKeyword = sc.nextInt();

			double weight = 10;
			String kind = sc.next();
			Keyword k = null;
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

			web.setAllScore(keywords);
			web.sort();
		}
		sc.close();
	}

}
