package com.dataCopy;

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
		frame.getContentPane().setLayout(new GridLayout(3, 1, 50, 50));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		JTextArea log = new JTextArea();
		frame.getContentPane().add(log);
		log.setColumns(10);
		log.setRows(4);
		log.setText("操作过程打印...");
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("设置参数");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config config = new Config();
				config.dialog();
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("运行");
		panel.add(btnNewButton);
		panel.add(btnNewButton_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setSize(400, 10);
		progressBar.setValue(10);
		progressBar.setStringPainted(true);
		frame.getContentPane().add(progressBar);
	}

}
