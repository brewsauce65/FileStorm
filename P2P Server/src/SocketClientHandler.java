import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class SocketClientHandler implements Runnable {

	private Socket client;

	public SocketClientHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread started with name:" + Thread.currentThread().getName());
			readResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void readResponse() throws IOException, InterruptedException {
		ObjectInputStream stdIn = new ObjectInputStream(client.getInputStream());
		String[] clientInput;
		try {
			clientInput = (String[]) stdIn.readObject();
			System.out.println(clientInput[0]);
			System.out.println(clientInput[1]);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			//possibly need to parse input from client
			/*if (userInput.equals("CREATE")) {
				System.out.println("Creating new client profile");
				createClientProfile();
				break;
			}
			if (userInput.equals("APPEND")) {
				System.out.println("Appending new files to file list");
				appendClientProfile();
				break;
			}
			if (userInput.equals("SEARCH")) {
				System.out.println("Searching file list for file");
				fileSearch();
				break;
			}
			System.out.println(userInput);*/
		}
	

	private void createClientProfile() throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write(new Date().toString());
		writer.flush();
		writer.close();
	}
	
	private void appendClientProfile() throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write(new Date().toString());
		writer.flush();
		writer.close();
	}
	
	private void fileSearch() throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write(new Date().toString());
		writer.flush();
		writer.close();
	}

}