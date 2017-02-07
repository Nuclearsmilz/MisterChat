package nukechat;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtName;
	private JTextField txtIP;
	private JTextField txtPort;

	private JLabel lblIP;
	private JLabel lblPort;
	private JLabel lblIPDescription;
	private JLabel lblPortDescription;

	private JButton btnLogin;

	public static void main( String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		        | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
			System.out.println("Cannot set default look and feel.");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setResizable(false);
		setTitle("Login");
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(67, 38, 165, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(127, 21, 45, 16);
		contentPane.add(lblName);

		txtIP = new JTextField();
		txtIP.setBounds(67, 104, 165, 28);
		contentPane.add(txtIP);
		txtIP.setColumns(10);

		lblIP = new JLabel("IP:");
		lblIP.setBounds(127, 87, 34, 16);
		contentPane.add(lblIP);

		txtPort = new JTextField();
		txtPort.setBounds(67, 173, 165, 28);
		txtPort.setColumns(10);
		contentPane.add(txtPort);

		lblPort = new JLabel("Port:");
		lblPort.setBounds(127, 158, 34, 16);
		contentPane.add(lblPort);

		lblIPDescription = new JLabel("(eg. 192.168.1.1)");
		lblIPDescription.setBounds(100, 132, 97, 16);
		contentPane.add(lblIPDescription);

		lblPortDescription = new JLabel("(eg. 9999)");
		lblPortDescription.setBounds(114, 201, 68, 16);
		contentPane.add(lblPortDescription);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(91, 228, 117, 29);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent ae ) {
				String name = txtName.getText();
				String ip = txtIP.getText();
				int port = Integer.parseInt(txtPort.getText());
				login(name, ip, port);
			}
		});
		contentPane.add(btnLogin);
	}

	private void login( String name, String address, int port ) {
		dispose();
		new MainWindow(name, address, port);
	}
}