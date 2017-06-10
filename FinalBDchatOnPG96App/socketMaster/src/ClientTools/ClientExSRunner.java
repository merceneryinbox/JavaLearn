package ClientTools;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mercenery on 26.05.2017.
 */
public class ClientExSRunner extends Thread{
	private static int    id     = (int)Math.random() * 2147483647;
	private        Socket socket = new Socket("localhost", 4444);
	
	public ClientExSRunner(int id, Socket socket) throws IOException{
		this.id = id;
		this.socket = socket;
	}
	
	public ClientExSRunner() throws IOException{
	}
	
	@Override public synchronized void start(){
		run();
	}
	
	@Override public void run(){
		
		WriteToServerObjectMessage writeToServerObjectMessage = new WriteToServerObjectMessage(id, socket);
		ReadMessagesFromServer     readMessagesFromServer     = new ReadMessagesFromServer(socket);
		
		ExecutorService            executorThreadsPool        = Executors.newCachedThreadPool();
		
		executorThreadsPool.execute(writeToServerObjectMessage);
		executorThreadsPool.execute(readMessagesFromServer);
		executorThreadsPool.shutdown();
	}
}
