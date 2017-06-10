package LibForChat;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mercenery on 27.05.2017.
 */
public class ClientHandlerChatStoring implements ListToDBChatHistoryDataTransport{
	private static Connection connection;
	private static HashMap<Integer, ArrayList<String>> map;
	private static String string;
	
	public ClientHandlerChatStoring(HashMap<Integer, ArrayList<String>> map){
		this.map = map;
	}
	
	@Override public void createConnect(){
	
	}
	
	@Override public void transferDataFromListToDB(){
	
	}
	
	@Override public void endWork(){
	
	}
}
