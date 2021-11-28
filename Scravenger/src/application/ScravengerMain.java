package application;
	
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * @author CS3443.003-Group2 Scravenger Web Scraper application
 *
 * Main is a JavaFX class and extends the Application class. The Main class includes the start 
 * method as the main entry point for this application and for this project was automatically created
 * as a JavaFX project generating the primary Stage, ScravengerFirst.fxml and the application.css.
 */
public class ScravengerMain extends Application {
	/**
	 * start method is the beginning of the application defining the primary user interface container as the
	 * Stage and the Scene within that Stage as well as the graphical element nodes placed in the Scene.
	 * The start method also makes all of these elements visible in the application window of the specified 
	 * size.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url = new File("src/application/view/ScravengerFirst.fxml").toURI().toURL();
			AnchorPane root = (AnchorPane)FXMLLoader.load(url);
			Scene scene = new Scene(root,1024, 768);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Scravenger - Web Scraper");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * main method used to contain the call of the launch method which is the stand alone application
	 * and displays the ScravengerFirst.fxml
	 * @param args - optional command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
