package TestingTools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by mercenery on 31.05.2017.
 */
public class ExecServer2{
	static ServerSocket   serverSocket;
	static Socket         socket;
	static BufferedReader bufferedReader;
	
	DataOutputStream dataOutputStream;
	DataInputStream  dataInputStream;
	
	static CopyOnWriteArrayList<String> history  = new CopyOnWriteArrayList<>();
	static CopyOnWriteArrayList<Socket> poolList = new CopyOnWriteArrayList<>();
	
	static boolean serverAlive = true;
	
	
	public static void killServer(){
		serverAlive = false;
	}
	
	public ExecServer2(){
		System.out.println("Wait for client contact...");
		
		ExecutorService poolOfClients = Executors.newFixedThreadPool(12);
		ExecutorService poolDialogs   = Executors.newFixedThreadPool(25);
		Semaphore       semaphore     = new Semaphore(25);
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			
			serverSocket = new ServerSocket(4444);
			
			while(serverAlive){
				semaphore.acquire();
				socket = serverSocket.accept();
				poolList.add(socket);
				System.out.println(" connected !");
				Thread.sleep(2000);
				if(bufferedReader.ready()){
					String s = bufferedReader.readLine();
					if(s.equalsIgnoreCase("quit")){
						killServer();
					}
				} else {
					handlDialog(socket);
				}
			}
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally{
			try{
				bufferedReader.close();
				for(Socket so : poolList){
					so.close();
				}
				serverSocket.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void handlDialog(final Socket socket2){     // ? не понятно почему параметр - класс Socket должен быть
		// FINAL ???
		try{
			DataInputStream  dataInputStream  = new DataInputStream(socket2.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket2.getOutputStream());
			while(true){
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
					socket2.close();
					break;
				}
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException{
		new ExecServer2();
	}
}