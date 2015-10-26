import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class SocketPeerHandler implements Runnable {

	private Socket client;

	public SocketPeerHandler(Socket client) {
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readResponse() throws IOException, InterruptedException, ClassNotFoundException {
		ObjectInputStream stdIn = new ObjectInputStream(client.getInputStream());
		String[] clientInput;

		clientInput = (String[]) stdIn.readObject();
		//upload to peer
	}

	/*private void createClientProfile(String[] input) throws IOException, InterruptedException {
		SocketServer.addMaster(input);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write("Your files have been added to the directory and are available for download");
		writer.flush();
		writer.close();
	}

	private void appendClientProfile(String[] input) throws IOException, InterruptedException {
		SocketServer.appendMaster(input);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write("Your file list has been updated");
		writer.flush();
		writer.close();
	}

	private void fileSearch(String[] input) throws IOException, InterruptedException {
		String[] id = SocketServer.searchMaster(input);
		if (id.length>0){
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write(id[0]);
		writer.write(id[1]);
		writer.flush();
		writer.close();
		} else {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write("Your file was not found. Please search again");
			writer.flush();
			writer.close();
		}
	}*/

}
