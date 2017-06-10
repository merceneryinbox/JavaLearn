package TestingTools;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by mercenery on 30.05.2017.
 */
public class HandlingDialog extends Thread{
	static ExecServerEvolution execServerEvolution;
	static Socket              socket;
	
	static DataOutputStream dataOutputStream;
	static DataInputStream  dataInputStream;
	Semaphore semaphore;
	
	static CopyOnWriteArrayList<HandlingDialog> dialogs;
	
	public HandlingDialog(Semaphore semaphore, Socket socket){
		
		this.semaphore = semaphore;
		this.socket = socket;
	}
	
	@Override public synchronized void start(){
		run();
	}
	
	@Override public void run(){
		
		try{
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
					socket.close();
					break;
				}
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally{
			try{
				semaphore.release();
				dataOutputStream.close();
				dataInputStream.close();
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}