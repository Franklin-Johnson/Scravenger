package application.model.extractors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractLink {

	public String extract(Document doc) {
		Elements links = doc.select("a[href]");
       String data = "";
		for (Element link : links) {
            data += " * a: "+ "<" + link.attr("abs:href") + ">" +"("+ trim(link.text(), 35)+ ")\n";
        }
		return data;
    }
	
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
} 