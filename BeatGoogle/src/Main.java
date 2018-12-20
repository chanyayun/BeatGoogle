import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		WebPage rootPage = new WebPage("http://www.espn.com/search/results?q=NFL#gsc.tab=0&gsc.q=NFL.html", "espnNFL");
		WebTree tree = new WebTree(rootPage);
		tree.root.addChild(new WebNode(new WebPage("https://www.foxnews.com/category/sports/nfl.html", "foxNews")));
		tree.root.addChild(new WebNode(new WebPage("https://www.bbc.co.uk/search?q=nfl&filter=sport&suggid=.html", "bbc")));
		tree.root.addChild(new WebNode(new WebPage("http://www.espn.com/search/results?q=NFL#gsc.tab=0&gsc.q=NFL.html", "espnNFL")));
		
		/*
		 * tree.root.children.get(1).addChild(new WebNode( new WebPage(
		 * "https://scholar.google.com/citations?user=IpxUy-YAAAAJ&hl=en",
		 * "Google Scholar")));
		 */
		System.out.println("this is a useless line");

		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			int numOfKeywords = sc.nextInt();
			ArrayList<Keyword> keywords = new ArrayList<>();

			for (int i = 0; i < numOfKeywords; i++) {
				String name = sc.next();
				double weight = sc.nextDouble();
				Keyword k = new Keyword(name, weight);
				keywords.add(k);
			}

			tree.setPostOrderScore(keywords);
			tree.printTree();
		}
		sc.close();
	}

}
