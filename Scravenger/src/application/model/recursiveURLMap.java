package application.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * recursiveURLMap is a Java class in the application.model package and includes the map, getLinks
 * and getUniqueSet methods
 */
public class recursiveURLMap{
	
    /**
     * variable declarations
     */
    public Set<String> uniqueURL = new HashSet<String>();
    public String parsedString;
    public int i=0;
    
    /**
     * map method accepts a String url and int limiter gets the domain from the url
     * for every url up to the limiter,
     * @param url - String the address passed
     * @param limiter - int passed
     */
    public void map(String url, int limiter) {
    	if (i > limiter) return;
    	String domain = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
        recursiveURLMap r = new recursiveURLMap();
        r.getLinks(url, domain);
        i++;
    }

    /**
     * getLinks method accepts a String url and a String domain. This method creates a Document 
     * object from the url passed and gets Element object links from the Document adds them to a
     * Set of Strings.
     * @param url - String web address
     * @param domain - String domain
     */
    public void getLinks(String url, String domain) {
    	try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a");
            if (links.isEmpty()) {
               return;
            }
            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
                boolean add = uniqueURL.add(this_url);
                System.out.println(this_url);
                if (add && this_url.contains(domain)) {  
                    getLinks(this_url, domain);
                }
            });
        } catch (IOException ex) {
        }	
    }
    
    /**
     * getUniqueSet method retrieves the uniqueURL Set <String>
     * @return the uniqueSet 
     */
    public Set<String> getUniqueSet(){
		return uniqueURL; 	
    }
}


