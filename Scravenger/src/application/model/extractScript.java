package application.model;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class extractScript {

	public String extract(Document doc) throws IOException {
		
	String newData = "";

    
	for (Element scripts : doc.getElementsByTag("script")) {
        newData+= "\n";
		for (DataNode dataNode : scripts.dataNodes()) {
        	newData+= dataNode + " :: " + dataNode.getWholeData() + "\n";
            }
        }
    return newData;
    }
    
}

