package application.model.extractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractRegex is a Java class in the application.model.extractors package and includes the search method.
 */
public class ExtractRegex{
	/**
	 * regex method accepts a Document object doc and a String key
	 * @param doc - Document object
	 * @param key - String
	 * @return String 
	 */
	public String search(Document doc, String key) {
		String data = "";
		String regex = "^.*" + key + ".*$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(doc.text());
	    
	    while(matcher.find()) {
	    	data+= matcher.group()+"\n";
	    } 	 
	    return data;
	}	
}
