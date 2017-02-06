package nukechat;

import java.net.*;

import javax.swing.*;

public class Client extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private DatagramSocket socket;
	private Thread runThread;
	private String name, address;
	private int port, ID = 1;

	public Client(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}

	public String recieve() {

		return new String();
	}

	public void send( byte[] data ) {

	}

	public void close() {

	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public int getID() {
		return ID;
	}

	public void setID( int id ) {
		this.ID = id;
	}
}