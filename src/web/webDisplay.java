package web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class webDisplay {
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
}
