/*package regexTools;

import java.util.stream.Stream;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class text extends Application{

	    private static final String NBSP = "\u2007" ;

	    private final PseudoClass expanded = PseudoClass.getPseudoClass("expanded");

	    @Override
	    public void start(Stage primaryStage) {
	        String[] words = text.split(" ");
	        TextFlow flow = new TextFlow();
	        flow.setLineSpacing(5);
	        Stream.of(words)
	            .map(s -> s.concat(" "))
	            .map(Text::new)
	            .peek(text -> text.getStyleClass().add("word"))
	            .peek(text -> text.setOnMousePressed(event -> expand(text)))
	            .forEach(flow.getChildren()::add);

	        ScrollPane scroller = new ScrollPane();
	        scroller.setFitToWidth(true);
	        scroller.setContent(flow);

	        Scene scene = new Scene(scroller, 600, 400);

	        scene.getStylesheets().add("expanding-text.css");

	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    private void expand(Text text) {
	        if (text.getPseudoClassStates().contains(expanded)) { //collapse:
	            String[] letters = text.getText().split(NBSP);
	            StringBuilder newText = new StringBuilder();
	            Stream.of(letters).forEach(newText::append);
	            text.setText(newText.toString());
	            text.pseudoClassStateChanged(expanded, false);
	        } else {
	            String[] letters = text.getText().split("");
	            text.setText(String.join(NBSP, letters));
	            text.pseudoClassStateChanged(expanded, true);
	        }
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}
}*/
