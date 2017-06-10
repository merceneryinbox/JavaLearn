package TestingTools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mercenery on 30.05.2017.
 */
public class ExecServer{
	static ServerSocket serverSocket;
	static Socket       socket;
	private static final String ADDRESS = "localhost";
	private static final int    PORT    = 4444;
	DataOutputStream dataOutputStream;
	DataInputStream  dataInputStream;
	BufferedReader   bufferedReader;
	
	static CopyOnWriteArrayList<String> history;
	static CopyOnWriteArrayList<Socket> poolList;
	
	public void setServerAlive(boolean serverAlive){
		this.serverAlive = serverAlive;
	}
	
	boolean serverAlive = true;
	
	
	public ExecServer() throws IOException{
		try{
			serverSocket = new ServerSocket(PORT);
			history = new CopyOnWriteArrayList<>();
			poolList = new CopyOnWriteArrayList<>();
			ExecutorService poolOfClients = Executors.newFixedThreadPool(12);
		} catch(IOException e) {
			e.printStackTrace();
		}


//			poolOfClients.execute(new HandlingDialog(this, socket));
		
			System.out.println("Wait for client contact...");
			
			Socket socket = serverSocket.accept();
			handelingDialog(socket);
	}
	
	private void handelingDialog(Socket socket){
		
		System.out.println(" connected !");
		try{
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try{
				while(serverAlive){
					while(! (dataInputStream.available() > 0)){
						Thread.sleep(30);
					}
					
					String in = dataInputStream.readUTF();
					dataOutputStream.writeUTF("Echo reply message : " + in);
					dataOutputStream.flush();
					System.out.println("Message from client : " + in);
					if(in.equalsIgnoreCase("quit")){
						System.out.println("Quit command from client.");
						Thread.sleep(1000);
						dataOutputStream.close();
						dataInputStream.close();
						socket.close();
						serverAlive = false;
					}
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally{
			try{
				dataOutputStream.close();
				dataInputStream.close();
				socket.close();
				serverAlive = false;
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new ExecServer();
	}
	
}
