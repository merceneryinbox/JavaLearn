package LibForChat;/*
 * Mercenery _ CopyLeft. For Learning Purpose.
 */

import java.io.Serializable;

/**
 * Created by mercenery on 22.05.2017.
 */
public interface ClServMessages extends Serializable{

	public abstract int getId();

	void setId(int id);

	public abstract String getMessage();

}
