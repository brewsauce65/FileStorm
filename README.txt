TO RUN FILESTORM

Should be able to run using a Java runtime in command line.  If this doesn't work, run the files in an IDE like Eclipse or Netbeans.
IMPORTANT - - - - - - -
You will need to change directories and IP addresses manually in the code in order for the application to work.  Specfically on line 251 of the SocketClient, which is
set to localhost by default.  Use ipconfig to find the IPv4 address that is running ON THE CENTRAL SERVER and put it there instead if you are not runnig a client through
localhost.  You will also need to change the file path in SocketClient at line 169, and possibly also at line 53.  You will also need to change the file path at line 42 in
SocketPeerHandler.
- - - - - - - - - - - -
Start by running ServerSocket.java.  This file starts up the central server.  The rest of the files need this to be running or they will have nothing to connect to.
After ServerSocket is running, run SocketClient.java on any machine you would like to be a peer.  This will bring up the GUI, and pressing Connect and Upload will 
connect you to the server.  If you don't have any files in the file folder that you specified, it will not upload any directory keys onto the server and other peers will not
be able to download files form you.  
To download a file, search for the file you want to download in the searchbar.  You need to type in the entire filename (i.e. README.txt will download README.txt, but README
by itself will not find the file README.txt).  If everything was setup properly this will download the file from a peer.
