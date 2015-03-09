package com.dataCopy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;

public class Main {

	public static JFrame frame;
	public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JTextArea log;
	
	JPanel textAreaPanel;
	JPanel uiPanel;
	JPanel buttonPanel;
	
	JButton configButton;
	JButton runButton;

	JProgressBar progressBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//设置JFrame的属性
		frame = new JFrame("数据库同步");
		frame.setSize(450, 300);
		Dimension frameSize = frame.getSize();
		if (frameSize.width > displaySize.width)  
			frameSize.width = displaySize.width;
		frame.setLocation((displaySize.width - frameSize.width) / 2,  
				(displaySize.height - frameSize.height) / 2);
		
		frame.setResizable(false);
		
		textAreaPanel = new JPanel();
		uiPanel = new JPanel();
		uiPanel.setLayout(new GridLayout(2, 1));
		buttonPanel = new JPanel();
		
		log = new JTextArea();
		frame.getContentPane().add(log);
		log.setColumns(39);
		log.setRows(15);
		log.setText("操作过程打印...");
		
		textAreaPanel.add(log);
		
		configButton = new JButton("设置参数");
		configButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config config = new Config();
				config.init();
			}
		});		
		runButton = new JButton("运行");
		buttonPanel.add(configButton);
		buttonPanel.add(runButton);
		
		progressBar = new JProgressBar();
		progressBar.setSize(396, 6);
		progressBar.setValue(20);
		progressBar.setStringPainted(true);
		
		uiPanel.add(buttonPanel);
		uiPanel.add(progressBar);
		
		frame.add(textAreaPanel, BorderLayout.CENTER);;
		frame.add(uiPanel,BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
