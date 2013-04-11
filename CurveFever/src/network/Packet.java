package network;

public class Packet {
	private int id;
	private int size;
	private byte[] content;
	private int iterator;
	private final int MAX_SIZE = 8192;
	public Packet( int eyeDee ) {
		id = eyeDee;
		size = 8;
		content = new byte[MAX_SIZE];
		iterator=0;
	}
	
	public void addByte( byte cont ) {
		content[iterator++] = cont;
		size += 1;
	}
	
	public void addDWord( int cont ) {
		content[iterator++] = (byte)((cont >> 24)&255);
		content[iterator++] = (byte)((cont >> 16)&255);
		content[iterator++] = (byte)((cont >> 8)&255);
		content[iterator++] = (byte)(cont & 255);
		size += 4;
	}

	public byte[] getContent() {
		return content;
	}
	public int getId() {
		return id;
	}
	public int size() {
		return size;
	}
}
