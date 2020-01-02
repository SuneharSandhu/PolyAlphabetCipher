import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    // local variables
    private final int PORT_NUM = 5520;

    private Socket sock;
    private ServerSocket servSock;
    private ServerThread servThread;

    private FileOutputStream log;
    private PrintWriter writer;


    public static void main(String[] args) {

        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void run() {
        try {
        	servSock = new ServerSocket(PORT_NUM);
            log = new FileOutputStream("prog1b.log"); 
            writer = new PrintWriter(log, true);
            while (true) {
                sock = servSock.accept();    // listens for connections
                servThread = new ServerThread(sock, writer);
                servThread.start();
            }
        } catch (Exception e) {
            //System.out.println("Error: " + e.getMessage());
            writer.println("Error: " + e.getMessage());
            sock = null;
        }
    }
}