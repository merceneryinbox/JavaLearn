package ClientTools;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * Created by mercenery on 25.05.2017.
 */
public class ClientExSThread{
	
	public static Socket socket;
	private static       String ADDRESS = "localhost";
	private static       int    PORT    = 4444;
	private static final int    ID      = (int)(Math.random()) * 2147483647;
	private static WriteToServerObjectMessage writeToServerObjectMessage;
	private static ReadMessagesFromServer     readMessagesFromServer;
	
	public static void main(String[] args){
		writeToServerObjectMessage = new WriteToServerObjectMessage(ID,socket);
		readMessagesFromServer = new ReadMessagesFromServer(writeToServerObjectMessage.getSocket());
		ExecutorService executorThreadsPool = Executors.newCachedThreadPool();
		executorThreadsPool.execute(writeToServerObjectMessage);
		executorThreadsPool.execute(readMessagesFromServer);
		executorThreadsPool.shutdown();
		
	}
	
}
