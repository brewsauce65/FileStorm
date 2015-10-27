import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.io.File;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient {

	//
	private static String hostname = "134.129.91.155";
	private int port;
	Socket socketClient;
	String[] header;
	String[] names = new String[6];
	private String uploadPath;
	Socket socket = null;
	PrintWriter out;
	BufferedReader in;
	private JFrame mainFrame;
	private JPanel panel1;
	private JButton connectButt, searchButt, changeUpload;
	private JLabel pName, title;
	private JTextField searchText;
	private JTextArea masterOutput;

	public SocketClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		uploadPath = "C:/Users/Squeelch/Documents/FileStorm";
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
		masterOutput = new JTextArea();
		masterOutput.setColumns(20);
		masterOutput.setRows(6);
		masterOutput.setEditable(false);

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
		panel1.add(masterOutput);
		mainFrame.setSize(400, 400);
		mainFrame.add(panel1);
		mainFrame.setVisible(true);

		// Connect and Upload button action
		connectButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new PeerConnect());
				thread.start();
				//Thread threadUDP = new Thread(new PeerConnect());
				//threadUDP.start();
				
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
				} catch (IOException | ClassNotFoundException e1) {
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
				} catch (IOException | ClassNotFoundException e1) {
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

	public void connectClient(String[] input) throws UnknownHostException, IOException, ClassNotFoundException {
		String[] ip = input[0].split("\\/");
		String hostname = ip[1];
		String port = input[1];
		System.out.println("Attempting to connect to " + hostname + ":" + port);
		socketClient = new Socket(hostname, Integer.parseInt(port));
		System.out.println("Connection Established");
		ObjectOutputStream out = new ObjectOutputStream(socketClient.getOutputStream());
		out.writeObject(input);
		byte[] mybytearray = new byte[3000000];
		InputStream is = socketClient.getInputStream();
		// downloading file location
		FileOutputStream fos = new FileOutputStream("C:/Users/Squeelch/Documents/FileStorm/" + input[2]);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int bytesRead = is.read(mybytearray, 0, mybytearray.length);
		int current = bytesRead;

		do {
			bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
			if (bytesRead >= 0)
				current += bytesRead;
		} while (bytesRead > -1);

		bos.write(mybytearray, 0, current);
		bos.flush();
		System.out.println("File " + input[2] + " downloaded (" + current + " bytes read)");
		if (fos != null)
			fos.close();
		if (bos != null)
			bos.close();
		String[] results = new String[3];
		results[0] = "APPEND";
		results[1] = InetAddress.getLocalHost().toString();
		results[2] = input[2];
		upload(results);
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

	public String[] getResponse() throws IOException {
		String userInput;
		String[] id = new String[2];
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

		System.out.print("RESPONSE FROM SERVER:");
		while ((userInput = stdIn.readLine()) != null) {
			if (id[0] == null) {
				id[0] = userInput;
			} else {
				id[1] = userInput;
			}
		}
		return id;
	}

	/*
	 * public void getMaster() throws ClassNotFoundException, IOException{
	 * ObjectInputStream stdIn = new
	 * ObjectInputStream(socketClient.getInputStream());
	 * ArrayList<ArrayList<String>> master = new ArrayList<ArrayList<String>>();
	 * 
	 * master = (ArrayList<ArrayList<String>>) stdIn.readObject(); for (int k =
	 * 0; k < master.size(); k++) { System.out.println("Entry " + k);
	 * ArrayList<String> row = master.get(k); for (int i = 0; i < row.size();
	 * i++) { masterOutput.append(row.get(i)); } System.out.println(); } }
	 */
	// Successfully passes an array through the socket
	public void upload(String[] input) throws IOException, ClassNotFoundException {
		connectServer();
		ObjectOutputStream out = new ObjectOutputStream(socketClient.getOutputStream());
		out.writeObject(input);
		if (input.length == 2) {
			String[] location = getResponse();
			String[] request = new String[3];
			request[0] = location[0];
			request[1] = location[1];
			request[2] = input[1];
			connectClient(request);
		} else {
			readResponse();
		}
		// getMaster();
	}

	public static void main(String arg[]) throws IOException {
		SocketClient client = new SocketClient("localhost", 9991);
		client.prepareGUI();
		UDPClient udpClient = new UDPClient();
	}
}
