package clientgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.net.*;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private Socket sock = null;         // Socket used to connect to server
    private PrintWriter writeSock;      // Used to write data to socket
    private BufferedReader readSock;    // Used to read data from socket 
	
    private JFrame frame;
    private JTextField host_addr;        
    private JTextField portNum;
    private JButton cd;
    private JLabel messagelbl;
    private JTextField messagetxt;
    private JTextArea textArea;

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				GUI window = new GUI();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        });
   }

	public GUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("DesktopIcon.borderRimColor"));
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel hostlbl = new JLabel("IP Address");
		hostlbl.setBounds(6, 11, 80, 16);
		frame.getContentPane().add(hostlbl);
		
		host_addr = new JTextField("constance.cs.rutgers.edu");
		host_addr.setBounds(87, 6, 170, 26);
		frame.getContentPane().add(host_addr);
		host_addr.setColumns(10);
		
		JLabel portlbl = new JLabel("Port Number");
		portlbl.setBounds(6, 53, 80, 16);
		frame.getContentPane().add(portlbl);
		
		portNum = new JTextField("5520");
		portNum.setBounds(96, 48, 130, 26);
		frame.getContentPane().add(portNum);
		portNum.setColumns(10);
		
		cd = new JButton("Connect");
		cd.setBounds(238, 49, 96, 26);
		frame.getContentPane().add(cd);
		
		messagelbl = new JLabel("Message to Server");
		messagelbl.setBounds(6, 112, 120, 16);
		frame.getContentPane().add(messagelbl);
		
		messagetxt = new JTextField();
		messagetxt.setBounds(6, 135, 438, 26);
		frame.getContentPane().add(messagetxt);
		messagetxt.setColumns(10);
		
		JButton sendbtn = new JButton("Send");
		sendbtn.setBounds(6, 168, 80, 29);
		frame.getContentPane().add(sendbtn);
		
		JLabel comm = new JLabel("Client/Server Communication");
		comm.setBounds(6, 212, 200, 16);
		frame.getContentPane().add(comm);
		
		textArea = new JTextArea();
		textArea.setBounds(6, 235, 438, 330);
                frame.getContentPane().add(textArea);
        
        JScrollBar scrollBar = new JScrollBar();
	scrollBar.setBounds(429, 235, 15, 207);
	frame.getContentPane().add(scrollBar);
		
	cd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		if (cd.getText().equals("Connect")) {
                    openSocket();
                    if (sock.isConnected()) {
                        textArea.append("Connected to Server!\n");
                    }
			cd.setText("Disconnect");
		}
		else {
                    closeSocket();
		}
	}
    });

       sendbtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			send();
		}
        });
    }
    
    // Opens the socket to connect to the server
    private void openSocket() {
        try { 
            String hostAddress = host_addr.getText();
            int portNumber = Integer.parseInt(portNum.getText());
            sock = new Socket(hostAddress, portNumber);
            writeSock = new PrintWriter(sock.getOutputStream(), true);
            readSock = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (Exception e) {
            textArea.append("Error: " + e.getMessage() + "\n");
            sock = null;    // set to null so we can check if it is open
        }
    }

    // Closes the socket and disconnects from the server
    private void closeSocket() {
        try {
            readSock.close();
            writeSock.close();
            sock.close();
            textArea.append("Disconnected!\n");
            cd.setText("Connect");
        }
        catch (Exception e){
            textArea.append("Error: " + e.getMessage() + "\n");
            sock = null;
        }
    }

    // Reads the message input from the user and sends 
    // it to the server to communicate
    private void send() {
        try {
            if (sock != null) {
                String message = messagetxt.getText();
                writeSock.println(message);
                textArea.append("Client: " + message + "\n");

                // clears the message text field
                messagetxt.setText("");
                
                String dataRead = readSock.readLine();
                textArea.append("Server: " + dataRead + "\n");

                if (dataRead.equals("Good Bye!")) {
                    closeSocket();
                    cd.setText("Connect");
                }
            }
        } catch (Exception e) {
            sock = null;
            textArea.append("Error: " + e.getMessage() + "\n");
            cd.setText("Connect");
        }
    }

}
