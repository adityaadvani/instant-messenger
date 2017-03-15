import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Header for messages from client to server.
 *
 * @author Aditya Advani
 * @author Mohita Jethwani
 * @author Rohit Giyanani
 */
public class HeaderToServer implements Serializable {
	/*
	 * 	1: request To connect
	 * 	2: simpleMessage
	 * 3. broadcast message
	 * 	4. leaving
	 * 	5. Login
	 * 	6. Register
                7. requestUpdate
	 */
	public int type;
	public String senderName;
	public String receiverName;
	public String ipAddressOfSender;
	public String username;
	public String password;
	public String message;
	public int portAddressOfSender;
	
	public HeaderToServer(int type, String senderName, String receiverName, String ipAddressOfSender, int portAddressOfSender, String username, String password, String message)	{
		this.ipAddressOfSender = ipAddressOfSender;
		this.type = type;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.username = username;
		this.password = password;
		this.message = message;
		this.portAddressOfSender = portAddressOfSender;
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
