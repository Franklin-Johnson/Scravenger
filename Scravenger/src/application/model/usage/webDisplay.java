package application.model.usage;

import java.io.File;
import java.net.URL;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class webDisplay {
	
	public void displayWeb(String path) {
    	
    	Stage popWindow =new Stage();
    	WebView webPanel = new WebView();
    	popWindow.initModality(Modality.APPLICATION_MODAL);
    	 
        Group root = new Group();
        File f = new File(path);
        
		try {

			URL url = f.toURI().toURL();
		    WebEngine engine = webPanel.getEngine();
		    engine.load(url.toString());
		        
			} 
			catch (Exception e) {
				e.printStackTrace();
		}
        


        VBox v = new VBox();
        v.getChildren().addAll(webPanel);
        
        v.setAlignment(Pos.CENTER);
        root.getChildren().add(v);
    	      
    	popWindow.setScene(new Scene(root, 800, 600));      
    	popWindow.show();
			

		
		
	}
}

