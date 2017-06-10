package serverside;

import entries.EntryDic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by mercenery on 02.06.2017.
 */

public class Dictator{
	
	static ServerSocket serverSocket;
	static Socket       socket;
	
	static ExecutorService exeDic;
	
	public static EntryDic entryDic;
	
	/**
	 * Поднимает сервер для работы с клентом на порту 5432  инициализирует обработчиков
	 * чтения объектов <code>EntryDic</code> из канала и
	 * передачи в канал(сокет) и ждёт обращения <code>accept();</code>accept();
	 * после handShaking-а инициализирет объект EntryDic переданным от клиента в сокет объектом
	 * <code>EntryDic</code> (методом ObjectInputStream), вызывает метод селектор выбора операции
	 * (удаление записи, поиск определения по слову, добавление записи, корректировка определения)
	 * по флагу в самом объекте , результирующий EntryDic из пула нитей <code>exeDic</code> который запускает
	 * <code>Runnable</code> объект <code>CSDialog</code> в котором и происходит всё общение формы с серввером,
	 * <code>CSDialog</code> в свою очередь <code>Callable </code> объект - <code>DBServerHandler</code> - сервер
	 * базы данных , который выполняет действия в базе и возвращает ответ из БД в <code>CSDialog</code> в виде
	 * <code>EntryDic</code> в сокет методом (<code>ObjectOutputStream</code>-а - <code>writeObject();</code>)
	 * клиент принимает на своей стороне объект , расшифровывает записи в нём и отображает результат
	 * в GUI.
	 *
	 * @param args
	 */
	public static void main(String[] args){
		try{
			
// создаём пул нитей для работы с сервером базы и создания подсерверов общения
			exeDic = Executors.newFixedThreadPool(10);
			
// открываем порт для подключения
			serverSocket = new ServerSocket(23345);
			System.out.println("Dictator : serversocket in main method in Dictator created");
// начинаем рабочий цикл по приёму подключений с запросами
			while(! serverSocket.isClosed()){
				System.out.println("Dictator : main loop in main method in Dictator starts");
// сервер становится в ожидание подключения
				socket = serverSocket.accept();
				System.out.println("Dictator : socket accept  in main method in  Dictator waits starts");
				
// когда подключение установлено - создаёт новую нить в которой делигирует своё общение
//  с клиентом и базой данных объекту - CSDialog
				System.out.println("Dictator : exeDic in Dictator creats new thread with CSDialog(socket accept)");
				exeDic.execute(new CSDialog(socket));
			}
			
// закрываем пул нитей
			exeDic.shutdown();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally{
			try{
// закрываем ресурс создания сокетов общения
				serverSocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
