package application.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * HtmlParse is a Java class in the application.model package and includes the parseHTML method 
 * as well as the helper methods print and trim.
 * 
 */
public class HtmlParse {
	/**
	 * variable declaration
	 */
	public String parsedString = "";
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
	     for (Element src : media) {
	    	 if (src.normalName().equals("img"))
	    		 print(" * %s: <%s> %sx%s (%s)",src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),trim(src.attr("alt"), 20));
	    	 else
	    		 print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
	     }
	     print("\nImports: (%d)", imports.size());
	     for (Element link : imports) {
	    	 print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
         }     
	     print("\nLinks: (%d)", links.size());
	     for (Element link : links) {
	    	 print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
	     }	        		     
	     return parsedString;
	}
	
	/**
	 * print method accepts a String and Element objects to create a String
	 * @param msg - String
	 * @param args - Element object(s)
	 */
	private void print(String msg, Object... args) {
        parsedString += ((String.format(msg, args))+"\n");
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
