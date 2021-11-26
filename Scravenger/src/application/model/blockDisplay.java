package application.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * blockDisplay is a Java class in the application.model package and includes the display method.
 */
public class blockDisplay{
	/**
	 * display method accepts html as a String then parses the html and formats the html to treat each element 
	 * as a block element and returns that newly formatted html document.
	 * @param html - String to be passed
	 * @return html Document object
	 */
	public String display(String html) {
		Document doc = Jsoup.parse(html);

		doc.outputSettings().outline(true);

		return doc.html();
	}
}
