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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.nio.file.Files;
import application.model.extractors.ExtractCss;
import application.model.extractors.ExtractEmail;
import application.model.extractors.ExtractScript;
import application.model.extractors.ExtractLink;
import application.model.extractors.ExtractMedia;
import application.model.extractors.ExtractPort;
import application.model.extractors.ExtractRegex;
import application.model.extractors.ExtractTel;
import application.model.iterators.BreadthFirstIterator;
import application.model.iterators.RecursiveIterator;
import application.model.usage.fileGrid;
import application.model.usage.gridSelector;
import application.model.usage.popWindow;
import application.model.usage.webDisplay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 * @author  CS3443.003-Group2 Scravenger Web Scraper application
 *
 * Controller is a JavaFX class in the application.controller package and implements the Initializable interface
 * and the initialize method. This class also includes ActionExtractSettings, ActionOutputFileSelector, ActionExtract 
 * findButtonAction, ActionUsageFileSelector, clickGrid, textViewAction and browserViewAction methods. The 
 * ActionExtract tailors the output according to the checkable items selected by the user.
 */
public class Controller implements Initializable {

	/**
	 * variable declarations
	 */
	@FXML
    private TextField textFieldUrlList;
	@FXML
	private TextField outputFileDir;
	@FXML
    private TextField textFieldCustomExtractor;
	@FXML
    private TextField textFieldRegex;
	@FXML
    private Button btnFileBrowser, btnSettings, btnExtract;
	@FXML
    private CheckBox checkRegex, checkCustomExtractor;
    @FXML
    private CheckBox checkBfs, checkRecursive;
    @FXML
    private CheckBox checkHtml, checkScripts, checkCss, checkLinks, checkPorts, checkMedia, checkEmail, checkTel;
	
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
    	Node src = (Node) event.getSource();
    	Window stage = src.getScene().getWindow();
        DirectoryChooser dirChooser = new DirectoryChooser();
        if(outputFileDir.getText() == null) {
        	dirChooser.setInitialDirectory(new File("src"));
         } else {
 	       dirChooser.setInitialDirectory(new File(outputFileDir.getText()));
         }
        
        File selectedDirectory = dirChooser.showDialog(stage);
        if(selectedDirectory != null) {
        	outputFileDir.setText(selectedDirectory.getAbsolutePath());
           	try {
    			File file = new File("directory.txt");
    			FileWriter writer = new FileWriter(file);
    			writer.write(selectedDirectory.getAbsolutePath());
    			writer.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        } else {
        	outputFileDir.setText(null);
        }
        
    }
	/**
	 * Event Listener on Button[#btnExtract].onAction starts a new thread and the process of extracting data
	 * from the user input web address according to the types of data check items selected by the user.
	 * @param event - button click
	 */
	@FXML
    void ActionExtract(ActionEvent event) {
    	Runnable task1 = () -> {

    	
    	String testDir = outputFileDir.getText();
    	
    	Date date = Calendar.getInstance().getTime();  
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
    	String strDate = dateFormat.format(date);  
    	
    	testDir += "\\EXTRACT" + strDate.replaceAll("[^a-zA-Z0-9]","_");  
			
    /**
     * 
     * start of breadth first link iteration
     * 	
     */
    	
    	if(checkBfs.isSelected()){
    		
    		BreadthFirstIterator b = new BreadthFirstIterator();
    		LinkedHashSet<String> marked = new LinkedHashSet<String>();
    		marked = b.implement(textFieldUrlList.getText(), 100);
    		
    		
    		
            if(checkTel.isSelected()){
            ExtractTel t = new ExtractTel();	       
            
            for(String page : marked) {
       			try {
       				Document doc = Jsoup.connect(page).get();
       				File f = new File(testDir + "\\tel",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
    		
        	if(checkHtml.isSelected()){
        		for (String page : marked) {
					try {
					Document doc = Jsoup.connect(page).get();
					File f = new File(testDir + "\\html",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".html");
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
        	
        	
        	if(checkScripts.isSelected()){
        		ExtractScript e = new ExtractScript();
        		for (String page : marked) {

					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\scripts",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".script");
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
        	
        	if(checkCss.isSelected()){
        		ExtractCss c = new ExtractCss();
        		for (String page : marked) {

					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\css",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".css");
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
        	if(checkLinks.isSelected()){
        		ExtractLink l= new ExtractLink();
        		for (String page : marked) {

					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\links",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
        	if(checkPorts.isSelected()){
        		ExtractPort p = new ExtractPort();
        		for (String page : marked) {
        			Document doc;
					try {
						doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\ports",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						FileUtils.writeStringToFile(f, p.extract(page), StandardCharsets.UTF_8);
	        			
					} catch (IOException e) {
						e.printStackTrace();
					}
						
        		}
        	}
        	if(checkMedia.isSelected()){
        		ExtractMedia m = new ExtractMedia();
        		for (String page : marked) {
        			Document doc;
					try {
						doc = Jsoup.connect(page).get();
						m.extract(doc, testDir);	        			
					} catch (IOException e) {
						e.printStackTrace();
					}
						
        		}
        	}
        	if(checkEmail.isSelected()){
            	ExtractEmail e = new ExtractEmail();
            		for (String page : marked) {

    					try {
    						Document doc = Jsoup.connect(page).get();
    						File f = new File(testDir + "\\email",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
    	        			try {
    	        				String s = e.extract(doc);
    	        				if(s.length()>2) {
    							FileUtils.writeStringToFile(f, s, StandardCharsets.UTF_8);
    	        				}
    						} catch (Exception e1) {
    							e1.printStackTrace();
    						}
    					} catch (Exception e2) {
    						e2.printStackTrace();
    					}
            		}
        	}  		
        	if(checkRegex.isSelected()){
        		ExtractRegex r = new ExtractRegex();
        		for (String page : marked) {

					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\regex",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
	        			try {
	        				String s = r.search(doc, textFieldRegex.getText());
	        				if(s.length()>2) {
							FileUtils.writeStringToFile(f, s, StandardCharsets.UTF_8);
	        				}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
        		}
        	}
        	
        	if(checkCustomExtractor.isSelected()) {
        		
        	}
        
        	//end bfs
        	       	
    	}
    	
    	if(checkRecursive.isSelected()){
    		RecursiveIterator r = new RecursiveIterator();
    		r.map(textFieldUrlList.getText(), 2);
        	if(checkHtml.isSelected()){
        		for (String page : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\html",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".html");
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
        	
        	
        	if(checkScripts.isSelected()){
        		ExtractScript e = new ExtractScript();
        		for (String page : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\scripts",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".script");
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
        	
        	if(checkCss.isSelected()){
        		ExtractCss c = new ExtractCss();
        		for (String page : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\css",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".css");
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
        	if(checkLinks.isSelected()){
        		ExtractLink l= new ExtractLink();
        		for (String page : r.getUniqueSet()) {
					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\links",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
        	if(checkPorts.isSelected()){
        		ExtractPort p = new ExtractPort();     	       
        		for (String page : r.getUniqueSet()) {
        			Document doc;
					try {
						doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\ports",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
						FileUtils.writeStringToFile(f, p.extract(page), StandardCharsets.UTF_8);
	        			
					} catch (IOException e) {
						e.printStackTrace();
					}
						
        		}
        	}
        	if(checkMedia.isSelected()){
        		ExtractMedia m = new ExtractMedia();
        		for (String page : r.getUniqueSet()) {
        			Document doc;
					try {
						doc = Jsoup.connect(page).get();
						m.extract(doc, testDir);	
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        	}
        	if(checkEmail.isSelected()){
            	ExtractEmail e = new ExtractEmail();
            		for (String page : r.getUniqueSet()) {
    					try {
    						Document doc = Jsoup.connect(page).get();
    						File f = new File(testDir + "\\email",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
            		
            if(checkTel.isSelected()){
            	ExtractTel t = new ExtractTel();	       
            		for (String page : r.getUniqueSet()) {
       					try {
       						Document doc = Jsoup.connect(page).get();
       						File f = new File(testDir + "\\tel",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
            
        	if(checkRegex.isSelected()){
        		ExtractRegex re= new ExtractRegex();
        		for (String page : r.getUniqueSet()) {

					try {
						Document doc = Jsoup.connect(page).get();
						File f = new File(testDir + "\\regex",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
	        			try {
	        				String str = re.search(doc, textFieldRegex.getText());
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
        	
        	if(checkCustomExtractor.isSelected()) {
        		
        	}
        
        	//end recur
        	
    	}
    	


    
    if(!checkBfs.isSelected() && !checkRecursive.isSelected()) {
    	List<String> urlList = Arrays.asList(textFieldUrlList.getText().split(","));
    	for(String s : urlList) {
    		
            if(checkTel.isSelected()){
            	ExtractTel t = new ExtractTel();	       
       					try {
       						Document doc = Jsoup.connect(s).get();
       						File f = new File(testDir + "\\tel",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
        	
    		if(checkHtml.isSelected()){
    			try {
    				Document doc = Jsoup.connect(s).get();
    				File f = new File(testDir + "\\html",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".html");
    				try {
    					FileUtils.writeStringToFile(f, doc.toString(), StandardCharsets.UTF_8);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
    		}
	
	
	
    		if(checkScripts.isSelected()){
    			ExtractScript e = new ExtractScript();
    			try {
    				Document doc = Jsoup.connect(s).get();
    				File f = new File(testDir + "\\scripts",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".script");
    				try {
					FileUtils.writeStringToFile(f, e.extract(doc), StandardCharsets.UTF_8);
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
    			}catch(Exception e2) {
    				e2.printStackTrace();
    			}
		
    		}
	
	
    		if(checkCss.isSelected()){
    			ExtractCss c = new ExtractCss();

    			try {
    				Document doc = Jsoup.connect(s).get();
    				File f = new File(testDir + "\\css",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".css");
    				try {
    					FileUtils.writeStringToFile(f, c.extract(doc), StandardCharsets.UTF_8);
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
    			} catch (Exception e2) {
    				e2.printStackTrace();
    			}
	
    		}
    		
    		if(checkLinks.isSelected()){
    			ExtractLink l= new ExtractLink();
    			try {
    				Document doc = Jsoup.connect(s).get();
    				File f = new File(testDir + "\\links",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
    				try {
    					FileUtils.writeStringToFile(f, l.extract(doc), StandardCharsets.UTF_8);
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
    			} catch (Exception e2) {
    				e2.printStackTrace();
    			}
    		}
    		
    		if(checkPorts.isSelected()){
    			ExtractPort p = new ExtractPort();

    			Document doc;
    			try {
    				doc = Jsoup.connect(s).get();
    				File f = new File(testDir + "\\ports",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
    				FileUtils.writeStringToFile(f, p.extract(s), StandardCharsets.UTF_8);
    			
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
				
    		}
    		
    		if(checkMedia.isSelected()){
    			ExtractMedia m = new ExtractMedia();
    			Document doc;
    			try {
    				doc = Jsoup.connect(s).get();
    				m.extract(doc, testDir);
    			} catch (IOException e) {
    				e.printStackTrace();
				}	
    		}
    	
    		if(checkEmail.isSelected()){
    			ExtractEmail e = new ExtractEmail();
				try {
					Document doc = Jsoup.connect(s).get();
					File f = new File(testDir + "\\email",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
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
    		
    		if(checkRegex.isSelected()) {
        		ExtractRegex r = new ExtractRegex();
        		

					try {
						Document doc = Jsoup.connect(s).get();
						File f = new File(testDir + "\\regex",doc.title().replaceAll("[^a-zA-Z0-9]","#") + ".txt");
	        			try {
	        				String str = r.search(doc, textFieldRegex.getText());
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
            
    } 
	};
	ExecutorService executorService = Executors.newFixedThreadPool(4);
    executorService.execute(task1);
    executorService.shutdown();
}
    
    /**
     * USAGE
     * variable declarations
     * 
     * 
     */
    @FXML
    GridPane fileGrid, selectedGrid, htmlGrid, scriptGrid, cssGrid, emailGrid, telGrid, linkGrid,
    regexGrid, mediaGrid, portGrid, extraGrid;
    @FXML
    TextField fileLocationTextField;
    @FXML
    Text fileNameText, fileTypeText, fileSizeText, filePathText;
    
    ArrayList<String> filePaths = new ArrayList<>();
    ArrayList<String> htmlPaths = new ArrayList<>();
    ArrayList<String> scriptPaths = new ArrayList<>();
    ArrayList<String> cssPaths = new ArrayList<>();
    ArrayList<String> emailPaths = new ArrayList<>();
    ArrayList<String> telPaths = new ArrayList<>();
    ArrayList<String> linkPaths = new ArrayList<>();
    ArrayList<String> regexPaths = new ArrayList<>();
    ArrayList<String> mediaPaths = new ArrayList<>();
    ArrayList<String> portPaths = new ArrayList<>();
    ArrayList<String> extraPaths = new ArrayList<>();
    
    /**
     * findButtonAction method is an ActionEvent trigger by the user selecting a tab inside the Usage tab which
     * then displays the selected data
     * @param e - ActionEvent
     */
    @FXML
    void findButtonAction(ActionEvent e) {
    	
    	fileGrid f = new fileGrid();
    	filePaths = f.makeGrid(fileGrid, fileLocationTextField.getText(), "all");
    	htmlPaths = f.makeGrid(htmlGrid, fileLocationTextField.getText(), "html");
    	scriptPaths = f.makeGrid(scriptGrid, fileLocationTextField.getText(), "scripts");
    	cssPaths = f.makeGrid(cssGrid, fileLocationTextField.getText(), "css");
    	emailPaths = f.makeGrid(emailGrid, fileLocationTextField.getText(), "email");
    	telPaths = f.makeGrid(telGrid, fileLocationTextField.getText(), "tel");
    	linkPaths = f.makeGrid(linkGrid, fileLocationTextField.getText(), "link");
    	regexPaths = f.makeGrid(regexGrid, fileLocationTextField.getText(), "regex");
    	mediaPaths = f.makeGrid(mediaGrid, fileLocationTextField.getText(), "media");
    	portPaths = f.makeGrid(portGrid, fileLocationTextField.getText(), "ports");
    	extraPaths = f.makeGrid(extraGrid, fileLocationTextField.getText(), "other");
    }
    
    /**
	 * Event Listener on Button[#btnFileBrowserUseage].onAction allows the user to select
	 * a directory of data files to be displayed 
	 * @param event - button click
	 */
    @FXML
    void ActionUsageFileSelector(ActionEvent event) {
    	Node src= (Node) event.getSource();
   	    Window stage = src.getScene().getWindow();
   	    DirectoryChooser dirChooser = new DirectoryChooser();
        if(fileLocationTextField.getText() == null) {
       	   dirChooser.setInitialDirectory(new File("src"));
        } else {
	       dirChooser.setInitialDirectory(new File(fileLocationTextField.getText()));
        }
        File selectedDir = dirChooser.showDialog(stage);
        if (selectedDir != null) {
        	fileLocationTextField.setText(selectedDir.getAbsolutePath());
        } else {
        	fileLocationTextField.setText(null);
        }
   }
    
    /**
     * clickGrid method is an MouseEvent triggered when the user clicks on a Grid
     * @param e - MouseEvent
     */
    @FXML
    void clickGrid(MouseEvent e) {
    	gridSelector g = new gridSelector();
    	int row = g.clickGrid(e, fileGrid);
    	String url = filePaths.get(row);
    	System.out.println(filePaths.get(row));
    	
    	File f = new File(url);
    	filePathText.setText(url);
    	fileSizeText.setText(String.valueOf(f.length()));
    	fileNameText.setText(f.getName());
    	try {
			fileTypeText.setText(Files.probeContentType(f.toPath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
    }
    
    /**
	 * Event Listener on Button[#btnViewText].onAction allows the user to view 
	 * selected data as text 
	 * @param e - button click
	 */
    @FXML
    void textViewAction(ActionEvent e) {
    	popWindow p = new popWindow();
    	p.deploy(filePathText.getText());
    	
    }
    
    
    /**
     * Event Listener on Button[#btnBrowserView].onAction allows the user to view 
	 * selected data in a brower window 
	 * @param e - button click
	 */
    @FXML
    void browserViewAction(ActionEvent e) {
    	webDisplay w = new webDisplay();
    	w.displayWeb(filePathText.getText());
    }
    
	/**
     * initialize method  is added from the interface Initializable and is called to initialize a controller after its root element
	 * has been completely processed. This method will populate the url list field with the user input web address from the 
	 * previous screen and also recall if it exists the previously used output directory for data files.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		outputFileDir.setText(null);
		fileLocationTextField.setText(null);
		textFieldUrlList.setText(FirstController.webAddress);
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
	
	/**
	 * about method is a menu item action event triggered when the about item is selected under the help menu item
	 * @param event - ActionEvent
	 */
	@FXML
    void about(ActionEvent event) {
		Label version = new Label("Version: 2021-11 (0.00.01)\nBuild id: 20212011-0001\n(c) Copyright Some Poor College Student Contributors.\n"
				+ "All rights reserved. Web Scravenger is a trademark of Some Poor College Student Contributors.\n"
				+ "The Web Scravenger logo cannot be altered without Some Poor College Student Contributors' permission.\n"
				+ "Oracle and Java are trademarks or registered trademarks of Oracle and/or its affiliates.\n"
				+ "Other names may be trademarks of their respective owners.\n" + "");
		version.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 14));
 
		 Image image = new Image("skull2.png", 200, 289, false, false);
		 
		 //image.getScaledInstance(132, 190, Image.SCALE_FAST);
				 //("https://e7.pngegg.com/pngimages/101/541/png-clipart-skull-skull.png");
 
		ImageView image2 = new ImageView();
		image2.setImage(image);
 
		AnchorPane secondaryLayout = new AnchorPane();
		version.setLayoutX(5);
		version.setLayoutY(210);
		image2.setLayoutX(290);
		image2.setLayoutY(5);
		secondaryLayout.getChildren().add(image2);
		secondaryLayout.getChildren().add(version);
		
		Scene secondScene = new Scene(secondaryLayout, 800, 400);
 
		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("About Scravenger");
		newWindow.setScene(secondScene);
 
		newWindow.show();
    }
	/**
	 * close method is an action event triggered when the close item is selected under the file menu item
	 * @param event - ActionEvent
	 */
	@FXML
    void close(ActionEvent event) {
		Platform.exit(); // terminates application
    }
	
}