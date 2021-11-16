import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Scrape extends Application {
    public static void main(String[] args) {
    	
    	//start javafx application
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	//load starting fxml document
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ScrapeFX.fxml"));
        
        //place scene in anchorpane container and show it in the fx application
    	AnchorPane anchorPane = (AnchorPane)loader.load();
    	Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}