
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;

		int port = 8888; // default
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			// ignore me
		}

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Started on: " + port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + args[0]);
			System.exit(-1);
		}

		System.out.println("Waiting for clients...");
		while (listening) {
			Socket client = serverSocket.accept();
			new ClientWorker(client).run();
			;
		}

		serverSocket.close();
	}
}