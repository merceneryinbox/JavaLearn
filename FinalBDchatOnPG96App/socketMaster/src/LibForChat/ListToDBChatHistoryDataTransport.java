package LibForChat;

import java.sql.SQLException;

/**
 * Created by mercenery on 27.05.2017.
 */
public interface ListToDBChatHistoryDataTransport{
	public void createConnect() throws SQLException;
	public void transferDataFromListToDB();
	public void endWork();
	
	public void saveChat();
}
