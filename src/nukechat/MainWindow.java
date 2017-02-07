package nukechat;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class MainWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtMessage;
	private JTextArea history;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem onlineUsersMenuItem;
	private JMenuItem exitMenuItem;

	private JButton btnSend;

	private DefaultCaret caret;

	private boolean running = false;
	private Thread run, listen;

	private Users onlineUsers;
	private Client client;

	public static void main( String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void create() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			//e.printStackTrace();
			System.out.println("Unsupported Look and Feel.");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("Menu");
		menuBar.add(menu);

		onlineUsersMenuItem = new JMenuItem("Online Users");
		onlineUsersMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				onlineUsers.setVisible(true);
			}
		});
		menu.add(onlineUsersMenuItem);

		exitMenuItem = new JMenuItem("Exit");
		menu.add(exitMenuItem);

		history = new JTextArea();
		history.setEditable(false);
		JScrollPane scrollpane = new JScrollPane(history);
		caret = (DefaultCaret) history.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.weightx = 1;
		gbc_scrollPane.weighty = 1;
		gbc_scrollPane.insets = new Insets(0, 5, 0, 0);
		contentPane.add(scrollpane, gbc_scrollPane);

		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed( KeyEvent key ) {
				if (key.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtMessage.getText(), true);
				}
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		gbc_txtMessage.weightx = 1;
		gbc_txtMessage.weighty = 0;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				send(txtMessage.getText(), true);
			}
		});

		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		gbc_btnSend.weightx = 0;
		gbc_btnSend.weighty = 0;
		contentPane.add(btnSend, gbc_btnSend);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 28, 815, 30, 7 }; // SUM = 880
		gbl_contentPane.rowHeights = new int[] { 25, 485, 40 }; // SUM = 550
		contentPane.setLayout(gbl_contentPane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing( WindowEvent we ) {
				String disconnect = "/d/" + client.getID() + "/e/";
				send(disconnect, false);
				running = false;
				client.close();
			}
		});

		setVisible(true);

		txtMessage.requestFocusInWindow();
	}

	public MainWindow(String name, String address, int port) {
		setTitle("Nuke Chat Client");
		client = new Client(name, address, port);
		boolean connect = client.open(address);
		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection failed!");
		}
		create();
		console("Attempting a connection to " + address + ":" + port + ", username:" + name);
		String connection = "/c/" + name + "/e/";
		client.send(connection.getBytes());
		onlineUsers = new Users();
		running = true;
		run = new Thread(this, "Running");
		run.start();
	}

	public void run() {
		listen();
	}

	public void listen() {
		listen = new Thread(() -> {
			while (running) {
				String message = client.recieve();
				if (message.startsWith("/c/")) {
					client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
					console("Successfully connected to server! ID: " + client.getID());
				} else if (message.startsWith("/m/")) {
					String text = message.substring(3);
					text = text.split("/e/")[0];
					console(text);
				} else if (message.startsWith("/i/")) {
					String text = "/i/" + client.getID() + "/e/";
					send(text, false);
				} else if (message.startsWith("/u/")) {
					String[] u = message.split("/u/|/n/|/e/");
					onlineUsers.update(Arrays.copyOfRange(u, 1, u.length - 1));
				}
			}
		});
	}

	public void console( String message ) {
		history.append(message + "\n\r");
		history.setCaretPosition(history.getDocument().getLength());
	}

	private void send( String message, boolean text ) {
		if (message.equals("")) return;
		if (text) {
			message = client.getName() + ": " + message;
			message = "/m/" + message + "/e/";
			txtMessage.setText("");
		}
		client.send(message.getBytes());
	}
}