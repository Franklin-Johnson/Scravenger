package application.model.usage;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParse {
	public String data = "";

	public String parseHTML(Document doc) {
		 Elements links = doc.select("a[href]");
	     Elements media = doc.select("[src]");
	     Elements imports = doc.select("link[href]");

	     print("\nMedia: (%d)", media.size());
	     for (Element e : media) {
	    	 if (e.normalName().equals("img"))
	    		 print(" * %s: <%s> %sx%s (%s)",e.tagName(), e.attr("abs:src"), e.attr("width"), e.attr("height"),trim(e.attr("alt"), 20));
	    	 else
	    		 print(" * %s: <%s>", e.tagName(), e.attr("abs:src"));
	     }
	     
	     print("\nImports: (%d)", imports.size());
	     for (Element e : imports) {
	    	 print(" * %s <%s> (%s)", e.tagName(),e.attr("abs:href"), e.attr("rel"));
         }
	     
	     print("\nLinks: (%d)", links.size());
	     for (Element e : links) {
	    	 print(" * a: <%s>  (%s)", e.attr("abs:href"), trim(e.text(), 35));
	     }	        	
	     
	     return data;
	}

	private void print(String text, Object... args) {
        data += ((String.format(text, args))+"\n");
    }
	
	private static String trim(String s, int len) {
		if (s.length() > len)
			return s.substring(0, len-1) + ".";
		else
			return s;
	}
}
