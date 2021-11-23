package application.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class cssScrape {
	
    private static final String TRUE_VALUE = "true";
    private static final String SKIP_INLINE = "data-skip-inline";
    private static final String STYLE_TAG = "style";
    
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
