package nukechat;

import javax.swing.*;

public class Users extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList list;
	
	public Users() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(200, 300);
		setTitle("Who's Online?");
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		
		
		list = new JList();
	}
	
	public void update(String[] users) {
		list.setListData(users);
	}
}