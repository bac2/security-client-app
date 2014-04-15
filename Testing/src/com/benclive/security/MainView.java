package com.benclive.security;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class MainView extends JFrame {
	private JPanel mainPanel;
	private JTextArea infoLabel;
	private Controller controller;
	private MainView m = this;
	
	public MainView(String title, Controller c) {
		this.setTitle(title);
		this.controller = c;
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
		this.setSize(450, 300);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		infoLabel = new JTextArea("Enter the device ID into the website when prompted. Click the registered button once complete.");
		infoLabel.setBackground(this.getBackground());
		infoLabel.setLineWrap(true);
		infoLabel.setWrapStyleWord(true);
		infoLabel.setFont(f);
		infoLabel.setDisabledTextColor(Color.black);
		infoLabel.setEnabled(false);

		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Options");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Show installed software");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame view = new SoftwareView(controller.getSoftwareList());
				view.setVisible(true);
			}
		});
		menu.add(menuItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ExitListener());
		menu.add(exitItem);
		
		
		JPanel centrePanel = new JPanel();
		centrePanel.setLayout(new GridLayout(3,1));
		
		//Create the label and text box for the Unique ID
		JPanel topPanel = new JPanel();
		JLabel uniqueIdLabel = new JLabel("Device unique ID: ");
		uniqueIdLabel.setFont(f);
		topPanel.add(uniqueIdLabel);
		JTextField uniqueIdBox = new JTextField(controller.getUniqueId());
		uniqueIdBox.setEditable(false);
		uniqueIdBox.setFont(f);
		topPanel.add(uniqueIdBox);
		
		//Add to the centre panel (Button if not registered, text if it is)
		centrePanel.add(infoLabel);
		centrePanel.add(topPanel);
		if (controller.isRegistered()) {
			JLabel l = new JLabel("This device is currently registered.");
			l.setFont(f);
			centrePanel.add(l);
		} else {
			JButton registeredButton = new JButton("I have registered");
			registeredButton.setFont(f);
			centrePanel.add(registeredButton);
			
			registeredButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.setRegistered(true);
					
				}
			});
		}
		
		//Enable the systray to be used.
		if (!SystemTray.isSupported()) {
			System.out.println("Systray not supported");
			return;
		}
	
		final MainView m = this;
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
		

		
		mainPanel.add(centrePanel);
		
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
