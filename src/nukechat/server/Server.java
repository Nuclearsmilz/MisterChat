package nukechat.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {

	private List<ClientServer> clients = new ArrayList<>();
	private List<Integer> responses = new ArrayList<>();

	private int port;
	private final int MAX_ATTEMPTS = 10;
	private DatagramSocket socket;
	private Thread run, send, receive, load;

	private boolean running = false;
	private boolean rawInput = false;

	public Server(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
	}

	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		loadClients();
		receive();
		Scanner in = new Scanner(System.in);
		while (running) {
			String text = in.nextLine();
			if (!text.startsWith("/")) {
				sendToAll("/m/Server: " + text + "/e/");
				continue;
			}
			text = text.substring(1);
			if (text.equals("rawInput")) {
				if (rawInput) System.out.println("Raw Input mode off.");
				else System.out.println("Raw Input mode on.");
				rawInput = !rawInput;
			} else if (text.equals("clients")) {
				System.out.println("Clients: ");
				System.out.println("------------");
				for (int i = 0; i < clients.size(); i++) {
					ClientServer cs = clients.get(i);
					System.out.println(cs.username + "(" + cs.getID() + "): " + cs.ip.toString() + ":" + cs.port);
				}
				System.out.println("------------");
			} else if (text.startsWith("kick")) {
				String name = text.split(" ")[1];
				int id = -1;
				boolean num = false;
				try {
					id = Integer.parseInt(name);
				} catch (NumberFormatException e) {
					num = false;
				}
				if (num) {
					boolean exists = false;
					for (int i = 0; i < clients.size(); i++) {
						if (clients.get(i).getID() == id) {
							exists = true;
							break;
						}
					}
					if (exists) disconnect(id, true);
					else System.out.println("Client ID: " + id + " doesn't exist. Try again?");
				} else {
					for (int i = 0; i < clients.size(); i++) {
						ClientServer cs = clients.get(i);
						if (name.equals(cs.username)) {
							disconnect(cs.getID(), true);
							break;
						}
					}
				}
			} else if (text.equals("help")) {
				printHelp();
			} else if (text.equals("quit")) {
				quit();
			} else {
				System.out.println("Command not valid. Would you like to see the comands? y/n");
				printHelp();
//				String input = in.nextLine();
//				if (input.contains("y") || input.contains("Y")) {
//					printHelp();
//				} else if (input.contains("n") || input.contains("N")) {
//					break;
//				} else {
//					break;
//				}
//				break;
			}
		}
		in.close();
	}

	private void printHelp() {
		System.out.println("All available commands: ");
		System.out.println("------------------------");
		System.out.println("/clients - Lists all connected clients.");
		System.out.println("/rawInput - Enables Raw Input mode.");
		System.out.println("/kick [ID or Username] - Kicks the specified user.");
		System.out.println("/help - Shows these commands and the dialog.");
		System.out.println("/quit - Closes down the server.");
	}

	public void receive() {
		receive = new Thread(() -> {
			while (running) {
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				try {
					socket.receive(packet);
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				process(packet);
			}
		});
		receive.start();
	}

	private void loadClients() {
		load = new Thread(() -> {
			while (running) {
				sendToAll("/i/server");
				sendStatus();
				try {
					Thread.sleep(2000); //2 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < clients.size(); i++) {
					ClientServer cs = clients.get(i);
					if (!responses.contains(cs.getID())) {
						if (cs.numAttempts >= MAX_ATTEMPTS) {
							disconnect(cs.getID(), false);
						} else {
							cs.numAttempts++;
						}
					} else {
						responses.remove(new Integer(cs.getID()));
						cs.numAttempts = 0;
					}
				}
			}
		}, "Load");
		load.start();
	}

	private void process( DatagramPacket packet ) {
		String string = new String(packet.getData());
		if (rawInput) System.out.println(string);
		if (string.startsWith("/c/")) {
			int id = UniqueID.getID();
			String name = string.split("/c/|/e/")[1];
			System.out.println(name + " (" + id + ") connected to the server!");
			clients.add(new ClientServer(name, id, packet.getPort(), packet.getAddress()));
			String ID = "/c/" + id;
			send(ID, packet.getAddress(), packet.getPort());
		} else if (string.startsWith("/m/")) {
			sendToAll(string);
		} else if (string.startsWith("/d/")) {
			String id = string.split("/d/|/e/")[1];
			disconnect(Integer.parseInt(id), true);
		} else if (string.startsWith("/i/")) {
			responses.add(Integer.parseInt(string.split("/i/|/e/")[1]));
		} else {
			System.out.println(string);
		}
	}

	private void sendStatus() {
		if (clients.size() <= 0) return;
		String users = "/u/";
		for (int i = 0; i < clients.size() - 1; i++) {
			users += clients.get(i).username + "/n/";
		}
		users += clients.get(clients.size() - 1).username + "/e/";
		sendToAll(users);
	}

	private void sendToAll( String message ) {
		if (message.startsWith("/m")) {
			String text = message.substring(3);
			text = text.split("/e/")[0];
			System.out.println(message);
		}
		for (int i = 0; i < clients.size(); i++) {
			ClientServer cs = clients.get(i);
			send(message.getBytes(), cs.ip, cs.port);
		}
	}

	private void send( final byte[] data, final InetAddress ip, final int port ) {
		send = new Thread(() -> {
			DatagramPacket packer = new DatagramPacket(data, data.length, ip, port);
			try {
				socket.send(packer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, "Send");
		send.start();
	}

	private void send( String message, InetAddress ip, int port ) {
		message += "/e/";
		send(message.getBytes(), ip, port);
	}

	private void quit() {
		for (int i = 0; i < clients.size(); i++) {
			disconnect(clients.get(i).getID(), true);
		}
		running = false;
		socket.close();
	}

	private void disconnect( int id, boolean status ) {
		ClientServer cs = null;
		boolean userExisted = false;
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getID() == id) {
				cs = clients.get(i);
				clients.remove(i);
				userExisted = true;
			}
		}
		if (!userExisted) return;
		String message = "";
		if (status) {
			message = "Client " + cs.username + " (" + cs.getID() + ") @ " + cs.ip.toString() + ":" + cs.port
			        + " disconnected.";
		} else {
			message = "Client " + cs.username + " (" + cs.getID() + ") @ " + cs.ip.toString() + ":" + cs.port
			        + " timed out.";
		}
		System.out.println(message);
	}
}