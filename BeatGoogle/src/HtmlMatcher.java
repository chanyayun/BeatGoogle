import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;

public class HtmlMatcher {
	private String urlStr;
	private String content;

	public HtmlMatcher(String urlStr) {
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

	public void match() throws IOException {
		if (content == null) {
			content = fetchContent();
		}

		Stack<String> tagStack = new Stack<>();

		int indexOfOpen = 0;

		if (content.contains("Doctype")) {
			indexOfOpen = content.indexOf("Doctype");
		} else if (content.contains("DOCTYPE")) {
			indexOfOpen = content.indexOf("DOCTYPE");
		} else if (content.contains("doctype")) {
			indexOfOpen = content.indexOf("doctype");
		}

		while ((indexOfOpen = content.indexOf("<", indexOfOpen)) != -1) {
			int indexOfClose = content.indexOf(">", indexOfOpen);
			String fullTag = content.substring(indexOfOpen, indexOfClose);

			String tagName = null;

			int indexOfSpace = -1;
			if ((indexOfSpace = fullTag.indexOf(" ")) == -1) {
				tagName = fullTag.substring(1, fullTag.length());
			} else {
				tagName = fullTag.substring(1, indexOfSpace);
			}

			int indexOfSlash = -1;
			if ((indexOfSlash = tagName.indexOf("/")) == -1) {
				tagStack.push(tagName);
			} else {
				tagName = tagName.substring(indexOfSlash + 1, tagName.length());

				if (tagStack.isEmpty()) {
					System.out.println("False");
					return;
				}

				String topMostTag = tagStack.peek();
				if (topMostTag.equals(tagName)) {
					tagStack.pop();
				} else {
					System.out.println("False " + getStackString(tagStack));
					return;
				}
			}

			indexOfOpen = indexOfClose;
		}

		if (!tagStack.isEmpty()) {
			System.out.println("False " + getStackString(tagStack));
		} else {
			System.out.println("True");
		}
	}

	private String getStackString(Stack<String> tagStack) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < tagStack.size(); i++) {
			if (i > 0) {
				sb.append(" ");
			}
			sb.append(tagStack.get(i));
		}
		return sb.toString();
	}
}
