package nukechat;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class Client extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private DatagramSocket socket;
	private InetAddress ip;

	private Thread sent;
	private String name, address;
	private int port, ID = -1;

	public Client(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}

	public boolean open( String address ) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String recieve() {
		byte[] dataPacket = new byte[1024]; // equal to 1 KB
		DatagramPacket packet = new DatagramPacket(dataPacket, dataPacket.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;
	}

	public void send( byte[] data ) {
		sent = new Thread(() -> {
			DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		sent.start();
	}

	public void close() {
		new Thread(() -> {
			synchronized (socket) {
				socket.close();
			}
		}).start();
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