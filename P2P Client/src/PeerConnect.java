import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerConnect implements Runnable {

	private ServerSocket serverSocket;

	/*
	 * public PeerConnect(Socket client) { this.client = client; }
	 */

	@Override
	public void run() {
		System.out.println("Starting the socket server at port:" + 9992);
		try {
			serverSocket = new ServerSocket(9992);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Socket client = null;

		while (true) {
			System.out.println("Waiting for clients...");
			try {
				client = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("The following client has connected:"
					+ client.getInetAddress().getCanonicalHostName());
			// A client has connected to this server. Send welcome message
			Thread thread = new Thread(new SocketPeerHandler(client));
			thread.start();
		}
	}
}
