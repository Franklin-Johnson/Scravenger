package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * FirstController is a JavaFX class in the application.controller package and includes the getWebAddress
 * method allows the user to enter a complete web site address from which they wish to extract data. If the
 * web site address is not complete a warning is displayed.
 */
public class FirstController {

	/**
	 * JavaFX variable declarations
	 */
	@FXML
	private Button btnScrape;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private TextField urlTextField;
	public static String webAddress;
	/**
	 * Event Listener on Button[#btnScrape].onAction saves user input web address and opens the 
	 * MainFXML.fxml view for further user interaction or display a warning to the user.
	 * @param event - button click
	 */
	@FXML
    void getWebAddress(ActionEvent event) {
		Alert alert = new Alert(AlertType.NONE);
		String address = urlTextField.getText();
		if (address.contains("http://") || address.contains("https://")) {
			webAddress = urlTextField.getText();
			try {
				URL url = new File("src/application/view/MainFXML.fxml").toURI().toURL();
	        	mainPane = FXMLLoader.load(url);
	        	Scene scene = new Scene(mainPane, 1024, 768);
	        	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	        	window.setTitle("Scravenger - Web Scraper");
	        	window.setScene(scene);
	        	window.show();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		} else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Please enter a complete website address.\nExample: "
					+ "https://www.websitename.com");
			alert.showAndWait();
		}
		        
    }
}
