package application.model.usage;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class popWindow {
	public void deploy(String path) {
    	fileToString f = new fileToString();
    	Stage popWindow =new Stage();
    	
    	popWindow.initModality(Modality.APPLICATION_MODAL);
    	 
        Group root = new Group();
        TextArea textArea = new TextArea();
        
        try {
			textArea.setText(f.getString(path));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        ScrollPane sPane = new ScrollPane();
        sPane.setContent(textArea);
        sPane.setFitToWidth(true);
        sPane.setPrefWidth(800);
        sPane.setFitToHeight(true);
        sPane.setPrefHeight(600);

        VBox v = new VBox();
        v.getChildren().addAll(sPane);
        
        v.setAlignment(Pos.CENTER);
        root.getChildren().add(v);
    	      
    	popWindow.setScene(new Scene(root, 800, 600));      
    	popWindow.show();
	}
}
