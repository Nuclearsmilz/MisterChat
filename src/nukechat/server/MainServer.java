package nukechat.server;

public class MainServer {

	private int port;
	private Server server;
	
	public MainServer(int port) {
		this.port = port;
		server = new Server(port);
	}
	
	public static void main(String[] args) {
		int port;
		if(args.length != 1) {
			System.out.println("Use: java -jar NukeServer.jar [port]");
		}
		port = Integer.parseInt(args[0]);
		new MainServer(port);
	}
}