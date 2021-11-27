package application.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

public class regex{
	public String search(Document doc, String key) {
		String data = "";
		String regex = "^<[^\n]*" + key + "[^\n]*>\n$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(doc.text());
	    
	    while(matcher.find()) {
	    	data+= matcher.group()+"\n";
	    } 	 
	    return data;
	}	
}
