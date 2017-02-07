package nukechat;

import java.awt.*;

import javax.swing.*;

public class Users extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JList list;

	@SuppressWarnings("rawtypes")
	public Users() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(200, 300);
		setTitle("Who's Online?");
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(list);
		contentPane.add(scrollpane, gbc_list);
		list.setFont(new Font("Arial", 0, 24));
	}

	@SuppressWarnings("unchecked")
	public void update( String[] users ) {
		list.setListData(users);
	}
}