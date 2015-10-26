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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.File;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient {

	private static String hostname = "134.129.91.155";
	private ServerSocket serverSocket;
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
	private JTextField searchText;

	public SocketClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		uploadPath = "C:/Users/adam.m.brewer/Desktop/uploadFolder";
		// prepareGUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("FILESTORMMMMM");
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());

		title = new JLabel("FILESTORM CLIENT");

		title.setLocation(240, 4);
		title.setFont(title.getFont().deriveFont(32.0f));
		title.setVisible(true);

		searchText = new JTextField("Search for File", 32);
		searchButt = new JButton("Search");
		connectButt = new JButton("Connect and Upload");
		pName = new JLabel("Current File Folder: " + uploadPath);
		changeUpload = new JButton("Change File Folder");

		connectButt.setLocation(550, 300);

		searchText.setEnabled(true);
		// enterName.setLocation(pName.getX() + pName.getWidth() + 10,
		// pName.getY());

		panel1.add(title);

		panel1.add(searchText);
		panel1.add(searchButt);
		panel1.add(connectButt);
		panel1.add(pName);
		panel1.add(changeUpload);
		mainFrame.setSize(400, 200);
		mainFrame.add(panel1);
		mainFrame.setVisible(true);

		// Connect and Upload button action
		connectButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new PeerConnect());
				thread.start();
				// Change folder path on different computers
				File folder = new File(uploadPath);
				File[] listOfFiles = folder.listFiles();

				String[] input = new String[listOfFiles.length + 3];
				input[0] = "CREATE";
				InetAddress ip;
				
				try {
					ip = InetAddress.getLocalHost();
					input[1] = ip.toString();
				} catch (UnknownHostException e2) {
					e2.printStackTrace();
				}
				
				input[2] = "9992";
				for (int i = 0; i < listOfFiles.length; i++) {
					input[i + 3] = listOfFiles[i].getName();
				}

				try {
					upload(input);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		// Search button action
		searchButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				System.out.println("Searching for files");
				String userInput = searchText.getText();
				String[] input = { "SEARCH", userInput };

				try {
					upload(input);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void connectServer() throws UnknownHostException, IOException {
		System.out.println("Attempting to connect to " + hostname + ":" + port);
		socketClient = new Socket(hostname, port);
		System.out.println("Connection Established");
	}
	
	public void connectClient(String[] input) throws UnknownHostException, IOException{
		String[] ip = input[0].split("\\/");
		String hostname = ip[1];
		String port = input[1];
		System.out.println("Attempting to connect to " + hostname + ":" + port);
		socketClient = new Socket(hostname, Integer.parseInt(port));
		System.out.println("Connection Established");
		ObjectOutputStream out = new ObjectOutputStream(
				socketClient.getOutputStream());
		out.writeObject(input);
	}

	public void readResponse() throws IOException {
		String userInput;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				socketClient.getInputStream()));

		System.out.print("RESPONSE FROM SERVER:");
		while ((userInput = stdIn.readLine()) != null) {
			System.out.println(userInput);
			System.out.println();
		}
	}
	
	public String[] getResponse() throws IOException {
		String userInput;
		String[] id = new String[2];
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				socketClient.getInputStream()));

		System.out.print("RESPONSE FROM SERVER:");
		while ((userInput = stdIn.readLine()) != null) {
			if (id[0] == null){
				id[0] = userInput;
			} else{
				id[1] = userInput;
			}
		}
		return id;
	}
	

	// Successfully passes an array through the socket
	public void upload(String[] input) throws IOException {
		connectServer();
		ObjectOutputStream out = new ObjectOutputStream(
				socketClient.getOutputStream());
		out.writeObject(input);
		if( input.length == 2){
			String[] location = getResponse();
			String[] request = new String[3];
			request[0] = location[0];
			request[1] = location[1];
			request[2] = input[1];
			connectClient(request);
		} else{
		readResponse();
	}
	}

	public static void main(String arg[]) {
		SocketClient client = new SocketClient("10.134.218.196", 9991);
		client.prepareGUI();
	}
}
