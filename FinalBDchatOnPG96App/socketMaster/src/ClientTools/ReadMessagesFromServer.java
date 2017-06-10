package ClientTools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

/**
 * Created by mercenery on 26.05.2017.
 */
public class ReadMessagesFromServer implements Runnable{
	private static Socket socket;
	
	public ReadMessagesFromServer(Socket socket){
		
		this.socket = socket;
	}
	
	@Override public void run(){
		
		while(true){
			try(DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())){
				if(dataInputStream.available() > 0){
					String string = dataInputStream.readUTF();
					if(string.equalsIgnoreCase("quit")){
						Date date = new Date();
						System.out.println("Quit signal was send from server at : " + date);
						System.out.println("connection closed...");
						dataInputStream.close();
						socket.close();
					} else {
						Date date = new Date();
						System.out.println("ServerMonoThread message at : " + date + " - " + string);
						Thread.sleep(100);
					}
				} else {
					Thread.sleep(100);
					continue;
				}
			} catch(IOException e) {
				e.printStackTrace();
			} catch(InterruptedException e) {
				e.printStackTrace();
			} finally{
				try{
					socket.close();
				} catch(Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
		}
	}
}
