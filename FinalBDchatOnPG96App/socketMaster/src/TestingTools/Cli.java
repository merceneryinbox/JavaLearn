package TestingTools;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;

/**
 * Created by mercenery on 28.05.2017.
 */
public class Cli{
	
	// common resources declaring
	private static Socket fromserver = null;
	private static BufferedReader in, inu;
	private static PrintWriter out;
	private static String      fuser, fserver;
	
	public static void main(String[] args) throws IOException, InterruptedException{

// invitation to send message
		System.out.println("Welcome to Client side");
		System.out.println("Connecting to... ");

// initializing common resources
		fromserver = new Socket("localhost", 4444);
		
		in = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
		out = new PrintWriter(fromserver.getOutputStream(), true);
		inu = new BufferedReader(new InputStreamReader(System.in));
		try{
// staart communication
			while(true){

// read user typing and check to avoid NullPointerWxception
				if((fuser = inu.readLine()) != null){

// write message to server
					out.println(fuser);
					out.flush();

// check if it's closing command and close all resources if it's so
					if(fuser.equalsIgnoreCase("quit")){
						System.out.println("Killing process started by client's initiative... close dialogs.");
						Thread.sleep(50);
						System.out.println(in.readLine());
						out.close();
						in.close();
						inu.close();
						fromserver.close();
						break;
					}

// check if server sand message, and start output it on the user's screen
					if(in.ready()){
						fserver = in.readLine();
						System.out.println(fserver);

// check if server send closing command closing all resources
						if(fserver.equalsIgnoreCase("quit")){
							
							System.out.println("Killing process started by server's initiative... close dialogs.");
							out.close();
							in.close();
							inu.close();
							fromserver.close();
							break;
						}
					}
				}

// in case if client not send messages yet, try to read from socket if server send some messages and display them
				else if((fserver = in.readLine()) != null){
					
					System.out.println("Server said: " + fserver); // display

// check if server send closing command closing all resources
					if(fserver.equalsIgnoreCase("quit")){
						
						System.out.println("Killing process started by server's initiative... close dialogs.");
						out.close();
						in.close();
						inu.close();
						fromserver.close();
						break;
					} else {
						Thread.sleep(50);
						continue;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * список багов:
 * <p> если первым диалог начинает сервер то его сообщения считываются клиентом
 * с задержкой на количество сообщений , которое было отправлено клиенту до начала
 * передачи клиентом, поэтому временно отключена возможность писать у сервера
 * оставлен только эхо ответ.
 * *
 */