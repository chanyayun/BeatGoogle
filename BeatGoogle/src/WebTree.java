import java.io.IOException;
import java.util.ArrayList;

public class WebTree {
	public WebNode root;

	public WebTree(WebPage rootPage) {
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
		for (WebNode child : startNode.children) {
			setPostOrderScore(child, keywords);
		}

		startNode.setNodeScore(keywords);
	}

	public void printTree() {
		printTree(root);
	}

	private void printTree(WebNode startNode) {
		System.out.print("(" + startNode.webPage.name + ", " + startNode.nodeScore);
		if (startNode.children.isEmpty()) {
			System.out.print(")");
		}
		for (WebNode child : startNode.children) {
			System.out.println();
			for (int i = 1; i < child.getDepth(); i++) {
				System.out.print("\t");
			}
			printTree(child);
		}
		if (!startNode.children.isEmpty()) {
			System.out.println();
			for (int i = 1; i < startNode.getDepth(); i++) {
				System.out.print("\t");
			}
			System.out.print(")");
		}
	}
}
