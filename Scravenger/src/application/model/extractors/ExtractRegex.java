package application.model.extractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

public class ExtractRegex{
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
