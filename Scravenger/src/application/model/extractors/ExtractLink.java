package application.model.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractLink is a Java class in the application.model.extractors package and includes the extract and
 * trim methods.
 */
public class ExtractLink {
	/**
	 * extract method accepts a Document object doc and collects and converts web 
	 * links contained within into String output
	 * @param doc - Document object
	 * @return - String of converted web links
	 */
	public String extract(Document doc) {
		Elements links = doc.select("a[href]");
       String data = "";
		for (Element link : links) {
            data += " * a: "+ "<" + link.attr("abs:href") + ">" +"("+ trim(link.text(), 35)+ ")\n";
        }
		return data;
    }
	/**
	 * trim method accepts a String s and an int width
	 * @param s - String passed
	 * @param width - to compare to the String
	 * @return - String
	 */
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
} 