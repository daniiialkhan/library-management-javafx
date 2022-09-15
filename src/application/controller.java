package application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class controller implements Book{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	//putting scenes for each button
	//=====================================================================
	//MAIN SCENE SWITCHES
	//=====================================================================
	//Gives Home page - Will be used everywhere but the home page
	
	public void SwitchToMain(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();	
	}
	//give us Search page
	public void SwitchToSearchBook(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("SearchBook.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
	}
	//give us display all books page
	public void SwitchToDisplayAll(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("DisplayAll.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
		
	}
	//give us add books page
	public void SwitchToAddBook(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
	}
	//give us delete books page
	public void SwitchToDeleteBook(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("DeleteBook.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
	}
	//give us issue books page
	public void SwitchToIssueBook(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("IssueBook.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
	}
	//gives us the login screen
	public void SwitchToLogin(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
	}
	//=========================================================================
	//SEARCH SCENE SWITCHES
	//=========================================================================
	
	//gives us search by ID
	public void SwitchToSearchByID(ActionEvent e) throws IOException{
		root = FXMLLoader.load(getClass().getResource("SearchByID.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	//gives us search by Title
	public void SwitchToSearchByTitle(ActionEvent e) throws IOException{
			root = FXMLLoader.load(getClass().getResource("SearchByTitle.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	}
	//gives us search by Author
	public void SwitchToSearchByAuthor(ActionEvent e) throws IOException{
			root = FXMLLoader.load(getClass().getResource("SearchByAuthor.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	}
	//gives us search by Genre
	public void SwitchToSearchByGenre(ActionEvent e) throws IOException{
			root = FXMLLoader.load(getClass().getResource("SearchByGenre.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	}
	//=========================================================================
	//Add Book functions
	//=========================================================================
	
	@FXML
	private TextField TitleInp;
	@FXML
	private TextField AuthorInp;
	@FXML
	private TextField GenreInp;
	@FXML
	private CheckBox IsIssuedInp;
	@FXML
	private Label label;
	
	@Override
	public void AddBookToFile(ActionEvent e) throws Exception {
		if((!TitleInp.getText().isEmpty()) && (!AuthorInp.getText().isEmpty()) && (!GenreInp.getText().isEmpty())) {
			try {
				
				String title = TitleInp.getText().toLowerCase();
				String author = AuthorInp.getText().toLowerCase();
				String genre = GenreInp.getText().toLowerCase();
				String isIssued = Boolean.toString(IsIssuedInp.isSelected());
				
				
				
					
				BufferedWriter writer = new BufferedWriter(new FileWriter("BookList.txt",true));
					
					
				int ID = 1 + returnLastID();
				String out = ID +";"+title+";"+author+";"+genre+";"+isIssued+"\n";
				writer.write(out);
				writer.close();
					
				label.setVisible(true);
				label.setText("Book Added!");
				
				
			}
			catch(IOException f) {
				f.printStackTrace();
				label.setVisible(true);
				label.setText("Error: Book Not Added");
			}
		}
		else {
			label.setVisible(true);
			label.setText("Please fill in all the records!");
		}
	}
	
	
	//=================================================================================
	//Refresh Function is used to clear out labels
	//=================================================================================
	
	public void refresh() {
		label.setVisible(false);
	}
	
	//==================================================================================
	//Searching Functions
	//==================================================================================
	
	
	//very important function, it turns the line taken from file into separated values
	//SearchBy: 1=ID, 2=Title, 3=Author, 4=Genre, 5=isIssued
	public String Separate(String line,int SearchBy) {
		
		int start;
        int end;
        //                                            44     50 (0-49)  the length() method return no of characters
        //example: 2;the call of the wild;jack london;adventure;false
 
        //1st round------------------------------------- substring(start,end)  start se end-1 tak
        start = line.lastIndexOf(";");//44
        end = line.length();//50
        String isIssued = line.substring((start+1),end);//(45,50) => (45,49)
                
        //2nd round-------------------------------------                                            34        44(0-43)
        line = line.substring(0,start);//(0,44) => (0,43), line= "2;the call of the wild;jack london;adventure"
        //update start and end
	    start=line.lastIndexOf(";"); //34
	    end=line.length();	 //44 (0-43)  
	    String genre = line.substring((start+1),end);//(35,44)  => (35,43)
       
	    //3rd round-------------------------------------
	    line = line.substring(0,start);
	    //update start and end
	    start=line.lastIndexOf(";");
	    end=line.length();	    
	    String Author = line.substring((start+1),end);
        
	    //4th Round-------------------------------------
		line = line.substring(0,start);	
		//update start and end
	    start=line.lastIndexOf(";");
	    end=line.length();	    
	    String Title = line.substring((start+1),end);
        
	    //5th Round-------------------------------------
		line = line.substring(0,start);
		//update start and end
	    start=line.lastIndexOf(";");
	    end=line.length();	    
	    String ID = line.substring((start+1),end);
 
	    //----------------------------------------------
	    //SearchBy: 1=ID, 2=Title, 3=Author, 4=Genre, 5=isIssued
	    switch(SearchBy) {
	    case 1: return ID;
		case 2: return Title;
		case 3: return Author;
	    case 4: return genre; 
	    case 5: return isIssued;
	    default: return null;
	    }
		
	
	}
	
	
	
	
	//==================================================================================
	//Search By ID functions
	//==================================================================================
	
	@FXML
	private TextArea output;
	
	@FXML
	private TextField SearchIdText;
	
	@FXML
	public void SearchById(ActionEvent a){
		if(!SearchIdText.getText().isEmpty()) {
			try {
			
			
				String ID_IN = SearchIdText.getText();
	
				BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));
	
				String line;
				String ID;
	
				boolean flag = false;
				
				while((line = Reader.readLine())!=null) {
					ID = Separate(line,1);
					
					if (ID_IN.equals(ID)) {
						//ID found!
						flag = true;
						
						//print
	
						output.setText("\n================================================="
								+ "\nID: " + ID
								+ "\nTitle: " + Separate(line,2)
								+ "\nAuthor Name: " + Separate(line,3)
								+ "\nGenre: " + Separate(line,4)
								+ "\nIs the book Issued: " + Separate(line,5)
								+ "\n=================================================");
						
						break; // saves time
						
					}
					
				}
				
				Reader.close();
				
				
				if (flag == false) {
					//ID not found!
					output.setText("ID not found!");
					
				}
			
				
			
			
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			output.setText("Please enter a value!");
		}
	}
	
	//======================================================================================
	//RETURN LAST ID - used when adding a new book
	//======================================================================================
	
	public int returnLastID(){
		try {
			BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));

			String line;
			String TextID = null;
			int LastID=0;
			
			while((line = Reader.readLine())!=null) {     //Runs till EOF to reach the last book
				TextID = Separate(line, 1);	
			}
			
			Reader.close();
			
			LastID =  Integer.parseInt(TextID);   //convert to int
			return LastID;
			
		}catch(Exception e) {
			System.out.println("ERROR");
			return 0;                            //placed here bcz if the catch clock is accessed there should be a value returned to finish the function
		}
		
		
	}
	//===================================================================================
	//Search by Title functions
	//=======================================================================================
	
	@FXML
	private TextField SearchTitleText;
	
	@FXML
	public void SearchByTitle(ActionEvent a){
		if(!SearchTitleText.getText().isEmpty()) {
		
			try {
				
				
				String Title_IN = SearchTitleText.getText();
				Title_IN = Title_IN.toLowerCase();           //make all lowercase so we can compare 
			
	
				BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));
				
				
				String out = "";
				String line;
				String Title;
				
				boolean flag = false;
				
				while((line = Reader.readLine())!=null) {
					Title = Separate(line,2);
					if (Title.contains(Title_IN)) {
						//Title found!
						flag = true;
						
						
						out += "\n================================================="
								+ "\nID: " + Separate(line,1)
								+ "\nTitle: " + Title
								+ "\nAuthor Name: " + Separate(line,3)
								+ "\nGenre: " + Separate(line,4)
								+ "\nIs the book Issued: " + Separate(line,5);
								//+ "\n=================================================";
					}
					
				}
				
				Reader.close();
				
				if (flag == false) {
					//Title not found!
					out = "Title not found";
					
				}	
				
				//System.out.println(out);  //testing by printing on console
				out += "\n=================================================";
				output.setText(out);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			output.setText("Please enter a value!");
		}
	}
	
	//======================================================================================
	//Search by Author functions
	//=======================================================================================
	
	@FXML
	TextField SearchAuthorText;
	
	@FXML
	public void SearchByAuthor(ActionEvent a){
		if(!SearchAuthorText.getText().isEmpty()) {
			try {
				
				
				String Author_IN = SearchAuthorText.getText();
				Author_IN = Author_IN.toLowerCase();           //make all lowercse 
			
	
			
				BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));
				
				
				String out = "";
				String line;
				String Author;
				
				boolean flag = false;
				
				while((line = Reader.readLine())!=null) {
					Author = Separate(line,3);
					if (Author.contains(Author_IN)) {
						//Title found!
						flag = true;
						
						out += "\n================================================="
								+ "\nID: " + Separate(line,1)
								+ "\nTitle: " + Separate(line,2)
								+ "\nAuthor Name: " + Author
								+ "\nGenre: " + Separate(line,4)
								+ "\nIs the book Issued: " + Separate(line,5);
								//+ "\n=================================================";
					}
					
				}
				
				Reader.close();
				
				if (flag == false) {
					//Title not found!
					out = "Author not found";
					
				}	
				
				//System.out.println(out);
				out += "\n=================================================";
				output.setText(out);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			output.setText("Please enter a value!");
		}
		
	}
	
	//Search by genre functions
	//===================================================================================================
	
	@FXML
	TextField SearchGenreText;
	
	
	@FXML
	public void SearchByGenre(ActionEvent a){
		if(!SearchGenreText.getText().isEmpty()) {
			try {
				
				
				String Genre_IN = SearchGenreText.getText();
				Genre_IN = Genre_IN.toLowerCase();           //make all lower case for easy comparison 
				
	
			
				BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));
				
				
				String out = "";
				String line;
				String Genre;
				
				boolean flag = false;
				
				while((line = Reader.readLine())!=null) {
					Genre = Separate(line,4);
					if (Genre.contains(Genre_IN)) {
						//Title found!
						flag = true;
						
						out += "\n================================================="
								+ "\nID: " + Separate(line,1)
								+ "\nTitle: " + Separate(line,2)
								+ "\nAuthor Name: " + Separate(line,3)
								+ "\nGenre: " + Genre
								+ "\nIs the book Issued: " + Separate(line,5);
								//+ "\n=================================================";
					}
					
				}
				
				Reader.close();
				
				if (flag == false) {
					//Title not found!
					out = "Genre not found";
					
				}	
				
				
				out += "\n=================================================";
				output.setText(out);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			output.setText("Please enter a value!");
		}
		
	}
	
	//===================================================================================================
	//DISPLAY ALL FUNCTIONS
	//===================================================================================================
	
	
	@FXML
	public void DisplayAll(ActionEvent a){
		
		try {
			BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));
			
			
			String out = "";
			String line;
			
			
			while((line = Reader.readLine())!=null) {
				out += "\n================================================="
						+ "\nID: " + Separate(line,1)
						+ "\nTitle: " + Separate(line,2)
						+ "\nAuthor Name: " + Separate(line,3)
						+ "\nGenre: " + Separate(line,4)
						+ "\nIs the book Issued: " + Separate(line,5);
						//+ "\n=================================================";
				}
			
			Reader.close();
			out += "\n=================================================";
			output.setText(out);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//===============================================================================================
	//DELETE BOOK FUNCTIONS
	//===============================================================================================
	
	@FXML
	TextField IdToDelete;
	
	boolean DeleteFlag = false;
	String SelectedLine = "";
	boolean IssueFlag = false;
	
	
	
	@FXML
	public void SearchToDeleteBook(ActionEvent a) {
		if(!IdToDelete.getText().isEmpty()) {
			try {
				
			
				
				String ID_IN = (IdToDelete.getText());
				
					
					
					BufferedReader Reader = new BufferedReader(new FileReader("Booklist.txt"));
					
					
					
					String line;
					String ID;
					
					
					
					boolean flag = false;
					
					while((line = Reader.readLine())!=null) {
						ID = Separate(line,1);
						
						
						if (ID_IN.equals(ID)) {
							//ID found!
							flag = true;
							
							//print
	
							output.setText("\n================================================="
									+ "\nID: " + ID
									+ "\nTitle: " + Separate(line,2)
									+ "\nAuthor Name: " + Separate(line,3)
									+ "\nGenre: " + Separate(line,4)
									+ "\nIs the book Issued: " + Separate(line,5)
									+ "\n=================================================");
							
							DeleteFlag = true;
							SelectedLine = line;
							
							if(Separate(line,5).equals("true")) {
								IssueFlag = true;
							}
							else {
								IssueFlag = false;
							}
							
						}
						
						
					}
					
					if (flag == false) {
						//ID not found!
						output.setText("ID not found!");
						DeleteFlag = false;
						
					}
					
					Reader.close();
				
					
				
				
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else {
			output.setText("Please enter an ID!");
		}
	}
	
	
	
	public void DeleteBook(ActionEvent e) {
		
		if(DeleteFlag) {   //if it is true, that means the book was found, so we are able to delete it
			try {
			      //Construct the new file that will later be renamed to the original filename.
				BufferedWriter writer = new BufferedWriter(new FileWriter("BookList2.txt"));

				BufferedReader reader = new BufferedReader(new FileReader("BookList.txt"));
				
				String line;
				
				
				//Read from the original file and write to the new
			    //unless content matches data to be removed.
			    while ((line = reader.readLine()) != null) {

			      if (!line.equals(SelectedLine)) {

			        writer.write(line+"\n"); //written on booklist2.txt
			        writer.flush();
			      }
			    }
			    
			    
			    writer.close();
			    reader.close();
			    

			    deletes();//file is deleted
		
			    
			    Path source = Paths.get("BookList2.txt");
			    Files.move(source, source.resolveSibling("BookList.txt"));
	
				
			}catch (FileNotFoundException f) {
				f.printStackTrace();
			} catch (IOException f) {
				System.out.println("Failed");
				f.printStackTrace();
			}//else print book was not found so cant delete
			}else {
				output.setText("Error: Please Search for a Book to Delete.");
			}
		}
		
	
	public void deletes() {
		
	    File f = new File("BookList.txt");
		
	    
	    //System.out.println(f.getPath());
		//Delete the original file
	      if (f.delete()) {
	        System.out.println("File deleted");
	      }
	      else
	    	  System.out.println("Could not delete file");
	      
	      
	}
	
	//=====================================================================================================
	//ISSUE BOOK functions
	//=====================================================================================================
	
	
	
	
	
	public void IssueBook() {
		if(DeleteFlag) {    //if it is true, that means the book was found, so we are able to issue it
			if(IssueFlag){  //if it is true, that means the book was already issued
					output.setText("This Book is already issued!\nPlease return book first..");
				}
				else {
					try {
						
					      //Construct the new file that will later be renamed to the original filename.
						BufferedWriter writer = new BufferedWriter(new FileWriter("BookList2.txt"));
		
						BufferedReader reader = new BufferedReader(new FileReader("BookList.txt"));
						
						String line;
						
						
						//Read from the original file and write to the new
					    //unless content matches book to be issued
					    while ((line = reader.readLine()) != null) {
		
					      if (!line.equals(SelectedLine)) {
		
					        writer.write(line+"\n");
					        writer.flush();
					      }
					      else {
					    	  String out; //will set the book to be issued
					    	  out = Separate(line, 1)+";"+Separate(line,2)+";"+Separate(line,3)+";"+Separate(line,4)+";true\n";
					    	  writer.write(out);
					    	  writer.flush();
					      }
					    }
					    
					    label.setVisible(true);
					    label.setText("Book Issued!");
					    
					    writer.close();
					    reader.close();
		
					    
					    deletes();
		
					    Path source = Paths.get("BookList2.txt");
					    Files.move(source, source.resolveSibling("BookList.txt"));
					
					}catch (FileNotFoundException f) {
						f.printStackTrace();
					} catch (IOException f) {
						System.out.println("Deleting or Renaming Failed");
						f.printStackTrace();
					}
				}
			}else {
				output.setText("Error: Please Search for a Book to Issue.");
			}
		
	}
	
	
	public void ReturnBook() {
		if(DeleteFlag) {    //if it is true, that means the book was found, so we are able to issue it
			if(!IssueFlag){ //if it is true, that means the book was already returned
					output.setText("This Book is already Returned!\nPlease Issue book first..");
				}
				else {
					try {
						
					      //Construct the new file that will later be renamed to the original filename.
						BufferedWriter writer = new BufferedWriter(new FileWriter("BookList2.txt"));
		
						BufferedReader reader = new BufferedReader(new FileReader("BookList.txt"));
						
						String line;
		

						//Read from the original file and write to the new
					    //unless content matches book to be issued
					    while ((line = reader.readLine()) != null) {
		
					      if (!line.equals(SelectedLine)) {
		
					        writer.write(line+"\n");
					        writer.flush();
					      }
					      else {
					    	  String out; //will set the book to be issued
					    	  out = Separate(line, 1)+";"+Separate(line,2)+";"+Separate(line,3)+";"+Separate(line,4)+";false\n";
					    	  writer.write(out);
					    	  writer.flush();
					      }
					    }
					    
					    label.setVisible(true);
					    label.setText("Book Returned!");
					    
					    
					    writer.close();
					    reader.close();
		
					    
					    deletes();
		
					    Path source = Paths.get("BookList2.txt");
					    Files.move(source, source.resolveSibling("BookList.txt"));
					
					}catch (FileNotFoundException f) {
						f.printStackTrace();
					} catch (IOException f) {
						System.out.println("Deleting or Renaming Failed");
						f.printStackTrace();
					}
				}
			}else {
				output.setText("Error: Please Search for a Book to Return.");
			}
		
	}
	
	//================================================================================================================
	//LOGIN FUNCTION
	//================================================================================================================
	
	@FXML
	private TextField Username;
	@FXML
	private PasswordField password;
	
	//You can change the preset id and password from here
	
	String LoginUsername = "studentdsu";
	String LoginPassword = "123";
	
	public void Login(ActionEvent a) throws IOException {
		String UserID = Username.getText();
		String pass = password.getText();
		
		if((UserID.equals(LoginUsername))&&(pass.equals(LoginPassword))) {
			SwitchToMain(a);
		}
		else {
			label.setVisible(true);
			label.setText("Incorrect Username/Password Combination.. Please try again.");
		}
		
	}
	
	
	
}
