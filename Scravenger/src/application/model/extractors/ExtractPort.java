package application.model.extractors;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractPort is a Java class in the application.model.extractors package and includes the extract method.
 */
public class ExtractPort {
		/**
	     * extract method accepts a String url and converts contained information in to a String
	     * for output
	     * @param url - String url to be sourced
	     * @return - String 
	     * @throws MalformedURLException - may throw an exception
	     */
	    public String extract(String url) throws MalformedURLException {
	    	String data= "";
	    	URL aURL = new URL(url);
	    	data+= url + "\n" + "--protocol = " + aURL.getProtocol() + "\n" + "--authority = "
	    	+ aURL.getAuthority()+ "\n" +"--host = " + aURL.getHost()+ "\n" +"--port = "
	    	+ aURL.getPort() + "\n" +"--path = " + aURL.getPath() + "\n" +"--query = "
	    	+ aURL.getQuery()+ "\n" +"--filename = " + aURL.getFile()+ "\n" +"--ref = " 
	    	+ aURL.getRef()+ "\n";
			return data;
	    }
	}
