package application.model.usage;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class gridSelector {
	public int clickGrid(MouseEvent event, GridPane grid) {
		Integer rIndex = 0;
		Integer cIndex = 0;
	    Node clickedNode = event.getPickResult().getIntersectedNode(); 
	    cIndex = GridPane.getColumnIndex(clickedNode);
	    rIndex = GridPane.getRowIndex(clickedNode);
	        
	        

	    return rIndex;
	}
}
	
