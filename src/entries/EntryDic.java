package entries;

import java.io.Serializable;

/**
 * Created by mercenery on 01.06.2017.
 */
public class EntryDic implements Serializable{
	public int SEARCH = 0;
	public int ADD    = 1;
	public int DELETE = - 1;
	public int REDUCT = 10;
	public int ERROR  = 2;
	
	public String word;
	public String definition;
	public int    flag;
	
	/**
	 * Constructor which takes three variables <code> flag </code>, <code> word </code> and <code> definition </code>.
	 *
	 * @param flag
	 * @param word
	 * @param definition
	 */
	public EntryDic(int flag, String word, String definition){
		this.flag = flag;
		this.word = word;
		this.definition = definition;
	}
}
