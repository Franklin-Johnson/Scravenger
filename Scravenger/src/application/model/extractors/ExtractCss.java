package application.model.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractCss is a Java class in the application.model.extractors package and includes the extract method.
 */
public class ExtractCss {
	/**
     * variable declarations
     */
    String trueValue = "true";
    String skipInline = "data-skip-inline";
    String styleTag = "style";
    /**
	 * extract method accepts a Document object doc and returns a String
	 * @param doc - Document object
	 * @return - String
	 */
	public String extract (Document doc) {
		Elements els = doc.select(styleTag);
		StringBuilder s = new StringBuilder();
		  
		for (Element e : els) {
			if (!trueValue.equals(e.attr(skipInline))) {
				s.append(e.data());
				e.remove();
		    }
		}
			return s.toString();
	}
}
