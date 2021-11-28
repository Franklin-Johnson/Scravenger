package application.model.iterators;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * RecursiveIterator is a Java class in the application.model.iterators package and includes the map, getLinks
 * and getUniqueSet methods
 */
public class RecursiveIterator{
	
	/**
     * variable declarations
     */
    public Set<String> urlHash = new HashSet<String>();
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
    	
    	RecursiveIterator r = new RecursiveIterator();
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

            if (links.isEmpty()) 
            	return;
           
            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((thisUrl) -> {
                boolean b = urlHash.add(thisUrl);
                System.out.println(thisUrl);
                if (b && thisUrl.contains(domain)) {
                    getLinks(thisUrl, domain);
                }
            });
        } catch (IOException e) {
        	e.printStackTrace();
        }	

    }
    
	/**
     * getUniqueSet method retrieves the uniqueURL Set <String>
     * @return the uniqueSet 
     */
    public Set<String> getUniqueSet(){
		return urlHash;
    	
    }
}
