package nukechat;

import java.awt.*;

import javax.swing.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField textPort;
	private JLabel lblAddress;
	private JLabel labelPort;
	private JLabel lblAddressDesc;
	private JLabel lblPortDesc;

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

	}

	private void login( String name, String address, int port ) {

	}
}