package application.model.extractors;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractScript is a Java class in the application.model.extractors package and includes the extract method.
 */
public class ExtractScript {
	/**
	 * extract method accepts a Document object doc and converts DataNodes into a
	 * String for output
	 * @param doc - Document object
	 * @return - String from converted DataNodes
	 * @throws IOException - may throw IOException
	 */
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

