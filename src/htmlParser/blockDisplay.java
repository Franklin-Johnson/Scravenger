package htmlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class blockDisplay{
	public String display(String html) {
		Document doc = Jsoup.parse(html);

		doc.outputSettings().outline(true);

		return doc.html();
	}
}
