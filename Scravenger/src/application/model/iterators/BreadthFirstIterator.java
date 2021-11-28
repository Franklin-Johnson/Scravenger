package application.model.iterators;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BreadthFirstIterator {
	
	public Queue<String> qList = new LinkedList<String>();
	public LinkedHashSet<String> trueLinks = new LinkedHashSet<String>();
	public String data = "";
	
	public LinkedHashSet<String> implement(String url, int maxLinks){
		String s = url;
		String domain = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
		Document doc;
		
		qList.add(s);
		trueLinks.add(s);
		
		outer: while (!qList.isEmpty()) {

			String r = qList.remove();
			System.out.println(r);

			if (trueLinks.size() < maxLinks) {
				try {
					doc = Jsoup.connect(r).get();
					Elements links = doc.select("a[href]");
					for (Element link : links) {
						if((link.attr("abs:href").contains(domain) && (link.attr("abs:href").startsWith("http")))){
							if (trueLinks.size() == maxLinks) {
								continue outer;
							}
							else {
								qList.add(link.attr("abs:href"));
								trueLinks.add(link.attr("abs:href"));
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Total Links: " + trueLinks.size());
		int i =0;
		int size = trueLinks.size();
		data += trueLinks.size() + "\n";
		for (String fileName : trueLinks) {
			data += i + "/"	+ size + ".." + fileName +"\n";
			i++;
		}
		return trueLinks;
	}
}