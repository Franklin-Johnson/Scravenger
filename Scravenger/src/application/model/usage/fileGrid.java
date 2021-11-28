package application.model.usage;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * filesGrid is a Java class in the application.model.usage package and includes the makeGrid and getResources methods.
 */
public class fileGrid {
	
	/**
	 * makeGrid method accepts a GridPane object grid, a String url and String tag and returns
	 * an ArrayList of String objects
	 * @param grid - GridPane
	 * @param url - String
	 * @param tag - String
	 * @return paths - ArrayList <String>
	 */
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
	
    /**
     * getResources method accepts a String path, ArrayList <Files>, ArrayList <String> and a String tag 
     * and returns an ArrayList of File objects
     * @param path - String
     * @param files - ArrayList <File>
     * @param paths - ArrayList <String>
     * @param tag - String
     * @return file - ArrayList <File>
     */
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