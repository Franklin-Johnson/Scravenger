package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.jsoup.*;
import org.jsoup.nodes.Document;

import application.model.BeginScrape;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class ScrapeController implements Initializable{

	//object fxids
	//text field for entering target url
	@FXML
	private TextField urlTextField;

	//button to call handleFetchPage -> urlTextField
	@FXML
	private Button urlFetch;

	//call handle handleParseHtml
	@FXML
	private MenuItem parseButton;

	//area to append result from handleParseHtml
	@FXML
	private TextArea parseTextField;

	//area to append result of handleFetchPage
	@FXML
	private TextArea docTextArea;

	//webengine display panel
	@FXML
	private WebView webPanel;

	//Jsoup document structure for html content
	@FXML
	private Document doc;

	//call to toggleWebView and displayWeb functions - broken
	@FXML
	private CheckMenuItem menuWebView;

	//call toggleHTMLView - broken
	@FXML
	private CheckMenuItem menuHTMLView;

	//container to organize borderpane objects
	@FXML
	private SplitPane MainSplitView;

	//container for interactable objects in the scene
	@FXML
	private BorderPane webViewBorderPane;

	//display or hide browser view of html content
	@FXML
	void toggleWebView(ActionEvent event) {
		//if to add or remove web display panel
		if (((CheckMenuItem) event.getSource()).isSelected()) {
			//add borderpane to splitpane
			MainSplitView.getItems().add(webViewBorderPane);
			BeginScrape o = new BeginScrape();
			//
			o.displayWeb(docTextArea, webPanel);  	
		}
		else {
			MainSplitView.getItems().remove(webViewBorderPane);
		}
	}

	//display or hide extracted html
	@FXML
	void toggleHTMLView(ActionEvent event) {
		if (((CheckMenuItem) event.getSource()).isSelected()) {
			MainSplitView.getItems().add(docTextArea);
		}
		else
			MainSplitView.getItems().remove(docTextArea);
	}

	//call to parseHTML and inserts the result into text field
	@FXML
	void handleParseHtml(ActionEvent event){
		String parsedString = "";
		BeginScrape prox = new BeginScrape();
		Document doc = Jsoup.parse(docTextArea.getText());
		parsedString += prox.parseHTML(doc);
		parseTextField.setText(parsedString);
	}

	//call to jsoup connect and putting the result in a document obj
	//displaying the document body in a text field
	@FXML
	void handleFetchPage(ActionEvent event) {
		try {
			doc = Jsoup.connect(BeginScrape.webAddress).get();
			docTextArea.setText(doc.body().html());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		urlTextField.setText(BeginScrape.webAddress);
		try {
			doc = Jsoup.connect(BeginScrape.webAddress).get();
			docTextArea.setText(doc.body().html());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}   
	
}
