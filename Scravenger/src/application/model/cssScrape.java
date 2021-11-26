package application.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * cssScrape is a Java class in the application.model package and includes the extract method.
 */
public class cssScrape {
	
    /**
     * variable declarations
     */
    private static final String TRUE_VALUE = "true";
    private static final String SKIP_INLINE = "data-skip-inline";
    private static final String STYLE_TAG = "style";
    
	/**
	 * extract method accepts a Document object doc and returns a String
	 * @param doc - Document object
	 * @return - String
	 */
	public String extract (Document doc) {

		  Elements els = doc.select(STYLE_TAG);
		  StringBuilder styles = new StringBuilder();
		  for (Element e : els) {
		    if (!TRUE_VALUE.equals(e.attr(SKIP_INLINE))) {
		      styles.append(e.data());
		      e.remove();
		    }
		  }
		  return styles.toString();
		}
}
