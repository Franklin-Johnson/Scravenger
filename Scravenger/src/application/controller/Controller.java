package application.controller; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import application.model.BeginScrape;
import application.model.breadthFirstImplement;
import application.model.cssScrape;
import application.model.emailExtractor;
import application.model.extractScript;
import application.model.linkScrape;
import application.model.mediaScraper;
import application.model.portScrape;
import application.model.recursiveURLMap;
import application.model.telScrape;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * Controller is a JavaFX class in the application.controller package and implements the Initializable interface
 * and the initialize method. This class also includes ActionExtractSettings, ActionOutputFileSelector, ActionExtract 
 * methods. The ActionExtract tailors the output according to the checkable items selected by the user.
 */
public class Controller implements Initializable {
	/**
	 * variable declarations
	 */
	@FXML
	private TextField TextFieldUrlList;
	@FXML
	private TextField outputFileDir;
	@FXML
	private TextField TextFieldCustomExtractor;
	@FXML
	private TextField TextFieldRegex;
	@FXML
    private Button btnFileBrowser, btnSettings, btnExtract;
	@FXML
	private CheckBox CheckRegex, CheckCustomExtractor;
	@FXML
	private CheckBox CheckBfs, CheckRecursive;
	@FXML
	private CheckBox CheckHtml, CheckScripts, CheckCss, CheckLinks, CheckPorts, CheckMedia, CheckEmail, CheckTel;
	
	/**
	 * Event Listener on Button[#btnSettings].onAction takes the user to the custom 
	 * extractions settings dialogue.
	 * @param event - button click
	 */
	@FXML
	void ActionExtractSettings(ActionEvent event) {

	}
	/**
	 * Event Listener on Button[#btnFileBrowser].onAction allows the user to select
	 * an output directory for the extracted data files and saves that directory to be 
	 * automatically recalled later.
	 * @param event - button click
	 */
	@FXML
	void ActionOutputFileSelector(ActionEvent event) {
		Node source = (Node) event.getSource();
		Window stage = source.getScene().getWindow();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File("src"));
		File selectedDirectory = directoryChooser.showDialog(stage);
		outputFileDir.setText(selectedDirectory.getAbsolutePath());
		try {
			File file = new File("directory.txt");
			FileWriter writer = new FileWriter(file);
			writer.write(selectedDirectory.getAbsolutePath());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Event Listener on Button[#btnExtract].onAction begins the process of extracting data
	 * from the user input web address according to the types of data check items selected by the
	 * user.
	 * @param event - button click
	 */
	@FXML
	void ActionExtract(ActionEvent event) {
		String testdir = outputFileDir.getText();
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String strDate = dateFormat.format(date);  
		testdir+= "\\EXTRACT" + strDate.replaceAll("[^a-zA-Z0-9]","_");  
		if(CheckBfs.isSelected()){
			breadthFirstImplement b = new breadthFirstImplement();
			LinkedHashSet<String> marked = new LinkedHashSet<String>();
			marked = b.implement(TextFieldUrlList.getText(), 100);
			if(CheckTel.isSelected()){
				telScrape t = new telScrape();	       
				for (String pageUrl : marked) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\tel",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							String str = t.extract(doc);
							if(str.length()>2) {
								FileUtils.writeStringToFile(f, str, StandardCharsets.UTF_8);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}           		
			}
			if(CheckHtml.isSelected()){
				for (String pageUrl : marked) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\html",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".html");
						try {
							FileUtils.writeStringToFile(f, doc.toString(), StandardCharsets.UTF_8);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			if(CheckScripts.isSelected()){
				extractScript e = new extractScript();
				for (String pageUrl : marked) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\scripts",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".script");
						try {
							FileUtils.writeStringToFile(f, e.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}	
				}
			}
			if(CheckCss.isSelected()){
				cssScrape c = new cssScrape();
				for (String pageUrl : marked) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\css",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".css");
						try {
							FileUtils.writeStringToFile(f, c.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			if(CheckLinks.isSelected()){
				linkScrape l= new linkScrape();
				for (String pageUrl : marked) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\links",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							FileUtils.writeStringToFile(f, l.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			if(CheckPorts.isSelected()){
				portScrape p = new portScrape();
				for (String pageUrl : marked) {
					Document doc;
					try {
						doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\ports",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						FileUtils.writeStringToFile(f, p.extract(pageUrl), StandardCharsets.UTF_8);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(CheckMedia.isSelected()){
				mediaScraper m = new mediaScraper();
				for (String pageUrl : marked) {
					Document doc;
					try {
						doc = Jsoup.connect(pageUrl).get();
						m.extract(doc, testdir);	        			
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(CheckEmail.isSelected()){
				emailExtractor e = new emailExtractor();
				for (String pageUrl : marked) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\email",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							String str = e.extract(doc);
							if(str.length()>2) {
								FileUtils.writeStringToFile(f, str, StandardCharsets.UTF_8);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}  		
			if(CheckRegex.isSelected()){

			}

			if(CheckCustomExtractor.isSelected()) {

			}
			//end bfs
		}
		if(CheckRecursive.isSelected()){
			recursiveURLMap r = new recursiveURLMap();
			r.map(TextFieldUrlList.getText(), 2);
			if(CheckHtml.isSelected()){
				for (String pageUrl : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\html",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".html");
						try {
							FileUtils.writeStringToFile(f, doc.toString(), StandardCharsets.UTF_8);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			if(CheckScripts.isSelected()){
				extractScript e = new extractScript();
				for (String pageUrl : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\scripts",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".script");
						try {
							FileUtils.writeStringToFile(f, e.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			if(CheckCss.isSelected()){
				cssScrape c = new cssScrape();
				for (String pageUrl : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\css",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".css");
						try {
							FileUtils.writeStringToFile(f, c.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			if(CheckLinks.isSelected()){
				linkScrape l= new linkScrape();
				for (String pageUrl : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\links",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							FileUtils.writeStringToFile(f, l.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			if(CheckPorts.isSelected()){
				portScrape p = new portScrape();     	       
				for (String pageUrl : r.getUniqueSet()) {
					Document doc;
					try {
						doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\ports",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						FileUtils.writeStringToFile(f, p.extract(pageUrl), StandardCharsets.UTF_8);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(CheckMedia.isSelected()){
				mediaScraper m = new mediaScraper();
				for (String pageUrl : r.getUniqueSet()) {
					Document doc;
					try {
						doc = Jsoup.connect(pageUrl).get();
						m.extract(doc, testdir);	
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(CheckEmail.isSelected()){
				emailExtractor e = new emailExtractor();
				for (String pageUrl : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\email",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							String str = e.extract(doc);
							if(str.length()>2) {
								FileUtils.writeStringToFile(f, str, StandardCharsets.UTF_8);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} 
			if(CheckTel.isSelected()){
				telScrape t = new telScrape();	       
				for (String pageUrl : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(pageUrl).get();
						File f = new File(testdir + "\\tel",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							String str = t.extract(doc);
							if(str.length()>2) {
								FileUtils.writeStringToFile(f, str, StandardCharsets.UTF_8);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}           		
			}
			if(CheckRegex.isSelected()){
				
			}
			if(CheckCustomExtractor.isSelected()) {
				
			}
		}
		if(!CheckBfs.isSelected() && !CheckRecursive.isSelected()) {
			List<String> urlList = Arrays.asList(TextFieldUrlList.getText().split(","));
			for(String s : urlList) {
				if(CheckTel.isSelected()){
					telScrape t = new telScrape();	       
					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\tel",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							String str = t.extract(doc);
							if(str.length()>2) {
								FileUtils.writeStringToFile(f, str, StandardCharsets.UTF_8);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}           		
				if(CheckHtml.isSelected()){
					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\html",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".html");
						try {
							FileUtils.writeStringToFile(f, doc.toString(), StandardCharsets.UTF_8);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				if(CheckScripts.isSelected()){
					extractScript e = new extractScript();
					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\scripts",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".script");
						try {
							FileUtils.writeStringToFile(f, e.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
				if(CheckCss.isSelected()){
					cssScrape c = new cssScrape();
					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\css",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".css");
						try {
							FileUtils.writeStringToFile(f, c.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				if(CheckLinks.isSelected()){
					linkScrape l= new linkScrape();
					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\links",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							FileUtils.writeStringToFile(f, l.extract(doc), StandardCharsets.UTF_8);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				if(CheckPorts.isSelected()){
					portScrape p = new portScrape();
					Document doc;
					try {
						doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\ports",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						FileUtils.writeStringToFile(f, p.extract(s), StandardCharsets.UTF_8);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(CheckMedia.isSelected()){
					mediaScraper m = new mediaScraper();
					Document doc;
					try {
						doc = Jsoup.connect(s).get();
						m.extract(doc, testdir);
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}

				if(CheckEmail.isSelected()){
					emailExtractor e = new emailExtractor();
					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testdir + "\\email",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						try {
							String str = e.extract(doc);
							if(str.length()>2) {
								FileUtils.writeStringToFile(f, str, StandardCharsets.UTF_8);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} 	
			//Document doc = Jsoup.connect(TextFieldUrlList.getText()).get();
		}       
	}
	/**
     * initialize method  is added from the interface Initializable and is called to initialize a controller after its root element
	 * has been completely processed. This method will populate the url list field with the user input web address from the 
	 * previous screen and also recall if it exists the previously used output directory for data files.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextFieldUrlList.setText(BeginScrape.webAddress);
		File file = new File ("directory.txt");
		if (file.exists()) {
			try {
				FileReader dir = new FileReader(file);
				BufferedReader readFile = new BufferedReader(dir);
				String line = readFile.readLine();
				outputFileDir.setText(line);
				readFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}