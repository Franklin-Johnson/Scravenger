package application.model.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractCss {
	
    String trueValue = "true";
    String skipInline = "data-skip-inline";
    String styleTag = "style";
    
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
