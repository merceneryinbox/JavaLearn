package clientside;

import entries.EntryDic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static entries.EntryDic.*;

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
	public TextField txtReductWord;
	
	@FXML
	public TextArea txtReductDef;
	
	@FXML
	public Button btnReductWordDef;
	
	@FXML
	public Button btnclearWordDef;
	
	@FXML
	public TextField txtdeleteWordDef;
	
	@FXML
	public Button btndeleteWordDef;
	
	ExecutorService executorService;
	
	{
		executorService = Executors.newFixedThreadPool(15);
	}
	
	/**
	 * Обработчик события по нажатию кнопки - <code>fx:id btnsearchDefByWord</code>
	 * создаётся новая ссылочная переменная типа - <code>EntryDic</code> с именем <code>searchEntry</code> куда в
	 * качестве одного из параметров передаётся слово - <code>searchDefineByWord</code>, определение которому нужно найти в словаре.
	 * <p>
	 * Далее <code>searchEntry</code>  передаётся аргументом в конструктор <code>Callable</code>  объекта
	 * <code>DoItWithEntry</code> , который в свою очередь создаётся в параллельной нити в пуле
	 * <code>ExecutorService</code> с именем <code>executorService</code> и возвращает на выходе своего метода <code>call</code>
	 * объект заявленный в <code>Future</code>  - <code>EntryDic</code> во временную переменную -
	 * <code>ReplyFu</code>.
	 * <p>
	 * Далее из полученного в ответе объекта <code>ReplyFu</code> через его метод - <code>get()</code> получаем
	 * <code>EntryDic</code> а из него через внутренние методы геттеры необходимое определение по заданному в поиске слову,
	 * сохраняем его во временную переменную <code>replyDefine</code> типа <code>String</code> и назначаем её в объект
	 * формы - <code>txtAreaSearch</code> методом <code>setText()</code> - выводим определение в ожидаемом поле формы.
	 *
	 * @param actionEvent
	 */
	
	public void searchDefByWord(ActionEvent actionEvent){
		System.out.println("Controller: searchDefByWord is pressed start searching");
		try{
			String searchDefineByWord = txtsearchTXT.getText();
			System.out.println("Controller: looking for definition of word - " + searchDefineByWord + " lookup");
			EntryDic searchEntry = new EntryDic(0, searchDefineByWord, "none");
			searchEntry.word = searchDefineByWord;
			searchEntry.definition="none";
			System.out.println(
				"Controller: data in EntryDic - " + searchEntry.flag + " " + searchEntry.word + " "
				+ searchEntry.definition);
			Thread.sleep(5000);
			
			System.out.println("Controller: Controller send entryDic to handle in DoItWithEntry class in executor service");
			
			Future<EntryDic> ReplyFu = executorService.submit(new DoItWithEntry(searchEntry));
			System.out.println(
				"Controller: submit thread to DoItWithEntry with (searchEntry) done, reply granted - " + ReplyFu
					.getClass().getCanonicalName());
			
			System.out.println("Controller: get() method on ReplyFu calling");
			EntryDic reply = ReplyFu.get();
			
			String replyDefine = reply.definition;
			System.out.println("Controller: " + replyDefine + " reply found");
			txtAreaSearch.setText(replyDefine);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void clearSearch(ActionEvent actionEvent){
		System.out.println("clearSearch");
		txtsearchTXT.clear();
		txtAreaSearch.clear();
	}
	
	/**
	 * *
	 * Обработчик события по нажатию кнопки - <code>fx:id btnaddWordDef</code> создаётся новая ссылочная переменная
	 * типа - <code>EntryDic</code> куда в * качестве параметров конструктора передаётся слово -
	 * <code>addWord</code> и определение <code>addDefinition</code> , которые нужно добавить в словарь.
	 * <p>
	 * Далее <code>new EntryDic()</code>  передаётся аргументом в конструктор <code>Callable</code>  объекта
	 * <code>DoItWithEntry</code> , который в свою очередь создаётся в параллельной нити в пуле
	 * <code>ExecutorService</code> с именем <code>executorService</code> и возвращает на выходе своего метода <code>call</code>
	 * объект заявленный в <code>Future</code>  - <code>EntryDic</code> во временную переменную -
	 * <code>ReplyFu</code>.
	 * <p>
	 * Далее из полученного в ответе объекта <code>ReplyFu</code> через его метод - <code>get()</code> получаем
	 * <code>EntryDic</code> а из него через внутренние методы геттеры результат
	 * сохраняем его во временную переменную <code>replyWord</code> и <code>replyDefine</code> типа
	 * <code>String</code> и назначаем её в объекты формы - <code>txtAddWord</code> и <code>txtAddDef</code> методом
	 * <code>setText()</code>.
	 *
	 * @param actionEvent
	 */
	
	public void addWordDef(ActionEvent actionEvent){
//		System.out.println("addWordDef");
		try{
			String addWord       = txtAddWord.getText();
			String addDefinition = txtAddDef.getText();
			
			System.out.println("Controller: Controller send entryDic to handle in DoItWithEntry class in executor service");
			
			Future<EntryDic> ReplyFu = executorService.submit(
				new DoItWithEntry(new EntryDic(1, addWord, addDefinition)));
			EntryDic reply           = ReplyFu.get();
			String   replyWord       = reply.definition;
			String   replyDefinition = reply.definition;
			
			txtAddWord.setText(replyWord);
			txtAddDef.setText(replyDefinition);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void clearAdd(ActionEvent actionEvent){
		System.out.println("clearAdd");
		txtAddWord.clear();
		txtAddDef.clear();
	}
	
	
	/**
	 * *
	 * Обработчик события по нажатию кнопки - <code>fx:id btnReductWordDef</code> создаётся новая ссылочная переменная
	 * типа - <code>EntryDic</code> куда в качестве параметров конструктора передаётся слово -
	 * <code>redWord</code> и определение <code>redDefinition</code> , которое нужно редактировать в словаре.
	 * <p>
	 * Далее <code>new EntryDic()</code>  передаётся аргументом в конструктор <code>Callable</code>  объекта
	 * <code>DoItWithEntry</code> , который в свою очередь создаётся в параллельной нити в пуле
	 * <code>ExecutorService</code> с именем <code>executorService</code> и возвращает на выходе своего метода <code>call</code>
	 * объект заявленный в <code>Future</code>  - <code>EntryDic</code> во временную переменную -
	 * <code>ReplyFu</code>.
	 * <p>
	 * Далее из полученного в ответе объекта <code>ReplyFu</code> через его метод - <code>get()</code> получаем
	 * <code>EntryDic</code> а из него через внутренние методы геттеры результат
	 * сохраняем его во временную переменную <code>replyWord</code> и <code>replyDefine</code> типа
	 * <code>String</code> и назначаем их в объекты формы - <code>txtReductWord</code> и <code>txtReductDef</code> методом
	 * <code>setText()</code>.
	 *
	 * @param actionEvent
	 */
	
	public void reductWordDef(ActionEvent actionEvent){
//		System.out.println("correctWordDef");
		try{
			String redWord       = txtReductWord.getText();
			String redDefinition = txtReductDef.getText();
			
			System.out.println("Controller: Controller send entryDic to handle in DoItWithEntry class in executor service");
			
			Future<EntryDic> ReplyFu = executorService.submit(
				new DoItWithEntry(new EntryDic(10, redWord, redDefinition)));
			
			EntryDic reply           = ReplyFu.get();
			String   replyWord       = reply.definition;
			String   replyDefinition = reply.definition;
			
			txtReductWord.setText(replyWord);
			txtReductDef.setText(replyDefinition);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearWordDef(ActionEvent actionEvent){
		System.out.println("clearWordDef");
		txtReductWord.clear();
		txtReductDef.clear();
	}
	
	/**
	 * Обработчик события по нажатию кнопки - <code>fx:id btndeleteWordDef</code> создаётся новая ссылочная переменная
	 * типа - <code>EntryDic</code> куда в качестве параметров конструктора передаётся слово -
	 * <code>redWord</code> и определение <code>"none"</code> , которое нужно удалить в словаре.
	 * <p>
	 * Далее <code>new EntryDic()</code>  передаётся аргументом в конструктор <code>Callable</code>  объекта
	 * <code>DoItWithEntry</code> , который в свою очередь создаётся в параллельной нити в пуле
	 * <code>ExecutorService</code> с именем <code>executorService</code> и возвращает на выходе своего метода <code>call</code>
	 * объект заявленный в <code>Future</code>  - <code>EntryDic</code> во временную переменную -
	 * <code>ReplyFu</code>.
	 * <p>
	 * Далее из полученного в ответе объекта <code>ReplyFu</code> через его метод - <code>get()</code> получаем
	 * <code>EntryDic</code> а из него через внутренние методы геттеры результат
	 * сохраняем его во временную переменную <code>replyWord</code> типа <code>String</code> и назначаем их в объекты
	 * формы - <code>txtdeleteWordDef</code> методом <code>setText()</code>.
	 *
	 * @param actionEvent
	 */
	
	public void deleteWordDef(ActionEvent actionEvent){
//		System.out.println("deleteWordDef");
		try{
			String delWord = txtReductWord.getText();
			System.out.println("Controller: Controller send entryDic to handle in DoItWithEntry class in executor "
			                   + "service");
			Future<EntryDic> ReplyFu = executorService.submit(new DoItWithEntry(new EntryDic(- 1, delWord, "none")));
			
			EntryDic reply     = ReplyFu.get();
			String   replyWord = reply.definition;
			
			txtdeleteWordDef.setText(replyWord);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}
	}
}
