package com.benclive.security;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class SoftwareView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3744767380326619494L;
	private JList<Software> jSoftwareList;
	private List<Software> softwareList;
	private DefaultListModel<Software> softwareListModel;
	
	public SoftwareView(List<Software> softwareList) {
		this.softwareList = softwareList;
		init();
	}
	
	public void init() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 300);
		this.setTitle("Installed Software");
		
		softwareListModel = new DefaultListModel<Software>();
		jSoftwareList = new JList<Software>(softwareListModel);
		jSoftwareList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for (Software s : this.softwareList) {
			addElementToSoftwareList(s);
		}
		JScrollPane scroller = new JScrollPane(jSoftwareList);
		this.add(scroller);
	}
	
	public void addElementToSoftwareList(Software element) {
		softwareListModel.addElement(element);
		revalidate();
		repaint();
	}
	
}
