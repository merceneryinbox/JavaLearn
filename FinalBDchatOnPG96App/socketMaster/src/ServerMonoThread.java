import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by mercenery on 19.05.2017.
 */
public class ServerMonoThread{
	
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.print("ServerMonoThread initializing on port ...");
		BufferedReader in  = null;
		PrintWriter    out = null;
		
		ServerSocket server = null;
		Thread.sleep(2000);
		Socket fromclient = null;
		
		
		// create server socket
		try{
			server = new ServerSocket(4444);
			System.out.println(server.getLocalPort());
			
			System.out.print("Waiting for a client taught...    ");
			
		} catch(IOException e) {
			System.out.println("Couldn't listen to port " + 4444);
			System.exit(- 1);
		}
		
		try{
			fromclient = server.accept();
			System.out.println(".. client connected");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		System.out.print("Begin in, out IO messages creation..");
		
		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		
		String input;
		
		System.out.println(" .. done.");
		System.out.println("Wait for messages...    ");
		
		while((input = in.readLine() )!= null){
			System.out.println("\nKey words marching search starts...");
			if(input.equalsIgnoreCase("quit")){
				
				System.out.print("Attention keyword detected");
				System.out.print(
					" - Key word  is : " + input.toUpperCase() + ": detected, begin termination process... " + " ");
				out.println("You typed in bad word and now You will be terminated!");
				System.out.print(".. Last message to client sending... ");
				server.close();
				break;
			} else {
				
				System.out.println("\nClientMonoThred's query ::: \"" + input + "\" - from client at: " + (new Date()));
				System.out.println("  .. keywords are not found, continue listening... ");
				out.println("S ::: " + input + " - from client at: " + (new Date()));
				continue;
			}
		}
		
		System.out.println("\nWrite channel closing.");
		out.close();
		System.out.println("Read channel closing.");
		in.close();
		System.out.println("ClientMonoThred socket closing.");
		fromclient.close();
		System.out.println("ServerMonoThread socket closing.");
		server.close();
	}
}
