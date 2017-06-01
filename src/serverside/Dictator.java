package serverside;

import entries.EntryDic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mercenery on 02.06.2017.
 */
public class Dictator{
	private static final String  search   = "SEARCH";
	private static final String  add      = "ADD";
	private static final String  reduct   = "REDUCT";
	private static final String  delete   = "DELETE";
	private static       boolean killFlag = false;
	
	public static void main(String[] args){
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try{
			ConnectToDB();
			StartServerMultiThreadInterconnect();
			while(! killFlag){
				String isDead = bufferedReader.readLine();
				if(isDead.equalsIgnoreCase("killserver")){
					setKillFlag(true);
				}
				HadlingDialogs();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setKillFlag(boolean killFlag){
		Dictator.killFlag = killFlag;
	}
	
	public void todoSelector(String word, String definition){
		
		switch(word){
			case search:
				searchDB(word);
				break;
			case add:
				addEntry(word, definition);
				break;
			case reduct:
				reductDB(word, definition);
				break;
			case delete:
				deleteEntry(word);
				break;
		}
		
		
	}
	
	private void deleteEntry(String word){
		// delete entry by keyword
	}
	
	private void reductDB(String word, String definition){
		//reduct entry
	}
	
	private void addEntry(String word, String definition){
		// update DB
	}
	
	private String searchDB(String word){
		String definition = null;
		// SQL request
		return definition;
	}
}