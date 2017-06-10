package ServerTools;

import LibForChat.ClMessage;
import LibForChat.ClientHandlerChatStoring;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mercenery on 27.05.2017.
 */
public class ClientsHandler implements Runnable{
	
	private Socket    socket;
	private String    serverSaid;
	private ClMessage clMessage;
	private String    message;
	private int       id;
	private ArrayList<String> tmpAr = new ArrayList<>();
	ConcurrentHashMap<Integer, ArrayList<String>> history;
	
	public ClientsHandler(Socket socket, ConcurrentHashMap<Integer, ArrayList<String>> chatHistory){
		this.socket = socket;
		this.history =chatHistory;
	}
	
	@Override public void run(){
		try{
			ObjectInputStream oiStream     = new ObjectInputStream(socket.getInputStream());
			DataOutputStream  outputStream = new DataOutputStream(socket.getOutputStream());
			
			while(true){
				if(oiStream.available() > 0){
					clMessage = (ClMessage)oiStream.readObject();
					message = clMessage.getMessage();
					id = clMessage.getId();
					
					outputStream.writeBytes("Server echo to client ID: " + id + "\n" + message);
					if(message.equalsIgnoreCase("quit")){
						
						System.out.println(
							"Server said : Suicide signal - QUIT was detected from client, ID : " + id + " this socket "
							+ "terminated...");
						outputStream.writeBytes(
							"Suicide signal detected from client ID: " + id + "\n" + "Connection" + " terminated ...");
						oiStream.close();
						outputStream.close();
						socket.close();
						
						if(! history.containsKey(id)){
							tmpAr.add(message);
							history.put(id, tmpAr);
							tmpAr.clear();
							Thread.sleep(20);
							
							try{
								new ClientHandlerChatStoring(history).saveChat();
							} catch(Exception e) {
								e.printStackTrace();
							}
							break;
						} else {
							
							tmpAr = history.get(id);
							tmpAr.add(message);
							history.put(id, tmpAr);
							
							Thread.sleep(20);
							
							try{
								new ClientHandlerChatStoring(history).saveChat();
							} catch(Exception e) {
								e.printStackTrace();
							}
							break;
						}
						
					} else {
						
						if(! history.containsKey(id)){
							tmpAr.add(message);
							history.put(id, tmpAr);
							tmpAr.clear();
							
							Thread.sleep(20);
							try{
								new ClientHandlerChatStoring(history).saveChat();
							} catch(Exception e) {
								e.printStackTrace();
							}
							
							System.out.println("Waiting for client message.");
							Thread.sleep(30);
							continue;
							
						} else {
							
							tmpAr = history.get(id);
							tmpAr.add(message);
							history.put(id, tmpAr);
							tmpAr.clear();
							
							Thread.sleep(20);
							try{
								new ClientHandlerChatStoring(history).saveChat();
							} catch(Exception e) {
								e.printStackTrace();
							}
							
							System.out.println("Waiting for client message.");
							Thread.sleep(30);
							continue;
						}
					}
					
				} else {
					Thread.sleep(50);
					continue;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
