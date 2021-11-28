package application.model.usage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileToString {
	public String getString(String url) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(url)));
		String data = "", line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        	data+=line+"\n";
        }
        reader.close();
		return data;
		
	}
	
}
