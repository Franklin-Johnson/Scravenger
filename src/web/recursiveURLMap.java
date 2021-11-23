package web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.scene.control.TextArea;

import java.util.Iterator;


public class recursiveURLMap{

    public Set<String> uniqueURL = new HashSet<String>();
    public String parsedString;

    public String map(String url) {
    	String domain = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
 
        recursiveURLMap r = new recursiveURLMap();
        r.getLinks(url, domain);
        return parsedString;
    }

    public String getLinks(String url, String domain) {
    	try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a");

            if (links.isEmpty()) {
            	System.out.println("done");
               return parsedString;
            }

            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
                boolean add = uniqueURL.add(this_url);
                System.out.println(this_url + " " + domain);
                if (add && this_url.contains(domain)) {
                    parsedString+= this_url + "\n";
                    System.out.println(this_url);
                    getLinks(this_url, domain);
                }
            });

        } catch (IOException ex) {

        }
		return "null";

    }
}


/*public class recursiveURLMap {
	String parsedString = "";
	public String map(String url, int depth) {	
	
		try {
			//Pattern domPat = Pattern.compile("^(https*://[www.]*[a-zA-Z1-9]+)");
			//Matcher domMatch = domPat.matcher(url);
			//domMatch.find();
			//String domain = domMatch.group(1);
			//String url = "";
			
			HashMap<String, String> h = new HashMap<>(); 
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();

			//Elements links = doc.select("[href*=" + sentUrl + "]");
			Elements links = doc.select("a[href]");
			List url_array = new ArrayList();
			int i=0;
		
			url_array.add(url);
			String root = url;
			
			h.put(url, title);	
			Iterator<String> keySetIterator = h.keySet().iterator(); 
			while((i<=(h.size()))){//h.size
				try{
					url = url_array.get(i).toString();
			

					doc = Jsoup.connect(url).get();
					title = doc.title();
					//links = doc.select("[href*=" + sentUrl + "]");
					links = doc.select("a[href]");
					
					for (Element link : links) {
						//String res= h.putIfAbsent(link.attr("href"), link.text());
						parsedString += "\nURL: " + link.attr("href")+ "\n" + "CONTENT: " + link.text() + "\n" + "depth: "  + depth;
						//if (res==null){
							//domMatch = domPat.matcher(url);
							//domMatch.find();
							
							//url_array.add(link.attr("href"));
							//parsedString += "\nURL: " + link.attr("href")+ "\n" + "CONTENT: " + link.text() + "\n";
							//System.out.print("\nURL: " + link.attr("href")+ "\n" + "CONTENT: " + link.text() + i + "match" + "\n");
							
							
						//}
							
					}
					if(depth < 3) {
						map(url, depth+1);
					}
				}catch(Exception e){ 
					System.out.println("\n"+ e);
					e.printStackTrace();
				}
				i++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsedString;
	}
}
*/