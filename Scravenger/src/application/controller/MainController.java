package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import application.model.BeginScrape;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {




	@FXML
	private Button btnScrape;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private TextField urlTextField;

	@FXML
    void getWebAddress(ActionEvent event) {
		BeginScrape.webAddress = urlTextField.getText();
		//System.out.println(BeginScrape.webAddress);
		try {
			URL url = new File("src/application/view/ScrapeView.fxml").toURI().toURL();
        	mainPane = FXMLLoader.load(url);
        	Scene scene = new Scene(mainPane, 1280, 1024);
        	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        	window.setTitle("Scravenger - Web Scraper");
        	window.setScene(scene);
        	window.show();
        } catch (IOException e) {
        	e.printStackTrace();
        }        
    }


}
