package com.dataCopy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;

/** 
* @ClassName: Main 
* @Description: 主程序
* @author qiugui 
* @date 2015年3月10日 下午12:01:07 
*  
*/ 
public class Main implements ActionListener{

	public static JFrame frame;
	public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//“日志”组件信息
	private JScrollPane logPane;
	private JTextArea log;
	
	//“UI”组件信息
	private JPanel uiPanel;
	private JPanel buttonPanel;
	private JButton configButton;
	private JButton runButton;
	private JProgressBar progressBar;

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
	 * @Title:  Main   
	 * @Description:  JFrame设置      
	 */  
	 
	public Main() {
		frame = new JFrame("数据库同步");
		frame.setSize(450, 300);
		Dimension frameSize = frame.getSize();
		if (frameSize.width > displaySize.width)  
			frameSize.width = displaySize.width;
		frame.setLocation((displaySize.width-frameSize.width)/2,(displaySize.height-frameSize.height)/2);
		frame.setResizable(false);
		this.initialize();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	/**   
	 * @Title: initialize   
	 * @Description: 初始化JFrame布局          
	 */
	 
	private void initialize() {
		//初始化组件信息
		log = new JTextArea();
		log.setColumns(38);
		log.setRows(12);
		log.setText("操作过程打印...");
		logPane = new JScrollPane(log);
		
		uiPanel = new JPanel();
		uiPanel.setLayout(new GridLayout(2, 1));
		buttonPanel = new JPanel();
		configButton = new JButton("设置参数");
		configButton.addActionListener(this);		
		runButton = new JButton("运行");
		runButton.addActionListener(this);
		buttonPanel.add(configButton);
		buttonPanel.add(runButton);
		
		progressBar = new JProgressBar();
		progressBar.setSize(396, 6);
		progressBar.setValue(20);
		progressBar.setStringPainted(true);
		
		uiPanel.add(buttonPanel);
		uiPanel.add(progressBar);
		
		frame.add(logPane, BorderLayout.CENTER);;
		frame.add(uiPanel,BorderLayout.SOUTH);

	}

	/**   
	 * <p>Title: actionPerformed</p>   
	 * <p>Description: 监听事件</p>   
	 * @param e   
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)   
	 */
	 
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("设置参数")){
			ConfigJFrame configJFrame = new ConfigJFrame();
			configJFrame.createJDialog();
		}
		
		if (e.getActionCommand().equals("运行")){
			System.out.println("运行...");
		}
	}

}
