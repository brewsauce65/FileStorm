import java.io.*;
import java.net.*;
import java.util.*;
 
public class UDPClient {
    public UDPClient() throws IOException {
    	System.out.println("Type something:" );
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            // get a datagram socket
        DatagramSocket socket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
            // send request
       
        byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();
        // InetAddress address = InetAddress.getLocalHost();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9991);
        socket.send(sendPacket);
     
            // get response
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
 
        // display response
        String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
        socket.close();
    }
}

	/*public UDPClient(String message) throws Exception {
		System.out.println("Type something:" );
		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		
		
		
		
		
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9991);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		clientSocket.receive(receivePacket);
		
		
		clientSocket.close();
	}*/

