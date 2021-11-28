package application.model.usage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * fileToString is a Java class in the application.model.usage package and includes the getString method.
 */
public class fileToString {
	
	/**
	 * getString method accepts a String url and returns a string representation of a File
	 * directed to by the passed url
	 * @param url - String
	 * @return data - String
	 * @throws IOException - may throw IOException
	 */
	public String getString(String url) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(url)));
		String data = "", line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        	data+=line+"\n";
        }
        reader.close();
		return data;
		
	}
	
}
