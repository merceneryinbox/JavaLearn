//package ServerTools;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by mercenery on 27.05.2017.
// */
//public class SolidMultiThreadedServer{
//	private static ServerSocket serverSocket;
//	private static ConcurrentHashMap<Integer, ArrayList<String>> chatHistory;
//
//
//	public SolidMultiThreadedServer() throws IOException{
//
//	}
//
//	public void killServer() throws IOException{
//		serverSocket.close();
//		System.exit(0);
//	}
//
//	public static void main(String[] args) throws IOException{
//		serverSocket = new ServerSocket(4444);
//		ExecutorService clientsPool = Executors.newFixedThreadPool(13);
//		try{
//			Socket socket = serverSocket.accept();
//			while(! serverSocket.isClosed()){
//				clientsPool.execute(new ClientsHandler(socket));
//			}
//		} finally{
//			clientsPool.shutdown();
//		}
//	}
//}
