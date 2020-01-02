package clientgui;

import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    public void run() {
        try {
            ServerSocket servSock = new ServerSocket(5520);
            while ( true ) {
            	try {
	                Socket sock = servSock.accept();    // listens for connections
	                ServerThread servThread = new ServerThread(sock);
	                servThread.start();
            	} catch (Exception e) {
            		servSock.close();
            	}
            }
        }
        catch (Exception e) {
            System.out.println("Error " + e.getMessage());            
        }
    }
}