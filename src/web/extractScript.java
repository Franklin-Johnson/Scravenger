package web;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import org.jsoup.Jsoup;

public class extractScript {

	public String extract(String url) throws IOException {
		
	String newData = "";
	Document doc = Jsoup.connect(url).get();
    
	for (Element scripts : doc.getElementsByTag("script")) {
        newData+= "\n";
		for (DataNode dataNode : scripts.dataNodes()) {
        	newData+= dataNode + " :: " + dataNode.getWholeData() + "\n";
            }
        }
    return newData;
    }
    
}

