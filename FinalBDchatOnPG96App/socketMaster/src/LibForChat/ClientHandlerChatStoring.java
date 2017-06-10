package LibForChat;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mercenery on 27.05.2017.
 */
public class ClientHandlerChatStoring implements ListToDBChatHistoryDataTransport , Runnable{
	private static Connection        connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet         resultSet;
	
	private static ConcurrentHashMap<Integer, ArrayList<String>> map;
	private        int                                           id;
	private        ArrayList<String>                             tmpAr;
	private        String                                        message;
	private static String                                        string;
	
	public ClientHandlerChatStoring(ConcurrentHashMap<Integer, ArrayList<String>> map){
		this.map = map;
	}
	
	@Override public void createConnect() throws SQLException{
		connection = DriverManager.getConnection("localhost", "postgres", "postgres");
	}
	
	@Override public void transferDataFromListToDB(){
		StringBuilder stringBuilder = new StringBuilder();
		for(ConcurrentHashMap.Entry<Integer, ArrayList<String>> entry : map.entrySet()){
			id = entry.getKey();
			tmpAr = entry.getValue();
			for(String s : tmpAr){
				stringBuilder.append(s);
			}
			tmpAr.clear();
			// now we have one complete pair of ID & ID's messages to store these into dataBase table
			// realise it feather
		}
		endWork();
	}
	
	@Override public void endWork(){
		try{
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override public void saveChat(){
		try{
			createConnect();
			transferDataFromListToDB();
			endWork();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override public void run(){
		saveChat();
	}
}
