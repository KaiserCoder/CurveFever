package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class NetworkClient {
	public static boolean isServerReachable( String ip ) throws UnknownHostException, IOException {
		return InetAddress.getByName(ip).isReachable(5000);
	}
	
	public static Socket connect( String ip, int port ) throws UnknownHostException, IOException {
		SocketAddress sockaddr = new InetSocketAddress( ip, port );
		Socket sock = new Socket();
		sock.connect(sockaddr, 5000);
		return sock;
	}
	
	public static void disconnect( Socket sock ) throws IOException {
		if(sock==null)
			return;
		
		sock.close();
		sock = null;
	}
	
	public static void sendInformation( Socket sock, Packet pak ) {
		try {
			sock.getOutputStream().write(pak.getContent(),0,pak.size());
		} catch( IOException io ) {
			
		}
	}
}
