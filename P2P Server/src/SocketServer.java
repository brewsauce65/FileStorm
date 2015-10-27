import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple socket server
 *
 */
public class SocketServer {

	private ServerSocket serverSocket;
	private int port;
	public static ArrayList<ArrayList<String>> master = new ArrayList<ArrayList<String>>();

	public SocketServer(int port) {
		this.port = port;
	}

	public static void showMaster() {
		for (int k = 0; k < master.size(); k++) {
			System.out.println("Entry " + k);
			ArrayList<String> row = master.get(k);
			for (int i = 0; i < row.size(); i++) {
				System.out.println(row.get(i));
			}
			System.out.println();
		}
	}

	public static ArrayList<ArrayList<String>> addMaster(String[] input) {
		String[] newInput = Arrays.copyOfRange(input, 1, input.length);
		ArrayList<String> temp = new ArrayList<String>(Arrays.asList(newInput));
		master.add(temp);
		showMaster();
		return master;
	}

	public static void appendMaster(String[] input) {
		for (int i = 0; i < master.size(); i++) {
			if (master.get(i).contains(input[1])) {
				master.get(i).add(input[2]);
			}
		}
		showMaster();
	}

	public static String[] searchMaster(String[] input) {
		for (int i = 0; i < master.size(); i++) {
			if (master.get(i).contains(input[1])) {
				String[] id = { master.get(i).get(0), master.get(i).get(1) };
				return id;
			}
		}
		String[] notFound = {};
		return notFound;
	}

	public void start() throws IOException {
		System.out.println("Starting the socket server at port:" + port);
		serverSocket = new ServerSocket(port);

		Socket client = null;

		while (true) {
			System.out.println("Waiting for clients...");
			client = serverSocket.accept();
			System.out.println("The following client has connected:" + client.getInetAddress().getCanonicalHostName());
			// A client has connected to this server. Send welcome message
			Thread thread = new Thread(new SocketClientHandler(client));
			thread.start();
		}
	}

	/**
	 * Creates a SocketServer object and starts the server.
	 *
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Setting a default port number.
		int portNumber = 9991;

		try {
			// initializing the Socket Server
			SocketServer socketServer = new SocketServer(portNumber);
			UDPServer udpSocket = new UDPServer();
			socketServer.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
