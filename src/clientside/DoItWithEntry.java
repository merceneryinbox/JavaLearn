package clientside;

import entries.EntryDic;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;


/**
 * Created by mercenery on 06.06.2017.
 */


public class DoItWithEntry implements Callable<EntryDic>{
	
	EntryDic entryDic;
	static Socket             socket;
	static ObjectInputStream  objectInputStream;
	static ObjectOutputStream objectOutputStream;
	
	/**
	 * Конструктор класса при инизиализации инициализирует общую переменную  <code>entryDic</code> типа
	 * <code>EntryDic</code>.
	 *
	 * @param entryDic
	 */
	
	
	public DoItWithEntry(EntryDic entryDic) throws InterruptedException{
		this.entryDic = entryDic;
		System.out.println(
			"DoItWithEntry : Constructor of DoItWith Entry starts: EntryDic iniated in DoItWithEntry constructor, socket creation "
			+ "starts");
		System.out.println(
			"DoItWithEntry :  - entryDic from Controller to DoItWith Entrygranted : " + this.entryDic.flag + " " + this.entryDic.word
			+ " " + this.entryDic.definition);
		Thread.sleep(5000);
		try{
			socket = new Socket("localhost", 23345);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("DoItWithEntry : Socket creation succes in DoIt class, io channels creation starts");
		
		System.out.println("DoItWithEntry : Channels in client's socket in constructor of DoIt created");
		
		System.out.println("DoItWithEntry : Constructor of DoIt ended");
	}
	
	/**
	 * Computes a result, or throws an exception if unable to do so.
	 * <p>
	 * Перегруженный метод <code>call</code> объявляет и инициализирует сокет <code>socket</code> для обработки
	 * поступившего на входе в конструктор <code>entryDic</code>. Объявляет каналы обмена сообщения
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public EntryDic call() throws Exception{
		System.out.println("DoItWithEntry  : Call method in DoItWithEntry  starts ");
		try{
			
			System.out.println(
				"DoItWithEntry  : EntryDic try to write from DoItWithEntry  to CSD in channel - " + entryDic.flag + "" + " " + entryDic.word
				+ " " + entryDic.definition);
// передаём в канал для сервера объект полученный из контроллера
			objectOutputStream.writeObject(entryDic);
			objectOutputStream.flush();
			System.out.println("DoItWithEntry  : entryDic wrote in socket channel to CSDialog, while loop waits for "
			                   + "reply from CSDialog starts");

// дожидаемся ответа
			
			System.out.println("DoItWithEntry  : reply from CSDialog granted");
// возвращаем полученный из сервера объект содержащий внутри себя ответ
			EntryDic replyEntryFromCSDIalog = (EntryDic)objectInputStream.readObject();
			System.out.println(
				"DoIt : EntryDic = " + replyEntryFromCSDIalog.flag + " ; " + replyEntryFromCSDIalog.word + " "
				+ replyEntryFromCSDIalog.definition);
			return replyEntryFromCSDIalog;
		} catch(IOException e) {
			e.printStackTrace();

// в случает ошибки соединения или чтения/записи объекта в канал возвращаем контроллекру ошибку
			return new EntryDic(2, "error ", "error ");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
// в случает ошибки соединения или чтения/записи объекта в канал возвращаем контроллекру ошибку
			return new EntryDic(2, "error ", "error ");
		}
	}
}
