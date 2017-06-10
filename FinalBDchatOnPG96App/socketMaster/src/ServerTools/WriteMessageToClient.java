package ServerTools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mercenery on 26.05.2017.
 */
public class WriteMessageToClient implements Runnable{
	private Socket socket;
	
	public WriteMessageToClient(Socket socket) throws IOException{
		this.socket = socket;
		
	}
	
	public WriteMessageToClient() throws IOException{
		socket = new Socket("localhost", 4444);
	}
	
	@Override public void run(){
		try(PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
			while(true){
				if(! bufferedReader.ready()){
					Thread.sleep(50);
					continue;
				} else {
					String string = bufferedReader.readLine();
					if(string.equalsIgnoreCase("quit")){
						System.out.println("Quit command detected , kill all connection ...");
						Thread.sleep(3000);
						printWriter.flush();
						printWriter.close();
						bufferedReader.close();
						socket.close();
						break;
					} else {
						printWriter.print(string);
						printWriter.flush();
						Thread.sleep(50);
						continue;
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
