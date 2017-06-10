package TestingTools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mercenery on 30.05.2017.
 */
public class Server{
	ServerSocket     serverSocket;
	Socket           socket;
	DataInputStream  dataInputStream;
	DataOutputStream dataOutputStream;
	
	private boolean serverWorks = true;
	
	public static CopyOnWriteArrayList<Dialogs> getDialogs(){
		return dialogs;
	}
	
	public static CopyOnWriteArrayList<Dialogs> dialogs;
	
	public static void main(String[] args){
		new Server();
	}
	
	public void setServerWorks(boolean serverWorks){
		this.serverWorks = serverWorks;
	}
	
	public boolean isServerWorks(){
		
		return serverWorks;
	}
	
	public Server(){
		try{
			this.serverSocket = new ServerSocket(4444);
			dialogs = new CopyOnWriteArrayList<Dialogs>();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try{
			while(serverWorks){
				System.out.println("Socket creating ...");
				Socket socket = serverSocket.accept();
				System.out.println("connected to : " + socket.getLocalSocketAddress());
				Dialogs dialog = new Dialogs(socket, this);
				dialogs.addIfAbsent(dialog);
				dialog.start();
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}