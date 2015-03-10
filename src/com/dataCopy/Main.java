package com.dataCopy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
public class Main implements ActionListener,Runnable{

	public static JFrame frame;
	private static Thread thread;
	public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//“菜单”组件信息
	private JMenuBar jMenuBar;
	private JMenu startMenu;
	private JMenu settingMenu;
	private JMenuItem aboutMenu;
	private JMenuItem runMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem connMenuItem;
	private JMenuItem timingMenuItem;
	private JMenuItem userinfoMenuItem;
	
	//“日志”组件信息
	private JScrollPane logPane;
	private JTextArea log;
	
	//“UI”组件信息
	private JPanel runInfoPanel;
	private JLabel stateLabel;
	private JProgressBar progressBar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					thread = new Thread(window);
					thread.start();
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
		runMenuItem = new JMenuItem("立即运行");
		runMenuItem.addActionListener(this);
		exitMenuItem = new JMenuItem("退出");
		exitMenuItem.addActionListener(this);
		connMenuItem = new JMenuItem("连接参数设置");
		connMenuItem.addActionListener(this);
		timingMenuItem = new JMenuItem("定时任务参数");
		userinfoMenuItem = new JMenuItem("用户信息设置");
		startMenu = new JMenu("开始");
		settingMenu = new JMenu("设置");
		aboutMenu = new JMenuItem("关于");
		aboutMenu.addActionListener(this);
		jMenuBar = new JMenuBar();
		startMenu.add(runMenuItem);
		startMenu.addSeparator();
		startMenu.add(exitMenuItem);
		settingMenu.add(connMenuItem);
		settingMenu.add(timingMenuItem);
		settingMenu.add(userinfoMenuItem);
		jMenuBar.add(startMenu);
		jMenuBar.add(settingMenu);
		jMenuBar.add(aboutMenu);
		frame.setJMenuBar(jMenuBar);

		log = new JTextArea();
		log.setColumns(38);
		log.setRows(12);
		log.setText("操作过程打印...");
		logPane = new JScrollPane(log);
		
		runInfoPanel = new JPanel();
		runInfoPanel.setLayout(new GridLayout(2, 1));
		stateLabel = new JLabel("正在运行中...");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		runInfoPanel.add(progressBar);
		runInfoPanel.add(stateLabel);
		
		frame.add(logPane, BorderLayout.CENTER);;
		frame.add(runInfoPanel,BorderLayout.SOUTH);

	}

	/**   
	 * <p>Title: actionPerformed</p>   
	 * <p>Description: 监听事件</p>   
	 * @param e   
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)   
	 */
	 
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("连接参数设置")){
			ConfigJFrame configJFrame = new ConfigJFrame();
			configJFrame.createJDialog();
		}
		
		if (e.getActionCommand().equals("立即运行")){
			System.out.println("运行...");
		}
		
		if (e.getActionCommand().equals("退出")){
			//frame.dispose();
			System.exit(0);
		}
		
		if (e.getActionCommand().equals("关于")){
			AboutJFrame aboutJFrame = new AboutJFrame();
		}
		
	}

	public void run() {
		System.out.println("进入线程");
		for (int i=0;i<=100; i++) {
			progressBar.setValue(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
