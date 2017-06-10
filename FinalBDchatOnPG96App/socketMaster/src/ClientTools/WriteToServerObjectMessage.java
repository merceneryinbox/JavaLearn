package ClientTools;

import LibForChat.ClMessage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadFactory;

/**
 * Created by mercenery on 24.05.2017.
 */
public class WriteToServerObjectMessage implements Runnable {
	
	private static int    id;
	private static Socket socket;
	private static String message;
	
	public static Socket getSocket(){
		return socket;
	}
	
	public WriteToServerObjectMessage() throws IOException{
		this.socket = new Socket("localhost", 4444);
	}
	
	public WriteToServerObjectMessage(int id, Socket socket){
		this.id = id;
		this.socket = socket;
	}
	
	@Override public void run(){
		try(ObjectOutputStream ooStream = (ObjectOutputStream)socket.getOutputStream();
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Input message: ");
			while(true){
				if(bufferedReader.ready()){
					message = bufferedReader.readLine();
					if(message.equalsIgnoreCase("quit")){
						System.out.println("Quit signal acquired, close connect...");
						Thread.sleep(50);
						ooStream.flush();
						ooStream.close();
						Thread.sleep(50);
						socket.close();
						Thread.sleep(50);
						break;
					} else {
						ooStream.writeObject(new ClMessage(id, message));
						ooStream.flush();
						Thread.sleep(50);
						System.out.println("Input message: ");
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
	
