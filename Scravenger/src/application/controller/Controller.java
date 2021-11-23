package application.controller; 

import java.io.File;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;


public class Controller implements Initializable {

	//extraction items
	@FXML
    private TextField TextFieldUrlList;
	
	@FXML
	private TextField outputFileDir;
	
	@FXML
    private TextField TextFieldCustomExtractor;
	
	@FXML
    private TextField TextFieldRegex;
	
	@FXML
    private CheckBox CheckRegex, CheckCustomExtractor;

    @FXML
    private CheckBox CheckBfs, CheckRecursive;

    @FXML
    private CheckBox CheckHtml, CheckScripts, CheckCss, CheckLinks, CheckPorts, CheckMedia, CheckEmail, CheckTel;
    @FXML
    void ActionExtractSettings(ActionEvent event) {
   
    }
    
    
        
    
    @FXML
    void ActionOutputFileSelector(ActionEvent event) {
    	 Node source = (Node) event.getSource();
    	    Window stage = source.getScene().getWindow();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("src"));

             File selectedDirectory = directoryChooser.showDialog(stage);

             outputFileDir.setText(selectedDirectory.getAbsolutePath());
            
    }
    
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
    		
    		
    		//
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
        	///
        	//
        	
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
        
        	//end recur
        	///
        	//    	
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
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextFieldUrlList.setText(BeginScrape.webAddress);
	}

}