package serverside;

import entries.EntryDic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by mercenery on 03.06.2017.
 */
public class CSDialog implements Runnable{
	private        EntryDic        entryDic;
	private static Socket          socket;
	private        EntryDic        replyEntryDic;
	private static ExecutorService exe;
	
	/**
	 * Initialize the static variable <code> socketFromDictator </code>.
	 * Further when initializing CSDialog-and create links through which will communicate with the applicant in client
	 * <code> socketFromDictator </code> (<code> objectInputStream and objectOutputStream <code> </code> </code>), as well as create
	 * thread pooling <code> exe </code>, which will continue to put every single request from a single
	 * client for simultaneous processing.
	 *
	 * @param socketFromDictator
	 */
	
	public CSDialog(Socket socketFromDictator) throws InterruptedException{
		this.socket = socketFromDictator;
		
		System.out.println(
			"CSDialog : constructor starts , socketFromDictator initiated , io channels for server creation starts");
		
		System.out.println(
			"CSDialog : data on socket from Dictator: \n " + socket.getPort() + " " + socket.getChannel() + " " + socket
				.getRemoteSocketAddress());
		
		// инициализируем пул нити для из нити для запроса к базе данных
		exe = Executors.newSingleThreadExecutor();
		System.out.println("CSDialog : monothread executor in CSDialog ctreated");
		System.out.println("CSDialog : CSDialog constructor ended");
	}
	
	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 * <p>
	 * <p>
	 * Создаём цикл - "пока сокет не закрыт" в котором запускаем ожидание поступления данных в канал <code>socket</code>
	 * <code>while(objectInputStream.available() == 0)</code> который усыпляет нить на 303 милисекунды и обновляет
	 * информацию  поступивших данных , если в одном из проходов данные обнаружены - (т.к. мы знаем что в канал клиент
	 * записывает данные типа - <code>EntryDic</code> то считываем  сериализуемый объект методом <code>readObject()</code>
	 * ссылки <code>objectInputStream</code>, которая отвечает за чтение из канала <code>socket</code> приводим его
	 * явным образом к типу - <code>EntryDic</code> для возможности получения доступа к его методам и переменным.
	 * Далее в объект - типа - <code>Future</code> - <code>sqlReply</code> сохраняем возвращаемую <code>Callable</code>
	 * объектом <code>DBServerHandler</code>  ссылку типа <code>EntryDic</code> с помощью метода <code>.get()</code> и
	 * явного приведения к типу - <code>EntryDic</code>.
	 * Далее через канал <code>objectOutputStream</code> отправляем <code>.writeObject()</code> сериализуемый объект
	 * типа - <code>EntryDic</code> обратно клиенту в <code>socket</code>.
	 * Далее происходит десириализация и обработка полей на стороне клиента <code>Main</code>.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run(){
		System.out.println("CSDialog : CSDialog run() method starts");
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
			System.out.println("CSDialog : input/output objects created in run() method");
			System.out.println(
				"CSDialog : input/output objects in run method" + objectInputStream.getClass().getName() + "   " +
				objectOutputStream
					.getClass().getName());

// сохраняем полученный объект от DoItWithEntryDic в свою переменную
			entryDic = (EntryDic)objectInputStream.readObject();
			System.out.println("CSDialog : EntryDic from channel from DoItWithEntry class to CSDialog granted");
			System.out.println(
				"CSDialog : entryDic fields: " + entryDic.flag + " " + entryDic.word + " " + entryDic.definition);
			Thread.sleep(5000);

// с помощью делегата для общения с базой - Callable объекта - DBServerHandler запрашиваем базу, передавая
// объект с кодом действия в конструкторе DBServerHandler

// получив объект от DBServerHandler
			Future<EntryDic> sqlReply = exe.submit(new DBServerHandler(entryDic));
			System.out.println(
				"CSDialog : object Future<EntryDic> sqlReply from DBServerHandler to CSDialog granted EntryDic is : " + sqlReply.get().flag
				+ " " + sqlReply.get().word + " " + sqlReply.get().definition);
			System.out.println("");

// выбираем из него EntryDic
			replyEntryDic = sqlReply.get();
			System.out.println("CSDialog : replyedEntryDic variable instantiated to replyEntryDic in CSDialog");

// пишем полученный от DBServerHandler - EntryDic в ванал сокета общения с DoItWithEntryDic
			if(! socket.isClosed()){
				objectOutputStream.writeObject(replyEntryDic);
				objectOutputStream.flush();
				Thread.sleep(5000);
				System.out.println("CSDialog : reply EntryDic to DoItWithEntry from CSDialog sent back in socket");
				System.out.println("CSDialog : waits not closing...");
				
				Thread.sleep(1000);
// закрываем ресурсы после выполнения транзакции
				exe.shutdown();
				socket.close();
				System.out.println("CSDialog : executor & socket in CSDialog closed");
			}
			else if(socket.isClosed()){
				System.out.println("CSDialog : socket suddenly dead...");
			}
		} catch(IOException e1)
		
		{
			System.err.println("CSDialog : IO troubles\n" + e1.getMessage());
			System.exit(1);
		} catch(ClassNotFoundException e1)
		
		{
			System.err.println("CSDialog : typecasting (EntryDic)sqlReply.get(); troubles\n" + e1.getMessage());
			System.exit(1);
		} catch(InterruptedException e1)
		
		{
			System.err.println("CSDialog : Thread.sleep(303); troubles\n" + e1.getMessage());
			System.exit(1);
		} catch(ExecutionException e)
		
		{
			System.err.println("CSDialog : exe.submit(new DBServerHandler(entryDic)); troubles\n" + e.getMessage());
			System.exit(1);
		} finally
		
		{
			try{
				exe.shutdown();
				socket.close();
			} catch(IOException e) {
				System.err.println("CSDialog : finally closing of IO/socket  troubles\n" + e.getMessage());
				System.exit(1);
			}
		}
	}
}
