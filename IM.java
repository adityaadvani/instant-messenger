import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Launcher class for Server. Compiling this compiles the client as well as the server.
 *
 * @author Aditya Advani
 * @author Mohita Jethwani
 * @author Rohit Giyanani
 */
// running server: java IM -s <server port number>
// running client: java Client <server address> <server port number> <client port number>
public class IM {

	static int clientListeningPort=5555;
	public static void main(String[] args) throws IOException {

		//Client variables
		String clientEnteredServerAddress="";
		int clientEnteredServerPort=0;
		
		
		
		//Server variables
		int serverEnteredListeningPort=0;
		
		
		if (args[0].equals("-c")) {
			clientEnteredServerAddress=args[1];
			clientEnteredServerPort=Integer.parseInt(args[2]);
                        
			
			new Thread(new Client(clientEnteredServerAddress, clientEnteredServerPort, 0, clientListeningPort+5)).start();
			new Thread(new Client(clientEnteredServerAddress, clientEnteredServerPort, 8, clientListeningPort+5)).start();


		} else if (args[0].equals("-s")) {
			serverEnteredListeningPort = Integer.parseInt(args[1]);
			Server s = new Server(0,serverEnteredListeningPort);
			s.setData();
			s.start();
			

		} else {
			usage("Invalid Input");
		}
	}
	
	public static void usage(String message){
        System.out.println(message);

    }
}

