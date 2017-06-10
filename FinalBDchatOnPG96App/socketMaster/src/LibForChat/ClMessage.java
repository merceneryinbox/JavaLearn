package LibForChat;/*
 * Mercenery _ CopyLeft. For Learning Purpose.
 */

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mercenery on 22.05.2017.
 */
public class ClMessage extends OutputStream implements ClServMessages{
	private int    id;
	private String message;

	public ClMessage(int id, String message) {
		this.id = id;

		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getId() {
		return id;

	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getMessage() {
		return message;

	}

	@Override
	public void write(int b) throws IOException {

	}
}
