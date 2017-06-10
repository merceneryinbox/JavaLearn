package TestingTools;

import java.io.*;
import java.net.Socket;

/**
 * Created by mercenery on 30.05.2017.
 */
public class Dialogs extends Thread{
	Socket           socket;
	Server           server;
	DataOutputStream dataOutputStream;
	DataInputStream  dataInputStream;
	BufferedReader   bufferedReader;
	
	public Dialogs(Socket socket, Server server){
		this.socket = socket;
		this.server = server;
	}
	
	@Override public synchronized void start(){
		run();
	}
	
	@Override public void run(){
		try{
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true){
				while(! bufferedReader.ready()){
					Thread.sleep(30);
				}
				
				String inputMess = bufferedReader.readLine();
				System.out.println("client " + socket.getLocalSocketAddress() + " said : " + inputMess);
				
				dataOutputStream.writeUTF("echo : " + inputMess);
				dataOutputStream.flush();
			}
			
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
