package application.model.extractors;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;

public class ExtractTel {

    public String extract(Document doc) throws IOException {   
    	//String patterns = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
        //	      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
        //	      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
    	String patterns = "(\\\\d{3}[- .]?){2}\\\\d{4}$\"";
    	String str = "";
        Pattern p = Pattern.compile(patterns);
        Matcher matcher = p.matcher(doc.text());
        
        Set<String> tel = new HashSet<String>();
        while (matcher.find()) {
        	System.out.println(matcher.group());
            tel.add(matcher.group());
            str += matcher.group()+ "\n";
            
        }
		return str;

    }

}


	