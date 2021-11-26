package application.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * webDisplay is a Java class in the application.model package and includes the displayWeb method.
 */
public class webDisplay {
	/**
	 * displayWeb method accepts a TextArea and a WebView this method gets the text from the 
	 * TextArea and writes it to a file to be read and displayed in the webPanel
	 * @param docTextArea - TextArea object containing html data
	 * @param webPanel - WebView object to display the converted html data
	 */
	public void displayWeb(TextArea docTextArea, WebView webPanel) {
	    String htmlbody = docTextArea.getText();
	   	File newHtmlFile = new File("src/pageHTML.html");
	   	BufferedWriter writer;      
		try {
			writer = new BufferedWriter(new FileWriter(newHtmlFile));
			writer.write(htmlbody);
			writer.close(); 
		    WebEngine engine = webPanel.getEngine();
		    URL url = new File("src/pageHTML.html").toURI().toURL();
		    engine.load(url.toExternalForm());    
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
}
