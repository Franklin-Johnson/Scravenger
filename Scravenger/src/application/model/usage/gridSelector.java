package application.model.usage;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * gridSelector is a Java class in the application.model.usage package and includes the clickGrid method.
 */
public class gridSelector {
	
	/**
	 * clickGrid method accepts a MouseEvent and a GridPane and returns and int
	 * corresponding to the clicked node in the GridPane
	 * @param event - MouseEvent
	 * @param grid - GridPane
	 * @return rIndex - int
	 */
	public int clickGrid(MouseEvent event, GridPane grid) {
		Integer rIndex = 0;
		Integer cIndex = 0;
	    Node clickedNode = event.getPickResult().getIntersectedNode(); 
	    cIndex = GridPane.getColumnIndex(clickedNode);
	    rIndex = GridPane.getRowIndex(clickedNode);
	        
	        

	    return rIndex;
	}
}
	
