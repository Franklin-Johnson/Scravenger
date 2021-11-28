package application.model.usage;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * HtmlParse is a Java class in the application.model.usage package and includes the parseHTML method 
 * as well as the helper methods print and trim.
 * 
 */
public class HtmlParse {
	/**
	 * variable declaration
	 */
	public String data = "";
	/**
	 * parseHTML method accepts a Document object doc and converts it to a 
	 * String for output to a file
	 * @param doc - Document object
	 * @return - String from converted HTML document
	 */
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
	
	/**
	 * print method accepts a String and Element objects to create a String
	 * @param msg - String
	 * @param args - Element object(s)
	 */
	private void print(String text, Object... args) {
        data += ((String.format(text, args))+"\n");
    }
	/**
	 * trim method accepts a String s and an int width
	 * @param s - String passed
	 * @param width - to compare to the String
	 * @return - String
	 */
	private static String trim(String s, int len) {
		if (s.length() > len)
			return s.substring(0, len-1) + ".";
		else
			return s;
	}
}
