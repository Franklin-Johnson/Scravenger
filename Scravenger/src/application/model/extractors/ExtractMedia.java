package application.model.extractors;

import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class ExtractMedia {
 
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