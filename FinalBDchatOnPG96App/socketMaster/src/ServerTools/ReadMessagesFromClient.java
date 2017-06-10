package ServerTools;

import LibForChat.ClMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mercenery on 26.05.2017.
 */
public class ReadMessagesFromClient implements Runnable{
	private Socket    socket;
	private ClMessage clMessage;
	private String    message;
	private int       id;
	private ArrayList<String>                   tmpAr      = new ArrayList<>();
	private HashMap<Integer, ArrayList<String>> clMesStory = new HashMap<>();
	
	public ReadMessagesFromClient(Socket socket){
		this.socket = socket;
	}
	
	public ReadMessagesFromClient() throws IOException{
		socket = new Socket("localhost", 4444);
	}
	
	@Override public void run(){
		try(ObjectInputStream oistream = new ObjectInputStream(socket.getInputStream())){
			
			while(true){
				
				if(oistream.available() > 0){
					clMessage = (ClMessage)oistream.readObject();
					message = clMessage.getMessage();
					id = clMessage.getId();
					
					if(message.equalsIgnoreCase("quit")){
						System.out.println(
							"Suicide signal - QUIT was detected from : " + id + " this socket " + "terminated...");
						Thread.sleep(3000);
						if(! clMesStory.containsKey(id)){
							tmpAr.add(message);
							clMesStory.put(id, tmpAr);
							tmpAr.clear();
							System.out
								.println("Suicide Message : " + message + " come from : " + id + " like : " + message);
							Thread.sleep(3000);
						} else {
							tmpAr = clMesStory.get(id);
							tmpAr.add(message);
							clMesStory.put(id, tmpAr);
							tmpAr.clear();
							System.out
								.println("Suicide Message : " + message + " come from : " + id + " like : " + message);
							Thread.sleep(30);
						}
						break;
						
					} else {
						
						if(! clMesStory.containsKey(id)){
							tmpAr.add(message);
							clMesStory.put(id, tmpAr);
							tmpAr.clear();
							System.out.println("Message : " + message + " come from : " + id + " like : " + message);
							Thread.sleep(30);
						} else {
							tmpAr = clMesStory.get(id);
							tmpAr.add(message);
							clMesStory.put(id, tmpAr);
							tmpAr.clear();
							System.out.println("Message : " + message + " come from : " + id + " like : " + message);
							Thread.sleep(30);
						}
					}
				} else {
					Thread.sleep(50);
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
