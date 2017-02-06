package nukechat.server;

import java.net.*;

public class ClientServer {
	public String username;
	public int port, numAttempts = 0;
	public final int ID;
	public InetAddress ip;

	public ClientServer(String username, int port, final int ID, InetAddress ip) {
		this.username = username;
		this.port = port;
		this.ID = ID;
		this.ip = ip;
	}

	public int getID() {
		return ID;
	}
}