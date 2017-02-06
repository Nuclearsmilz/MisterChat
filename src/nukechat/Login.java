package nukechat;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

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
		setSize(300, 400);
		setResizable(false);
		setTitle("Login");
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

	}

	private void login( String name, String address, int port ) {
		dispose();
	}
}