package ServerTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mercenery on 25.05.2017.
 */
public class ServerExSThread{
	private static int PORT = 4444;
	private static ServerSocket serverSocket;
	private static Socket socket;
	
	public ServerExSThread(int PORT) throws IOException{
		this.PORT = PORT;
		serverSocket = new ServerSocket(PORT);
		socket = serverSocket.accept();
		
	}
	
	public ServerExSThread() throws IOException{
		serverSocket = new ServerSocket(4444);
		socket = serverSocket.accept();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		ReadMessagesFromClient readMessagesFromClient = new ReadMessagesFromClient(socket);
		WriteMessageToClient writeMessageToClient = new WriteMessageToClient(socket);
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(readMessagesFromClient);
		executorService.execute(writeMessageToClient);
		
		executorService.shutdown();
	
	}
}
