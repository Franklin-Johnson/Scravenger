package application.model;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * extractScript is a Java class in the application.model package and includes the extract method.
 */
public class extractScript {
	/**
	 * extract method accepts a Document object doc and converts DataNodes into a
	 * String for output
	 * @param doc - Document object
	 * @return - String from converted DataNodes
	 * @throws IOException - may throw IOException
	 */
	public String extract(Document doc) throws IOException {
	String newData = "";
	for (Element scripts : doc.getElementsByTag("script")) {
        newData += "\n";
		for (DataNode dataNode : scripts.dataNodes()) {
        	newData += dataNode + " :: " + dataNode.getWholeData() + "\n";
            }
        }
    return newData;
    }
}

