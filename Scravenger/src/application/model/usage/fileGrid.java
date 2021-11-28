package application.model.usage;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class fileGrid {
	
	public ArrayList<String> makeGrid(GridPane grid, String url, String tag) {
		ArrayList<String> paths = new ArrayList<>();
		
		grid.getChildren().clear();
		
		while(grid.getRowConstraints().size() > 0){
		    grid.getRowConstraints().remove(0);
		    
		}

		while(grid.getColumnConstraints().size() > 0){
		    grid.getColumnConstraints().remove(0);
		}
		
		ArrayList<File> files = new ArrayList<>();
				getResource(url, files, paths, tag);
		int i= 0;
		
				
		for(File file : files) {
			System.out.println(file.toString());
			Text t = new Text();
			t.setText(file.getName());
			grid.addRow(i, t);
			i++;
		}
		
		return paths;
	}
	
    public ArrayList<File> getResource(String path, ArrayList<File> files, ArrayList<String> paths, String tag) {
        File file = new File(path);
        if (file.isFile()) {
        	
    	    if(tag=="all") {
        		files.add(file);
        		paths.add(file.getAbsolutePath());
    	    }
    	    else if(file.getParentFile().getName().equals(tag)) {
        		files.add(file);
        		paths.add(file.getAbsolutePath());
        	}
    	    
        } else {
            File[] fileList = file.listFiles();
            
            if (fileList != null) {
                for (File resourceInDir : fileList) {

                    if (!resourceInDir.isFile()) {
                        getResource(resourceInDir.getAbsolutePath(), files, paths, tag);
                        //allFiles.add(file);
                    } else {
                        getResource(resourceInDir.getAbsolutePath(), files, paths, tag);
                       // allFiles.add(file);

                    }

                }
            }

        }
		return files;
    }
    
}