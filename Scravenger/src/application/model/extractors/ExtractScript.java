package application.model.extractors;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class ExtractScript {

	public String extract(Document doc) throws IOException {		
		String data = "";

		for (Element script : doc.getElementsByTag("script")) {
			data+= "\n";
			for (DataNode node : script.dataNodes()) {
				data += node + " :: " + node.getWholeData() + "\n";
            }
        }
    return data;
    }   
}

