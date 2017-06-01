package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller{
	
	@FXML
	public TextField txtsearchTXT;
	
	@FXML
	public TextArea txtAreaSearch;
	
	@FXML
	public Button btnsearchDefByWord;
	
	@FXML
	public Button btnclearSearch;
	
	@FXML
	public TextField txtAddWord;
	
	@FXML
	public TextArea txtAddDef;
	
	@FXML
	public Button btnaddWordDef;
	
	@FXML
	public Button btnclearAdd;
	
	@FXML
	public TextField txtCorrectWord;
	
	@FXML
	public TextArea txtAreaCorrectWord;
	
	@FXML
	public Button btncorrectWordDef;
	
	@FXML
	public Button btnclearWordDef;
	
	@FXML
	public TextField txtdeleteWordDef;
	
	@FXML
	public Button btndeleteWordDef;
	
	
	public void searchDefByWord(ActionEvent actionEvent){
		System.out.println("searchDefByWord");
	}
	
	public void clearSearch(ActionEvent actionEvent){
		System.out.println("clearSearch");
	}
	
	public void addWordDef(ActionEvent actionEvent){
		System.out.println("addWordDef");
	}
	
	public void clearAdd(ActionEvent actionEvent){
		System.out.println("clearAdd");
	}
	
	public void correctWordDef(ActionEvent actionEvent){
		System.out.println("correctWordDef");
	}
	
	public void clearWordDef(ActionEvent actionEvent){
		System.out.println("clearWordDef");
	}
	
	public void deleteWordDef(ActionEvent actionEvent){
		System.out.println("deleteWordDef");
	}
}
