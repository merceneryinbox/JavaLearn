package ClientTools;

import LibForChat.ClMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by mercenery on 28.05.2017.
 */
public class TestClient{
private static Socket socket  ;
public TestClient (){
		try{
			socket = new Socket("localhost", 4444);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
			for(int i = 0; i < 10 ; i++){
				objectOutputStream.writeObject(new ClMessage(1,i+i + ""));
			}
			objectOutputStream.close();
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
