package interfaces;

/**
 * Created by mercenery on 01.06.2017.
 */
public interface Dictionary{
	
	// search definition by keyword
	public void search();
	
	// add entry = keyword-definition
	public void add();
	
	// correct definition by word
	public void correct();
	
	// delete entry from DB
	public void delete();
}
