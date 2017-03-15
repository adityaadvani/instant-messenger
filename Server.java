
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Main class that handles the server side functions.
 *
 * @author Aditya Advani
 * @author Mohita Jethwani
 * @author Rohit Giyanani
 */

public class Server extends Thread implements Serializable {

    //static variables
    public static int serverPort = 0;
    public static HashMap<String, String> registeredUsers = new HashMap<String, String>();
    public static HashSet<String> onlineUsers = new HashSet<String>();
    public static HashMap<String, String> ipAddressMapping = new HashMap<String, String>();
    public static HashMap<String, Integer> portMapping = new HashMap<String, Integer>();
    public static HashSet<String> fileNameTracker = new HashSet<String>();
    public static HashMap<String, String> nameToFileTracker = new HashMap<String, String>();

    //instance variables
    public int switcher;
    public int senderOrReceiver = 0;
    public DatagramSocket serverSocket;
    public byte[] serverReceivingDataArray = new byte[1024];
    public byte[] serverSendingDataArray = new byte[1024];
    public int sendingPortOfOriginatingClient = 0;
    public InetAddress IPAddressOfOriginatingClient = null;
    public HeaderToServer header;

    //constructor
    public Server(int switcher, int serverEnteredListeningPort) {
        this.switcher = switcher;
        this.serverPort = serverEnteredListeningPort;
    }

    //constructor
    public Server(int typeOfTask, HeaderToServer header) {
        this.switcher = typeOfTask;
        this.header = header;
    }
    
    //handles the regstration presistence
    public static void setData()	{
    	FileInputStream fis ;
		ObjectInputStream ois;
		try {
			File file;
			try	{
				file = new File("registeredUsers.txt");
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				registeredUsers = (HashMap) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Here in try");
			}
			catch(Exception e)	{
				System.out.println("Server started");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }

    // handles the situation of invalid sign in/ sign up request
    public void negCredentialResponse(int typeSend, String message) {

        try {
            serverSocket = new DatagramSocket();

            IPAddressOfOriginatingClient = InetAddress.getByName(header.ipAddressOfSender);
            HeaderToClient headerOutgoing = new HeaderToClient(typeSend, "", null, message, null);
            byte[] sendData = HeaderToClient.getByteArray(headerOutgoing);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // handles valid sign up/ sign in query
    public void posCredentialResponse(int typeSend, String message, String username) {

        HashMap<String, String> fileHistory = new HashMap<String, String>();
        Iterator it = onlineUsers.iterator();
        while (it.hasNext()) {

            String name = it.next().toString();
            String fileName;
            if (username.compareTo(name) < 0) {
                fileName = username + "_" + name;
            } else {
                fileName = name + "_" + username;
            }
            if (fileNameTracker.contains(username + "_" + name) || fileNameTracker.contains(name + "_" + username)) {
                BufferedReader br = null;
                String data = "";
                try {
                    br = new BufferedReader(new FileReader(fileName + ".txt"));
                    while ((data = br.readLine()) != null) {
                        System.out.print("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fileHistory.put(name, data);
            }
        }
        

        try {
            System.out.println("In pos");
            serverSocket = new DatagramSocket();
            IPAddressOfOriginatingClient = InetAddress.getByName(header.ipAddressOfSender);
            HeaderToClient headerOutgoing = new HeaderToClient(typeSend, "", onlineUsers, message, fileHistory);
            System.out.println(typeSend +" "+ header.ipAddressOfSender + " "+ header.portAddressOfSender );
            byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //registers the user
    public void register(String username, String password, String ipAddress, int port) {
        System.out.println(username + " " + password);
        if (registeredUsers.isEmpty()) {
            System.out.println("in is empty");
            posCredentialResponse(6, "Register", username);
            System.out.println("exited pos");
            registeredUsers.put(username, password);
            onlineUsers.add(username);
            ipAddressMapping.put(username, ipAddress);
            portMapping.put(username,port);
            FileOutputStream fos;
            ObjectOutputStream oos;
			try {
				// persistently creates the user's entry in the system
				fos = new FileOutputStream("registeredUsers.txt");
				oos = new ObjectOutputStream(fos);
				oos.writeObject(registeredUsers);
				oos.close();
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            
        } else {

            if (registeredUsers.containsKey(username)) {
                    System.out.println("found duplicate username");
                System.out.println("Username already used");
                negCredentialResponse(7, "Register");
            } else {
                System.out.println("in no duplicates");
                posCredentialResponse(6, "Register", username);
                registeredUsers.put(username, password);
                onlineUsers.add(username);
                ipAddressMapping.put(username, ipAddress);
                portMapping.put(username,port);
            }
        }
    }

    //handles sign in request
    public void signIn(String username, String password, String ipAddress, int port) {
        System.out.println(username + " " + password);
        if (!registeredUsers.containsKey(username)) {
            System.out.println("Username does not exist");
            negCredentialResponse(7, "Login");
        } else {
            if (registeredUsers.get(username).equals(password)) {
                onlineUsers.add(username);
                System.out.println("Successful Login");
                ipAddressMapping.put(username, ipAddress);
                portMapping.put(username,port);
                posCredentialResponse(6, "Login", username);

            } else {
                System.out.println("Unsuccessful Login");
                negCredentialResponse(7, "Login");
            }

        }
    }

    // handles initial connection
    public void requestToConnect() {

        try {

            IPAddressOfOriginatingClient = InetAddress.getByName(header.ipAddressOfSender);
            HeaderToClient headerOutgoing = new HeaderToClient(5, "", null, "", null);
            byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            System.out.println("Request to Connect by " + header.ipAddressOfSender + " " + header.portAddressOfSender);
            sendingSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update list of online users
    public void updateListOfOthers() {
        Iterator<String> it = onlineUsers.iterator();
        while (it.hasNext()) {
            String nameOfClients = it.next();
            String ipAddress = ipAddressMapping.get(nameOfClients);

            try {
                serverSocket = new DatagramSocket();
                IPAddressOfOriginatingClient = InetAddress.getByName(ipAddress);
                HeaderToClient headerOutgoing = new HeaderToClient(3, "", onlineUsers, "", null);
                byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
                DatagramSocket sendingSocket = new DatagramSocket();
                sendingSocket.send(sendPacket);
                sendingSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // handles exiting users
    public void leaving() {
        System.out.println("Removing "+header.username);
        onlineUsers.remove(header.senderName);
        Iterator<String> it = onlineUsers.iterator();   
        while(it.hasNext()) {
            System.out.println(it.next());
        }

    }

    //message passing
    public void simpleMessage() {
    	String sourceName = header.senderName;
        String destinationName = header.receiverName;
        System.out.println("The message from source "+sourceName + " destination "+destinationName +" is " + header.message);
        String fileName;
        if (sourceName.compareTo(destinationName) < 0) {
            fileName = sourceName + "_" + destinationName;
        } else {
            fileName = destinationName + "_" + sourceName;
        }
        if (fileNameTracker.contains(fileName)) {
        	
        	
        	BufferedWriter bw = null;
            try {
            	String temp = fileName + ".txt";
            	File file = new File(temp);
            	FileWriter fileWriter = new FileWriter(file.getName(),true);
				bw = new BufferedWriter(fileWriter);
				bw.write(header.message);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
        	BufferedWriter bw = null;
            try {
            	String temp = fileName + ".txt";
            	File file = new File(temp);
            	FileWriter fileWriter = new FileWriter(file.getName(),true);
				bw = new BufferedWriter(fileWriter);
				bw.write(header.message);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            fileNameTracker.add(fileName);
            nameToFileTracker.put(sourceName, fileName);
            nameToFileTracker.put(destinationName, fileName);
        }

        HeaderToClient headerOutgoing = new HeaderToClient(1, sourceName, null, header.message, null);

        try {
            serverSocket = new DatagramSocket();
            
            if(ipAddressMapping.containsKey(destinationName))    {
                System.out.println("In if");
                String ipAddressOfDestination = ipAddressMapping.get(destinationName);
                int portAddressOfDestination = portMapping.get(destinationName);
                InetAddress IPAddressOfDestinationClient = InetAddress.getByName(ipAddressOfDestination);
                byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfDestinationClient, portAddressOfDestination);
                DatagramSocket sendingSocket = new DatagramSocket();
                sendingSocket.send(sendPacket);
                sendingSocket.close();
            }
            else    {
                System.out.println("In else");
                IPAddressOfOriginatingClient = InetAddress.getByName(header.ipAddressOfSender);
                byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
                DatagramSocket sendingSocket = new DatagramSocket();
                sendingSocket.send(sendPacket);
                sendingSocket.close();
            }

            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    //broadcasted message handling
    public void broadcastMessage()	{
    	Iterator<String> it = onlineUsers.iterator();
    	
    	while(it.hasNext())	{
    		String temp  = it.next();
    		System.out.println("The value in onlineUsers" + temp);
    		if(temp != header.senderName)	{
    			String sourceName = header.senderName;
    	        String destinationName = temp;
    	        System.out.println("The message from source "+sourceName + " destination "+destinationName +" is " + header.message);
    	        String fileName;
    	        if (sourceName.compareTo(destinationName) < 0) {
    	            fileName = sourceName + "_" + destinationName;
    	        } else {
    	            fileName = destinationName + "_" + sourceName;
    	        }
    	        String temp2 = "";
    	        if (fileNameTracker.contains(fileName)) {
    	        	
    	        	
    	        	BufferedWriter bw = null;
    	            try {
    	            	temp2 = fileName + ".txt";
    	            	File file = new File(temp2);
    	            	FileWriter fileWriter = new FileWriter(file.getName(),true);
    					bw = new BufferedWriter(fileWriter);
    					bw.write(header.message);
    					bw.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    	            
    	        } else {
    	        	BufferedWriter bw = null;
    	            try {
    	            	temp2 = fileName + ".txt";
    	            	File file = new File(temp2);
    	            	FileWriter fileWriter = new FileWriter(file.getName(),true);
    					bw = new BufferedWriter(fileWriter);
    					bw.write(header.message);
    					bw.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    	            fileNameTracker.add(fileName);
    	            nameToFileTracker.put(sourceName, fileName);
    	            nameToFileTracker.put(destinationName, fileName);
    	        }

    	        HeaderToClient headerOutgoing = new HeaderToClient(1, sourceName, null, header.message, null);

    	        try {
    	            serverSocket = new DatagramSocket();
    	            
    	            if(ipAddressMapping.containsKey(destinationName))    {
    	                System.out.println("In if");
    	                String ipAddressOfDestination = ipAddressMapping.get(destinationName);
    	                int portAddressOfDestination = portMapping.get(destinationName);
    	                InetAddress IPAddressOfDestinationClient = InetAddress.getByName(ipAddressOfDestination);
    	                byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
    	                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfDestinationClient, portAddressOfDestination);
    	                DatagramSocket sendingSocket = new DatagramSocket();
    	                sendingSocket.send(sendPacket);
    	                sendingSocket.close();
    	            }
    	            else    {
    	                System.out.println("In else");
    	                IPAddressOfOriginatingClient = InetAddress.getByName(header.ipAddressOfSender);
    	                byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
    	                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
    	                DatagramSocket sendingSocket = new DatagramSocket();
    	                sendingSocket.send(sendPacket);
    	                sendingSocket.close();
    	            }

    	            

    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    		}
    	}
 
    	
    	
    }


    // update list of the requesting client
    public void updateListOfRequester() {
        try {
                serverSocket = new DatagramSocket();
                IPAddressOfOriginatingClient = InetAddress.getByName(header.ipAddressOfSender);
                HeaderToClient headerOutgoing = new HeaderToClient(3, "", onlineUsers, "", null);
                byte[] sendData = HeaderToServer.getByteArray(headerOutgoing);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfOriginatingClient, header.portAddressOfSender);
                DatagramSocket sendingSocket = new DatagramSocket();
                sendingSocket.send(sendPacket);
                sendingSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
   
    // main thread logic for server
    public void run() {
        if (switcher == 0) {
            try {
                serverSocket = new DatagramSocket(serverPort);

                while (true) {
                    System.out.print("");
                    DatagramPacket receiveSegment = new DatagramPacket(serverReceivingDataArray, serverReceivingDataArray.length);
                    serverSocket.receive(receiveSegment);
                    HeaderToServer header = (HeaderToServer) HeaderToClient.deserialize(serverReceivingDataArray);
                    System.out.println(header.ipAddressOfSender+" "+header.portAddressOfSender);
                    
                    int typeOfTask = header.type;
                    System.out.println("Received message of type " + header.type);
                    new Server(typeOfTask, header).start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (switcher == 1) {
            requestToConnect();
        } else if (switcher == 2) {
            simpleMessage();
        } else if (switcher == 3) {
        	broadcastMessage();
        } else if (switcher == 4) {
            leaving();
        } else if (switcher == 5) {
            signIn(header.username, header.password,header.ipAddressOfSender,header.portAddressOfSender);
            updateListOfOthers();

        } else if (switcher == 6) {
            register(header.username, header.password, header.ipAddressOfSender,header.portAddressOfSender);
            updateListOfOthers();
        }
        else if(switcher == 7)  {
            updateListOfRequester();
        }

    }
}

