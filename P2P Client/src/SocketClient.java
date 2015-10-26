import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient {

    private static String hostname = "134.129.91.155";
    private int port;
    Socket socketClient;
    String[] header;
    String[] names = new String[6];
    private int peerPort;
	private String peerName, uploadPath;
	Socket socket = null;
	PrintWriter out;
	BufferedReader in;
	private JFrame mainFrame;
	private JPanel panel1;
    private JButton connectButt, searchButt, changeUpload;
	private JLabel pName, title;
	private JTextField enterName;

    public SocketClient(String hostname, int port) {
		//this.hostname = hostname;
		this.port = port;
		uploadPath = "C:/Users/Squeelch/Documents/FileStorm";
		prepareGUI();
	}
    
    private void prepareGUI(){
		mainFrame = new JFrame("FILESTORMMMMM");
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		
		title = new JLabel("FILESTORM CLIENT");
		
	
		title.setLocation(240, 4);
		title.setFont(title.getFont().deriveFont(32.0f));
		title.setVisible(true);
		
		enterName = new JTextField("Search for File", 32);
		searchButt = new JButton("Search");
		connectButt = new JButton("Connect and Upload");
		pName = new JLabel("Current File Folder: " + uploadPath);
		changeUpload = new JButton("Change File Folder");
		
		connectButt.setLocation(550, 300);
		
		enterName.setEnabled(true);
		//enterName.setLocation(pName.getX() + pName.getWidth() + 10, pName.getY());
		
		panel1.add(title);
		
		panel1.add(enterName);
		panel1.add(searchButt);
		panel1.add(connectButt);
		panel1.add(pName);
		panel1.add(changeUpload);
		mainFrame.setSize(400,200);
		mainFrame.add(panel1);
		mainFrame.setVisible(true);
		
		connectButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e)
	            {
	                //Execute when button is pressed
	                System.out.println("You clicked the button");
	                try {
						connect();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
		});
		
		searchButt.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e)
	            {
	                //Execute when button is pressed
	                System.out.println("Searching for files");
	                names[0] = "SEARCH";
	                names[1] = hostname;
	                names[2] = new Integer(port).toString();
	                names[3] = null;
	                names[4] = null;
	                names[5] = null;
	                try {
	                	
						askForTime();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
		});
	}

	public void connect() throws UnknownHostException, IOException {
		System.out.println("Attempting to connect to " + hostname + ":" + port);
		socketClient = new Socket(hostname, port);
		System.out.println("Connection Established");
	}

	public void readResponse() throws IOException {
		String userInput;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

		System.out.print("RESPONSE FROM SERVER:");
		while ((userInput = stdIn.readLine()) != null) {
			System.out.println(userInput);
			System.out.println();
		}
	}

	// Successfully passes an array through the socket
	public void askForTime() throws IOException {
		//String[] names = { "CREATE", "todd", "8", "yellow"};
		//String[] names = {"SEARCH", "yellow"};
		System.out.println(names[0] + " " + names[1] + " " + names[2]);
		ObjectOutputStream out = new ObjectOutputStream(socketClient.getOutputStream());
		out.writeObject(names);
		// BufferedWriter writer = new BufferedWriter(new
		// OutputStreamWriter(socketClient.getOutputStream()));
		// writer.write("TIME?");
		// writer.newLine();
		// writer.flush();
	}

	public static void main(String arg[]) {
		// Creating a SocketClient object
		SocketClient client = new SocketClient("localhost", 9991);
		/*try {
			// trying to establish connection to the server
			client.connect();
			// asking server for time
			client.askForTime();
			// waiting to read response from server
			client.readResponse();

		} catch (UnknownHostException e) {
			System.err.println("Host unknown. Cannot establish connection");
		} catch (IOException e) {
			System.err.println("Cannot establish connection. Server may not be up." + e.getMessage());
		} */
	} 
}
