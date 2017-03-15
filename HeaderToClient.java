import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Header for messahes from server to client
 *
 * @author Aditya Advani
 * @author Mohita Jethwani
 * @author Rohit Giyanani
 */

public class HeaderToClient implements Serializable{
	/*
	 * 	1: message
	 * 	2: History
	 * 	3: updateList
	 *	5: credentials 
	 * 	6: credentialSuccess
	 * 	7: credentialFailure
	 */
	public int type;
	public String sourceName;
	public HashSet<String> onlineList = new HashSet<String>();
	public String message;
	public HashMap<String,String> history = new HashMap<String,String>();
	
	public HeaderToClient(int type,String sourceName, HashSet<String> onlineList,String message, HashMap<String,String> history)	{
		this.type = type;
		this.sourceName = sourceName;
		this.onlineList = onlineList;
		this.message = message;
		this.history = history;
	}
	
	public static  byte[] getByteArray(Object obj) throws IOException	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = null;
		objectStream = new ObjectOutputStream(byteStream);
		objectStream.writeObject(obj);
		byte[] temp = byteStream.toByteArray();
		return temp;
	}
	
	public static Object deserialize(byte[] receivePacket) throws IOException, ClassNotFoundException	{
		ByteArrayInputStream byteStream = new ByteArrayInputStream(receivePacket);
		ObjectInputStream objectStream = new ObjectInputStream(byteStream);
		Object temp = objectStream.readObject();
		return temp;
	}
}
