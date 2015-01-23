package com.nuclearsmilz.misterchat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public Login() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(64, 51, 165, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(131, 26, 46, 14);
		contentPane.add(lblName);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(64, 118, 165, 20);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		lblAddress = new JLabel("IP Address:");
		lblAddress.setBounds(116, 94, 70, 14);
		contentPane.add(lblAddress);
		
		labelPort = new JLabel("Port:");
		labelPort.setBounds(135, 173, 42, 14);
		contentPane.add(labelPort);
		
		textPort = new JTextField();
		textPort.setColumns(10);
		textPort.setBounds(64, 193, 165, 20);
		contentPane.add(textPort);
		
		lblAddressDesc = new JLabel("(eg. 192.168.0.2)");
		lblAddressDesc.setBounds(99, 144, 103, 14);
		contentPane.add(lblAddressDesc);
		
		lblPortDesc = new JLabel("(eg. 8192)");
		lblPortDesc.setBounds(121, 219, 65, 14);
		contentPane.add(lblPortDesc);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = Integer.parseInt(textPort.getText());
				login(name, address, port);
			}
		});
		btnLogin.setBounds(102, 305, 89, 23);
		contentPane.add(btnLogin);
	}
	/**
	 * Login stuff here
	 */
	private void login(String name, String address, int port) {
		dispose();
		System.out.println(name + ", " + address + ", " + port);
	}
}
