package web;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class breadthFirstImplement {
	Queue<String> queue = new LinkedList<String>();
	LinkedHashSet<String> marked = new LinkedHashSet<String>();
	String parsedString = "";
	
	public String implement(String url, int maxLinks){
		

		String s = url;
		String domain = url.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)","");
		queue.add(s);
		marked.add(s);
		Document doc;

		OUTER: while (!queue.isEmpty()) {

			String r = queue.remove();
			System.out.println(r);

			if (marked.size() < maxLinks) {
				try {
					doc = Jsoup.connect(r).get();
					Elements questions = doc.select("a[href]");
					for (Element link : questions) {
						if ((link.attr("abs:href").contains(domain) && (link.attr("abs:href").startsWith("http")))) {
							if (marked.size() == maxLinks) {
								continue OUTER;
							}
							else {
								queue.add(link.attr("abs:href"));
								marked.add(link.attr("abs:href"));
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		System.out.println("Total Links: " + marked.size());
		int counter =0;
		int listSize = marked.size();
		parsedString+= marked.size() + "\n";
		for (String fileName : marked) {
			parsedString += counter + "/"	+ listSize + ".." + fileName +"\n";
			counter++;
		}
		return parsedString;
	}
}