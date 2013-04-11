package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {
	private ServerSocket socket;
	private byte[] buffer;
	private final int BUFFER_SIZE = 8192; //8kb
	
	public void createSocket( int port ) throws IOException {
		socket = new ServerSocket(port);
	}
	
	public Socket waitForClient() throws IOException {
		try {
			Socket client = socket.accept();
			if(client!=null) {
				buffer = new byte[BUFFER_SIZE];
				return client;
			}
		} catch( InterruptedIOException io ) {
			//....
		}
		return null;
	}
	
	public void handleClient( final Socket client ) throws IOException {
		new Thread( new Runnable() {
			@Override 
			public void run() {
				try {
					while(!client.isClosed()) {
						InputStream is = client.getInputStream();
						is.read(buffer, 0, BUFFER_SIZE);
					}
				} catch(IOException io) {
					
				}
			}
		}).start();
	}
}
