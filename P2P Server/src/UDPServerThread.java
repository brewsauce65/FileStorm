import java.io.*;
import java.net.*;
import java.util.*;

public class UDPServerThread extends Thread {

	protected DatagramSocket clientSocket = null;
	protected BufferedReader in = null;
	protected boolean moreQuotes = true;
	protected InetAddress IPAddress;
	public UDPServerThread() throws IOException {
		this("UDPServerThread");
	}

	public UDPServerThread(String name) throws IOException {
		super(name);
		clientSocket = new DatagramSocket(9991);
		System.out.println("UDP Server ---");

	}

	public void run() {

		/*
		 * while (moreQuotes) { try { byte[] buf = new byte[256];
		 * 
		 * // receive request DatagramPacket packet = new DatagramPacket(buf,
		 * buf.length); socket.receive(packet);
		 * 
		 * // figure out response String dString = null; if (in == null) dString
		 * = new Date().toString(); else dString = getNextQuote();
		 * 
		 * buf = dString.getBytes();
		 * 
		 * // send the response to the client at "address" and "port"
		 * InetAddress address = packet.getAddress(); int port =
		 * packet.getPort(); packet = new DatagramPacket(buf, buf.length,
		 * address, port); socket.send(packet); } catch (IOException e) {
		 * e.printStackTrace(); moreQuotes = false; } } socket.close();
		 */
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = null;
		try {
			sentence = inFromUser.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9991);
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			clientSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
	}

	/*
	 * protected String getNextQuote() { String returnValue = null; try { if
	 * ((returnValue = in.readLine()) == null) { in.close(); moreQuotes = false;
	 * returnValue = "No more quotes. Goodbye."; } } catch (IOException e) {
	 * returnValue = "IOException occurred in server."; } return returnValue; }
	 */
}
