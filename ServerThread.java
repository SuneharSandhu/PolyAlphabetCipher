import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ServerThread extends Thread {
	
		private Socket clientSock;                      // Socket connected to the Client
	    private PrintWriter logFile, writeSock;         // Used to write data to the socket and log file
	    private BufferedReader readSock;                // Used to read data from socket
	    
	    Date date = new Date();

	    public ServerThread(Socket sock, PrintWriter logFile) {
	    	try {
		        this.clientSock = sock;
		        this.logFile = logFile;
		        writeSock = new PrintWriter(sock.getOutputStream(), true);
		        readSock = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	    	}
	    	catch(Exception e) {
	    		logFile.println("Error: " + e.getMessage());
	    	}
	        
	    }

	    public void run() {
	        boolean quit = false;
	        try {
	            logFile.write(
	                    "Connection recieved: " + date.toString()+ "\t" 
	                    + clientSock.getInetAddress() + " Port: " + clientSock.getPort() + "\n");

	            while (!quit) { 
	                String readLine = readSock.readLine();
	                if (readLine.equals("quit"))  {
	                    logFile.write("Connection closed. Port: " + clientSock.getPort() + "\n");
	                    writeSock.println("Good Bye!");
                        clientSock.close();
                        quit = true;
	                }             
	                PolyAlphabet cipher = new PolyAlphabet();
                    String cipherText = cipher.encrypt(readLine);
                    writeSock.println(cipherText);
                    
	            }
	        } catch (Exception e) {
	            logFile.println("Error: " + e.getMessage());
	            clientSock = null;
	        }
	    }
}
