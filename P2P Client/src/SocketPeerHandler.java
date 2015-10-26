import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.File;
import java.io.OutputStream;

public class SocketPeerHandler implements Runnable {

	private Socket client;

	public SocketPeerHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread started with name:"
					+ Thread.currentThread().getName());
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

	private void readResponse() throws IOException, InterruptedException,
			ClassNotFoundException {
		ObjectInputStream stdIn = new ObjectInputStream(client.getInputStream());
		String[] clientInput;

		clientInput = (String[]) stdIn.readObject();
		sendFile(clientInput[2]);
	}

	private void sendFile(String fileName) throws IOException {
		// Uploading file location
		File myFile = new File("c:/users/squeelch/documents/filestorm/"
				+ fileName);
		byte[] mybytearray = new byte[(int) myFile.length()];
		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(mybytearray, 0, mybytearray.length);
		OutputStream os = client.getOutputStream();
		System.out.println("Sending " + fileName + "(" + mybytearray.length
				+ " bytes)");
		os.write(mybytearray, 0, mybytearray.length);
		os.flush();
		System.out.println("Done.");
		if (bis != null)
			bis.close();
		if (os != null)
			os.close();
	}

}
