package TestingTools;

import java.io.*;
import java.net.Socket;

/**
 * Created by mercenery on 30.05.2017.
 */
public class ExecClient{
	
	
	static Socket         socket;
	static BufferedReader bufferedReader;
	static private boolean clientAlive = true;
	
	static DataOutputStream dataOutputStream;
	static DataInputStream  dataInputStream;
	
	public ExecClient() throws IOException{
	
	}
	
	public static void setClientAlive(boolean clientAlive){
		ExecClient.clientAlive = clientAlive;
		
	}
	
	public static void main(String[] args) throws IOException{
		socket = new Socket("localhost", 4444);
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataInputStream = new DataInputStream(socket.getInputStream());
		
		try{
			while(clientAlive){
				
				bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				while(! bufferedReader.ready()){
					Thread.sleep(2000);
				}
				String s = bufferedReader.readLine();
				dataOutputStream.writeUTF(s);
				dataOutputStream.flush();
				while(dataInputStream.available() <= 0){
					Thread.sleep(300);
				}
				String in = dataInputStream.readUTF();
				System.out.println(in);
				
				
				if(s.equalsIgnoreCase("quit")){
					setClientAlive(false);
					dataOutputStream.close();
					dataInputStream.close();
					bufferedReader.close();
					socket.close();
					break;
				}
				
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
