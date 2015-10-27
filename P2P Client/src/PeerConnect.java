import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerConnect implements Runnable {

	private ServerSocket serverSocket;

	/*
	 * Opens a port on the client that listens for connections from other peers.
	 * Called from the SocketClient
	 * 
	 */

	@Override
	public void run() {
		// Opens new socket
		System.out.println("Starting the socket server at port:" + 9992);
		try {
			serverSocket = new ServerSocket(9992);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Socket client = null;
		// listens on socket for incoming connections and
		// starts a new thread of SocketPeerHandler for each connection
		while (true) {
			System.out.println("Waiting for clients...");
			try {
				client = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("The following client has connected:" + client.getInetAddress().getCanonicalHostName());
			Thread thread = new Thread(new SocketPeerHandler(client));
			thread.start();
		}
	}
}
