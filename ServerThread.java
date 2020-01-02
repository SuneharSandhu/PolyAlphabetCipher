package clientgui;

import java.net.*;
import java.io.*;

class ServerThread extends Thread { 

    private Socket sock;
    private PrintWriter writeSock;
    private BufferedReader readSock;

    public ServerThread(Socket s) {
        try {
            sock = s;
            writeSock = new PrintWriter(sock.getOutputStream(), true);
            readSock = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            sock = null;
        }
    }

    public void run() {
        boolean quit = false;
        while(!quit) {
            try {
                String inLine = readSock.readLine();
                String outLine = inLine + "HaHa!";
                writeSock.println(outLine);
                if (inLine.equals("quit")) {
                    quit = true;
                }
            } 
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sock = null;
            }
            try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}