package application.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * emailExtractor is a Java class in the application.model package and includes the extract method.
 */
public class emailExtractor {

    /**
     * extract method accepts a Document object doc finds email addresses in that document and returns
     * an email address as a String
     * @param doc - Document object 
     * @return - String as a formatted email address
     * @throws IOException - may throw an IOException
     */
    public String extract(Document doc) throws IOException {
        String str = "";
        Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
        Matcher matcher = p.matcher(doc.text());
        Set<String> emails = new HashSet<String>();
        while (matcher.find()) {
            emails.add(matcher.group());
            str += matcher.group()+ "\n";
        }
		return str;
    }
}