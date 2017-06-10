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
public class ExecServerEvolution{
	static ServerSocket   serverSocket;
	static Socket         socket;
	static BufferedReader bufferedReader;
	
	static CopyOnWriteArrayList<String> history  = new CopyOnWriteArrayList<>();
	static CopyOnWriteArrayList<Socket> poolList = new CopyOnWriteArrayList<>();
	
	static boolean serverAlive = true;
	
	
	public static void killServer(){
		serverAlive = false;
	}
	
	public ExecServerEvolution(){
		System.out.println("Wait for client contact...");
		
		ExecutorService poolOfClients = Executors.newFixedThreadPool(12);
		
	}
	
	public static void main(String[] args) throws IOException{
		
		try{
			
			serverSocket = new ServerSocket(4444);
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			ExecutorService poolDialogs = Executors.newFixedThreadPool(25);
			Semaphore       semaphore   = new Semaphore(25);
			
			
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
					HandlingDialog handlingDialog = new HandlingDialog(semaphore, socket);
					poolDialogs.execute(handlingDialog);
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
}
