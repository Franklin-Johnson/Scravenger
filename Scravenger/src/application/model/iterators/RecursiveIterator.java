package application.model.iterators;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RecursiveIterator{
	
    public Set<String> urlHash = new HashSet<String>();
    public String parsedString;
    public int i=0;
    
    public void map(String url, int limiter) {
    	if (i > limiter) return;	
    	
    	String domain = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
    	
    	RecursiveIterator r = new RecursiveIterator();
    	r.getLinks(url, domain);
    	i++;
    }

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
    
    public Set<String> getUniqueSet(){
		return urlHash;
    	
    }
}
