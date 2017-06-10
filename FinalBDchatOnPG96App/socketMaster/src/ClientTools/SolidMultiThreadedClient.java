package ClientTools;

import LibForChat.ClMessage;

import java.io.*;
import java.net.Socket;

/**
 * Created by mercenery on 27.05.2017.
 */
public class SolidMultiThreadedClient{
	
	// common variables declaring
	private static final int ID = (int)(Math.random()) * 2147483647;
	private static Socket    socket;
	private static ClMessage clMessage;
	
	
	public SolidMultiThreadedClient() throws IOException{

// dialog socke creation
		socket = new Socket("localhost", 4444);
	}
	
	public static void main(String[] args){
		try{

// common variables init
			ObjectOutputStream ooStream        = new ObjectOutputStream(socket.getOutputStream());
			DataInputStream    dataInputStream = new DataInputStream(socket.getInputStream());
			BufferedReader     bufferedReader  = new BufferedReader(new InputStreamReader(System.in));
			String             message;
			String             tmpFromServ;

// invite to chat
			System.out.println("Input message: ");
			
			while(true){

// main loop start
				if((message = bufferedReader.readLine()) != null){ //look if client type in message

// look if client type in special keyword -" QUIT" to end chat.
					if(message.equalsIgnoreCase("quit")){ // handling closing command
						System.out.println("Quit signal acquired, close connect...");
						
						ooStream.writeObject(new ClMessage(ID, message)); // send message to server
						ooStream.flush(); // realising buffer
						Thread.sleep(50);

// before closing connection look if server send som message back&display it & exiting
						if((tmpFromServ = dataInputStream.readUTF()) != null){
							System.out.println(tmpFromServ);
							Thread.sleep(50);
							ooStream.close();
							dataInputStream.close();
							socket.close();
							break;
						}

// exiting in anythere
						Thread.sleep(50);
						ooStream.close();
						dataInputStream.close();
						socket.close();
						break;

// in case if QUIT command was not detected from user
					} else {
						ooStream.writeObject(new ClMessage(ID, message)); // send message & flushing
						ooStream.flush();
						Thread.sleep(50);

// after sending clien wait if server reply something&display it
						if((tmpFromServ = dataInputStream.readUTF()) != null){
							
							System.out.println(tmpFromServ);
							System.out.println("Keyword matching search start ... ");

// look if reply is QUIT command exiting
							if(tmpFromServ.equalsIgnoreCase("quit")){
								
								System.out.println(
									"Match key word found - Quit signal acquired, from server close connect...");
								Thread.sleep(50);
								ooStream.close();
								dataInputStream.close();
								socket.close();
								break;
							} else {

// if keyword QUIT was not found in server reply continue chatting
								Thread.sleep(50);
								System.out.println("Input message: ");
								continue;
							}

// in case if server dose not reply something continue chatting
						} else {
							Thread.sleep(50);
							continue;
						}
					}

// look if client dose not send anything continue listen client's console
				} else {
					Thread.sleep(50);
					continue;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}