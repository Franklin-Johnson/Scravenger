package application.model.extractors;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractTel is a Java class in the application.model.extractors package and includes the extract method.
 */
public class ExtractTel {
	/**
     * extract method accepts a Document object doc searches the Document for 
     * text matching the pattern and adds matches to the Set and returns a String of the 
     * text
     * @param doc - Document object
     * @return - String of converted text
     * @throws IOException - may throw IOException
     */
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


	