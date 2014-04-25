package com.benclive.security;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.CardLayout;

import java.awt.Component;


import java.awt.SystemColor;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;


public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4749512847347806958L;
	private JPanel mainPanel;
	private Controller controller;
	private MainView m = this;
	private JPanel cardPanel;
	private SoftwareView softwareView;
	private SettingsView settingsView;
	
	public MainView(String title, Controller c) {
		setResizable(false);
		this.setTitle(title);
		this.controller = c;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Options");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Show installed software");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (softwareView == null) {
					softwareView = new SoftwareView(controller.getSoftwareList());
				}
				softwareView.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mntmNewMenuItem_1.addActionListener(new ExitListener());
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Preferences");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (settingsView == null) {
					settingsView = new SettingsView(controller);
				}
				settingsView.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Help");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("About");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		init();
		
		this.setVisible(true);
	}
	
	public void init() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font f = new Font("Sans", Font.PLAIN, 18);
		this.setSize(440, 300);
		
		mainPanel = new JPanel();
		mainPanel.setBorder(null);
		getContentPane().add(mainPanel, BorderLayout.NORTH);

		
		//Enable the systray to be used.
		if (!SystemTray.isSupported()) {
			System.out.println("Systray not supported");
			return;
		}
	
		final TrayIcon trayIcon = new TrayIcon(createImage("/test.png", "tray icon"));
		trayIcon.addActionListener(new OpenListener());
		
		final SystemTray tray = SystemTray.getSystemTray();
		final PopupMenu popup = new PopupMenu();
		MenuItem showItem = new MenuItem("Open");
		showItem.addActionListener(new OpenListener());
		MenuItem closeItem = new MenuItem("Exit");
		closeItem.addActionListener(new ExitListener());
		
		popup.add(showItem);
		popup.add(closeItem);
		trayIcon.setPopupMenu(popup);
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		
		JPanel centrePanel = new JPanel();
		centrePanel.setAlignmentY(Component.TOP_ALIGNMENT);
		centrePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.add(centrePanel);
		GridBagLayout gbl_centrePanel = new GridBagLayout();
		gbl_centrePanel.columnWidths = new int[] {100};
		gbl_centrePanel.rowHeights = new int[] {50, 0, 0, 0};
		gbl_centrePanel.columnWeights = new double[]{1.0};
		gbl_centrePanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0};
		centrePanel.setLayout(gbl_centrePanel);
		
		JPanel logoHeader = new JPanel();
		logoHeader.setBackground(new Color(102, 204, 255));
		GridBagConstraints gbc_logoHeader = new GridBagConstraints();
		gbc_logoHeader.anchor = GridBagConstraints.BASELINE;
		gbc_logoHeader.fill = GridBagConstraints.BOTH;
		gbc_logoHeader.insets = new Insets(0, 0, 5, 0);
		gbc_logoHeader.gridx = 0;
		gbc_logoHeader.gridy = 0;
		centrePanel.add(logoHeader, gbc_logoHeader);
		GridBagLayout gbl_logoHeader = new GridBagLayout();
		gbl_logoHeader.columnWidths = new int[] {30};
		gbl_logoHeader.columnWeights = new double[]{0.0, 0.0};
		gbl_logoHeader.rowWeights = new double[]{0.0};
		logoHeader.setLayout(gbl_logoHeader);
		
		JLabel logoImage = new JLabel("");
		logoImage.setIcon(new ImageIcon(MainView.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		GridBagConstraints gbc_logoImage = new GridBagConstraints();
		gbc_logoImage.anchor = GridBagConstraints.WEST;
		gbc_logoImage.fill = GridBagConstraints.VERTICAL;
		gbc_logoImage.gridx = 0;
		gbc_logoImage.gridy = 0;
		logoHeader.add(logoImage, gbc_logoImage);
		
		JLabel lblVulmoVulnerabilityMonitor = new JLabel("VulMo Vulnerability Monitor");
		lblVulmoVulnerabilityMonitor.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_lblVulmoVulnerabilityMonitor = new GridBagConstraints();
		gbc_lblVulmoVulnerabilityMonitor.anchor = GridBagConstraints.WEST;
		gbc_lblVulmoVulnerabilityMonitor.fill = GridBagConstraints.BOTH;
		gbc_lblVulmoVulnerabilityMonitor.gridx = 1;
		gbc_lblVulmoVulnerabilityMonitor.gridy = 0;
		logoHeader.add(lblVulmoVulnerabilityMonitor, gbc_lblVulmoVulnerabilityMonitor);
		
		//Create the label and text box for the Unique ID
		JPanel topPanel = new JPanel();
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[] {430};
		gbl_topPanel.rowHeights = new int[] {30, 0};
		gbl_topPanel.columnWeights = new double[]{1.0};
		gbl_topPanel.rowWeights = new double[]{0.0, 0.0};
		topPanel.setLayout(gbl_topPanel);
		JLabel uniqueIdLabel = new JLabel("Device ID");
		uniqueIdLabel.setFont(f);
		GridBagConstraints gbc_uniqueIdLabel = new GridBagConstraints();
		gbc_uniqueIdLabel.anchor = GridBagConstraints.WEST;
		gbc_uniqueIdLabel.insets = new Insets(0, 10, 5, 5);
		gbc_uniqueIdLabel.gridx = 0;
		gbc_uniqueIdLabel.gridy = 0;
		topPanel.add(uniqueIdLabel, gbc_uniqueIdLabel);
		GridBagConstraints gbc_topPanel = new GridBagConstraints();
		gbc_topPanel.fill = GridBagConstraints.BOTH;
		gbc_topPanel.insets = new Insets(0, 0, 5, 0);
		gbc_topPanel.gridx = 0;
		gbc_topPanel.gridy = 1;
		centrePanel.add(topPanel, gbc_topPanel);
		JTextField uniqueIdBox = new JTextField(controller.getUniqueId());
		uniqueIdBox.setEditable(false);
		uniqueIdBox.setFont(f);
		GridBagConstraints gbc_uniqueIdBox = new GridBagConstraints();
		gbc_uniqueIdBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_uniqueIdBox.insets = new Insets(0, 10, 5, 10);
		gbc_uniqueIdBox.anchor = GridBagConstraints.NORTH;
		gbc_uniqueIdBox.gridx = 0;
		gbc_uniqueIdBox.gridy = 1;
		topPanel.add(uniqueIdBox, gbc_uniqueIdBox);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 10, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		centrePanel.add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainView.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("No applications discovered");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		panel.add(lblNewLabel_1);
		
		int numSoftware = controller.getSoftwareList().size();
		if (numSoftware > 0) {
			lblNewLabel_1.setText(numSoftware + " installed applications");
			lblNewLabel.setIcon(new ImageIcon(MainView.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		}
		
		cardPanel = new JPanel();
		GridBagConstraints gbc_cardPanel = new GridBagConstraints();
		gbc_cardPanel.anchor = GridBagConstraints.SOUTH;
		gbc_cardPanel.gridx = 0;
		gbc_cardPanel.gridy = 3;
		centrePanel.add(cardPanel, gbc_cardPanel);
		cardPanel.setLayout(new CardLayout(0, 0));
		
		JLayeredPane panel_1 = new JLayeredPane();
		panel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cardPanel.add(panel_1, "name_28049413466186");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.rowHeights = new int[] {30, 30, 30};
		gbl_panel_1.columnWidths = new int[] {100, 300};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnNewButton = new JButton("Register this device");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Desktop d = Desktop.getDesktop();
				try {
					d.browse(new URI("http://localhost:8000/device/add"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 10, 0, 0);
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		JTextArea txtrYouMustRegister = new JTextArea();
		txtrYouMustRegister.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrYouMustRegister.setBackground(SystemColor.control);
		txtrYouMustRegister.setDisabledTextColor(Color.BLACK);
		txtrYouMustRegister.setWrapStyleWord(true);
		txtrYouMustRegister.setEditable(false);
		txtrYouMustRegister.setEnabled(false);
		txtrYouMustRegister.setLineWrap(true);
		txtrYouMustRegister.setText("You must register this device before it can be used. Once registration is complete click the \"I've registered\" button.");
		GridBagConstraints gbc_txtrYouMustRegister = new GridBagConstraints();
		gbc_txtrYouMustRegister.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtrYouMustRegister.insets = new Insets(0, 10, 0, 0);
		gbc_txtrYouMustRegister.gridx = 1;
		gbc_txtrYouMustRegister.gridy = 1;
		panel_1.add(txtrYouMustRegister, gbc_txtrYouMustRegister);
		
		JButton btnNewButton_1 = new JButton("I've registered");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.isRegistered()) {
					setRegisteredPanel();
				} else {
					//Error message?
					
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 10, 0, 0);
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 2;
		panel_1.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JLayeredPane panel_2 = new JLayeredPane();
		cardPanel.add(panel_2, "name_28058764659625");
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {100, 300};
		gbl_panel_2.rowHeights = new int[] {60, 30};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0};
		panel_2.setLayout(gbl_panel_2);
		
		JButton btnNewButton_2 = new JButton("My Account");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop d = Desktop.getDesktop();
				try {
					d.browse(new URI(controller.getServerURI()));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 1;
		panel_2.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JLabel lblNewLabel_2 = new JLabel("This device has been registered");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 10, 0, 0);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 1;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		if (controller.isRegistered()) {
			setRegisteredPanel();
		}
	}

	private void setRegisteredPanel() {
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		cl.next(cardPanel);
	}
	//Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = MainView.class.getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    
    private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			m.dispose();
			controller.shutdown();
		}
    }
    
    private class OpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			m.setVisible(true);
		}
    }
}
