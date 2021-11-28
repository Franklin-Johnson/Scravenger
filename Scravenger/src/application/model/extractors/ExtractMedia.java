package application.model.extractors;

import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 /**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * ExtractMedia is a Java class in the application.model.extractors package and includes the extract and
 * getImages methods.
 */
public class ExtractMedia {
	/**
	 * extract method accepts a Document object and a String folderPath finds images
	 * from within the Document and creates a directory in which to store them
	 * @param doc - Document object
	 * @param folderPath - String directory location to store images
	 */
    public void extract(Document doc, String folderPath) {
        Elements img = doc.getElementsByTag("img");
 
		for (Element el : img) {

		   String src = el.absUrl("src");
		   String name = FilenameUtils.getName(src).replaceAll("[^a-zA-Z0-9]","_");
		    File dir = new File(folderPath+ "\\media");
		    if (!dir.exists()){
		        dir.mkdirs();
		        
		    }
		    getImages(src, folderPath+"\\media", name);
 
		}
    }
	/**
	 * getImages method accepts a String srcf, String folderPath and String name. This 
	 * method gets the srcf source folder and reads the images contained within and names
	 * the images while writing them to the output directory using folderPath
	 * @param srcf - String source folder
	 * @param folderPath - String destination folder path
	 * @param name - image file name
	 */
    private static void getImages(String srcf, String folderPath, String name) {
  
    	try {
    	URL url = new URL(srcf); 
    	RenderedImage image = ImageIO.read(url);
    	FileOutputStream fos = new FileOutputStream(folderPath + "\\"+ name + ".png");
    	ImageIO.write(image, "png", fos);
    	fos.close();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	

    }
}