import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;


public class MainView extends JFrame {
	private JPanel mainPanel;
	private JLabel infoLabel;
	private JList<Software> softwareList;
	private DefaultListModel<Software> softwareListModel;
	
	public MainView(String title) {
		this.setTitle(title);
		init();
		this.setVisible(true);
	}
	
	public void init() {
		
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		infoLabel = new JLabel("Welcome to the client");
		mainPanel.add(infoLabel, BorderLayout.NORTH);
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("My Menu");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Default menu item");
		menu.add(menuItem);
		
		softwareListModel = new DefaultListModel<Software>();
		softwareList = new JList<Software>(softwareListModel);
		softwareList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		mainPanel.add(softwareList);
		
	}
	
	public void addElementToSoftwareList(Software element) {
		softwareListModel.addElement(element);
		revalidate();
		repaint();
	}
}