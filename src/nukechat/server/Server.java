package nukechat.server;

import java.net.*;
import java.util.*;

public class Server implements Runnable {

	private List<ClientServer> clients = new ArrayList<>();
	private List<Integer> reponses = new ArrayList<>();

	private int port;
	private final int MAX_ATTEMPTS = 10;
	private DatagramSocket socket;
	private Thread run, sent, receive, manage;

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
		//TODO: manageClients
		//TODO: recieve
		Scanner in = new Scanner(System.in);
		while (running) {
			String text = in.nextLine();
			if (!text.startsWith("/")) {
				//TODO: send all
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
				} else {
					for (int i = 0; i < clients.size(); i++) {
						ClientServer cs = clients.get(i);
						if (name.equals(cs.username)) {
							//TODO: disconnect
						}
					}
				}
			}
		}

		in.close();
	}
}