package TestingTools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mercenery on 28.05.2017.
 */
public class Ser{
	public static void main(String[] args) throws IOException, InterruptedException{
// common variables declaration
		
		System.out.println("Welcome to Server side");
		BufferedReader in, ins;
		PrintWriter    out;
		
		ServerSocket servers    = null;
		Socket       fromclient = null;

// create server socket
		try{
			servers = new ServerSocket(4444);
		} catch(IOException e) {
			System.out.println("Couldn't listen to port 4444");
			System.exit(- 1);
		}
// create duplex channel to talk with clients
		
		try{
			System.out.print("Waiting for a client...");
			fromclient = servers.accept();
			System.out.println("Client connected");
		} catch(IOException e) {
			System.out.println("Can't accept");
			System.exit(- 1);
		}
// initializing common variables


// temporary strings declaring
		
		String input, output;
		
		System.out.println("Wait for messages"); // dialog invitation

// dialog starts
		try{
			in = new BufferedReader(
				new InputStreamReader(fromclient.getInputStream())); // for read messages from client
			// through socket
			
			out = new PrintWriter(fromclient.getOutputStream(), true); // for writing messages to client
//			ins = new BufferedReader(
//				new InputStreamReader(System.in)); // for manual console sending messages & commands
//
			while(true){

// initializing temp string variable & avoiding NullPointEsception throw
				if((input = in.readLine()) != null){

// echo reply to client within own client's message
					out.println("Server echo reply ::: " + input);

// processor register release
					out.flush();

// display client's message on server's sccreen
					System.out.println("User said : " + input);

// look if client's message is special closing word then closing all resources
					if(input.equalsIgnoreCase("quit")){
						System.out.println("Disconnect signal from client was detected....");
						out.close();
						in.close();
						fromclient.close();
						servers.close();
						break;

// another case where message is not closing command, look if server himself sending message to client then sending
					}
//					else if((output = ins.readLine()) != null){
//						out.println(output);
//						out.flush();
//
//// look if server send closing command, then closing all resources
//						if(output.equalsIgnoreCase("quit")){
//							fromclient.close();
//							servers.close();
//							break;
//
//// if not, wait some time and begin listen to client from start
//						} else {
//							Thread.sleep(1000);
//							continue;
//						}
//					}

// this case comes if client said nothing yet, server wait a few & begin listening from start
				}
//				else if((output = ins.readLine()) != null){
//
//					out.println(output);
//					out.flush();
//
//					if(output.equalsIgnoreCase("quit")){
//
//						System.out.println("Suicide begin , all connections closing...");
//						in.close();
//						ins.close();
//						out.close();
//						fromclient.close();
//						servers.close();
//					} else {
//						Thread.sleep(1000);
//						continue;
//					}
//				}
			}

// closeing all resources on exit
			out.close();
			in.close();
			fromclient.close();
			servers.close();
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