import java.io.*;
import java.net.Socket;

/**
 * Created by mercenery on 19.05.2017.
 */
public class ClientMonoThred{
	private static String address = "localhost";
	private static int    port    = 4444;
	
	public ClientMonoThred(String address, int port){
		this.address = address;
		this.port = port;
	}
	
	private static Socket socket;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("ClientMonoThred loading, please wait...");
		Thread.sleep(500);
		
		Socket fromserver = new Socket(address, port);
		System.out.println("Connected to server on " + address + " port: " + port);
		Thread.sleep(500);
		
		try{
			BufferedReader in  = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
			PrintWriter    out = new PrintWriter(fromserver.getOutputStream(), true);
			BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
			
			while(true){
				if(inu.ready()){
					String tmpUi = inu.readLine();
					
					if(tmpUi.equalsIgnoreCase("quit")){
						System.out.println("Ending dialog...");
						Thread.sleep(1000);
						out.print(tmpUi);
						out.flush();
						
						if(in.ready()){
							String tmpSi = in.readLine();
							System.out.println(tmpSi);
						}
						
						in.close();
						inu.close();
						out.close();
						fromserver.close();
						
						break;
					} else {
						out.print(tmpUi);
						out.flush();
						Thread.sleep(100);
						if(in.ready()){
							String tmpSi = in.readLine();
							System.out.println(tmpSi);
							
							if(tmpSi.equalsIgnoreCase("quit")){
								System.out.println("Server closing connection...");
								Thread.sleep(3000);
								in.close();
								inu.close();
								out.flush();
								out.close();
								fromserver.close();
								break;
							} else {
								Thread.sleep(2000);
								continue;
							}
						}
					}
				} else {
					Thread.sleep(1000);
					continue;
				}
				System.out.println("..done.");
				Thread.sleep(500);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
}


