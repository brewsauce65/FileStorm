import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class SocketClientHandler implements Runnable {

	private Socket client;

	// Constructor class
	// takes socket as input
	public SocketClientHandler(Socket client) {
		this.client = client;
	}

	// Run class called by main
	// Reads responses from client
	// Calls readResponse()
	@Override
	public void run() {
		try {
			System.out.println("Thread started with name:" + Thread.currentThread().getName());
			readResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Takes input from client and directs traffic
	// Sorts client input
	private void readResponse() throws IOException, InterruptedException, ClassNotFoundException {
		ObjectInputStream stdIn = new ObjectInputStream(client.getInputStream());
		String[] clientInput;

		clientInput = (String[]) stdIn.readObject();
		if (clientInput[0].equals("CREATE")) {
			System.out.println("Creating new client profile");
			createClientProfile(clientInput);
		}
		if (clientInput[0].equals("APPEND")) {
			System.out.println("Appending new files to file list");
			appendClientProfile(clientInput);
		}
		if (clientInput[0].equals("SEARCH")) {
			System.out.println("Searching file list for file");
			fileSearch(clientInput);
		}
	}

	// Create new ArrayList in master list
	// input: {host IP, port, file 1, file 2...}
	private void createClientProfile(String[] input) throws IOException, InterruptedException {
		ArrayList<ArrayList<String>> master = SocketServer.addMaster(input);
		ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
		out.writeObject(master);

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write("Your files have been added to the directory and are available for download");
		writer.flush();
		writer.close();
	}

	// updates client profile after new download
	// Appends new file to clients ArrayList
	// input: {host IP, file to be added}
	private void appendClientProfile(String[] input) throws IOException, InterruptedException {
		SocketServer.appendMaster(input);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write("Your file list has been updated");
		writer.flush();
		writer.close();
	}

	// Searches master list for specified file
	// returns {target IP, target port}
	// input = {file to be found}
	private void fileSearch(String[] input) throws IOException, InterruptedException {
		String[] id = SocketServer.searchMaster(input);
		if (id.length > 0) {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write(id[0]);
			writer.newLine();
			writer.write(id[1]);
			writer.flush();
			writer.close();
		} else {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write("Your file was not found. Please search again");
			writer.flush();
			writer.close();
		}
	}

}
