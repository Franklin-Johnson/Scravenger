package application.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BeginScrape {

	public static String webAddress;
	public String parsedString;

	public String parseHTML(Document doc) {
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.normalName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)",src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}	        	

		return parsedString;
	}

	private void print(String msg, Object... args) {
		parsedString += ((String.format(msg, args))+"\n");
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
	public void displayWeb(TextArea docTextArea, WebView webPanel) {

		String htmlbody = docTextArea.getText();
		File newHtmlFile = new File("src/pageHTML.html");
		BufferedWriter writer;

		try {
			writer = new BufferedWriter(new FileWriter(newHtmlFile));
			writer.write(htmlbody);
			writer.close();

			WebEngine engine = webPanel.getEngine();
			URL url = new File("src/pageHTML.html").toURI().toURL();
			engine.load(url.toExternalForm());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
