package ServerTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mercenery on 26.05.2017.
 */
public class ServerExSRunner extends Thread{
	private static Socket socket;
	private static ServerSocket serverSocket;
	public ServerExSRunner(){
		try{
			serverSocket = new ServerSocket(4444);
			socket = serverSocket.accept();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override public synchronized void start(){
		run();
	}
	
	@Override public void run(){
		ReadMessagesFromClient readMessagesFromClient = new ReadMessagesFromClient(socket);
		WriteMessageToClient writeMessageToClient = null;
		try{
			writeMessageToClient = new WriteMessageToClient(socket);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(readMessagesFromClient);
		executorService.execute(writeMessageToClient);
		
		executorService.shutdown();
	}
}
