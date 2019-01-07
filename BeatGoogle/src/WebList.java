import java.io.IOException;
import java.util.ArrayList;

public class WebList {
	public WebNode root;
	public ArrayList<String> subPages;
	public ArrayList<WebPage> webList;

	public WebList(ArrayList<Keyword> keywords) throws IOException {
		webList = new ArrayList<>();
		subPages = new ArrayList<>();

		for (int i = 0; i < HtmlMatcher.web.size(); i++) {
			webList.add(HtmlMatcher.web.get(i));
			this.root = new WebNode(HtmlMatcher.web.get(i));
			setAllScore(root, keywords);
			subPages.add(root.subPage);
		}
	}

	private void setAllScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
		startNode.setNodeScore(keywords);
	}

	public String sort() {
		webList = doQuickSort(webList);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < webList.size() && i < 15; i++) {
			if (i > 0) {
				sb.append("");
			}
			sb.append(webList.get(i).toString() + subPages.get(i) + "\n");
		}
		return sb.toString();
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
			WebPage page = list.get(i);
			if (page.score > pivotPage.score) {
				greatList.add(page);
			} else if (page.score < pivotPage.score) {
				lessList.add(page);
			} else {
				equalList.add(page);
			}
		}

		result.addAll(doQuickSort(greatList));
		result.addAll(equalList);
		result.addAll(doQuickSort(lessList));
		return result;
	}
}
