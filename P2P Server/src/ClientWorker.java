
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientWorker extends Thread {
	private Socket socket = null;
	private static final int BUFFER_SIZE = 32768;

	public ClientWorker(Socket socket) {
		super("ClientWorker");
		this.socket = socket;
	}

	public void run() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write("Hello. You are connected to a Simple Socket Server. What is your name?");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}