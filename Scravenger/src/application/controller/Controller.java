package application.controller; 

import java.io.File;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;


public class Controller implements Initializable {

	//extraction items
	@FXML
    private TextField textFieldUrlList;
	
	@FXML
	private TextField outputFileDir;
	
	@FXML
    private TextField textFieldCustomExtractor;
	
	@FXML
    private TextField textFieldRegex;
	
	@FXML
    private CheckBox checkRegex, checkCustomExtractor;

    @FXML
    private CheckBox checkBfs, checkRecursive;

    @FXML
    private CheckBox checkHtml, checkScripts, checkCss, checkLinks, checkPorts, checkMedia, checkEmail, checkTel;
    @FXML
    void ActionExtractSettings(ActionEvent event) {
   
    }
          
    
    @FXML
    void ActionOutputFileSelector(ActionEvent event) {
    	Node src = (Node) event.getSource();
    	Window stage = src.getScene().getWindow();
        DirectoryChooser dirChooser = new DirectoryChooser();
        
        if(fileLocationTextField.getText()=="")
        	dirChooser.setInitialDirectory(new File("src"));
        else
        	dirChooser.setInitialDirectory(new File(fileLocationTextField.getText()));
        
        File selectedDirectory = dirChooser.showDialog(stage);
        outputFileDir.setText(selectedDirectory.getAbsolutePath());       
    }

    @FXML
    void ActionExtract(ActionEvent event) {
    	Runnable task1 = () -> {

    	
    	String testDir = outputFileDir.getText();
    	
    	Date date = Calendar.getInstance().getTime();  
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
    	String strDate = dateFormat.format(date);  
    	
    	testDir += "\\EXTRACT" + strDate.replaceAll("[^a-zA-Z0-9]","_");  
    	
    /*start of breadth first link interation
     * 
     * 	
     */
    	
    	if(checkBfs.isSelected()){
    		
    		BreadthFirstIterator b = new BreadthFirstIterator();
    		LinkedHashSet<String> marked = new LinkedHashSet<String>();
    		
    		marked = b.implement(textFieldUrlList.getText(), 1000);
    		

    		//
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
        	///
        	//
        	
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
        	///
        	//    	
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
    
    /*
     * USAGE
     * 
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
    
    @FXML
    void ActionUsageFileSelector(ActionEvent event) {
   		Node src= (Node) event.getSource();
   	    Window stage = src.getScene().getWindow();
   	    DirectoryChooser dirChooser = new DirectoryChooser();
        if(fileLocationTextField.getText()=="")
       	   dirChooser.setInitialDirectory(new File("src"));
        else
	       dirChooser.setInitialDirectory(new File(fileLocationTextField.getText()));

        File selectedDir = dirChooser.showDialog(stage);
        fileLocationTextField.setText(selectedDir.getAbsolutePath());
           
   }
    
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    }
    @FXML
    void textViewAction(ActionEvent e) {
    	popWindow p = new popWindow();
    	p.deploy(filePathText.getText());
    	
    }
    @FXML
    void browserViewAction(ActionEvent e) {
    	webDisplay w = new webDisplay();
    	w.displayWeb(filePathText.getText());
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}