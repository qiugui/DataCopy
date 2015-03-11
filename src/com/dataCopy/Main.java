package com.dataCopy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//“菜单”组件信息
	private JMenuBar jMenuBar;
	private JMenu startMenu;
	private JMenu settingMenu;
	private JMenu helpMenu;
	private JMenuItem runMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem connMenuItem;
	private JMenuItem timingMenuItem;
	private JMenuItem userinfoMenuItem;
	private JMenuItem aboutMenuItem;
	
	//数据库信息
	private MydatabaseLabel databaseLabel;
	
	//“日志”组件信息
	private JScrollPane logPane;
	private JTextArea log;
	
	//“UI”组件信息
	private JPanel runInfoPanel;
	private JLabel stateLabel;
	private JProgressBar progressBar;  
	 
	private class MydatabaseLabel extends JLabel implements Runnable {

		private static final long serialVersionUID = 1L;

		public void run() {
			for (int i=1;i<=3;i++){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					 e.printStackTrace();
				}
				if (i==1) {
					databaseLabel.setForeground(Color.green);
				} else if (i==2) {
					databaseLabel.setForeground(Color.blue);
				} else {
					databaseLabel.setForeground(Color.red);
					i=0;	
				}
			}
		}
	}
	
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
		timingMenuItem = new JMenuItem("设置定时任务");
		timingMenuItem.addActionListener(this);
		userinfoMenuItem = new JMenuItem("用户信息设置");
		userinfoMenuItem.addActionListener(this);
		aboutMenuItem = new JMenuItem("关于");
		aboutMenuItem.addActionListener(this);
		startMenu = new JMenu("开始");
		startMenu.addMouseListener(new MouseAdapter() {
	            public void mouseEntered(MouseEvent e) {
	            	startMenu.setForeground(Color.red);
	            }
	 
	            public void mouseExited(MouseEvent e) {
	            	startMenu.setForeground(Color.black);
	            }
		});
		settingMenu = new JMenu("设置");
		settingMenu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	settingMenu.setForeground(Color.red);
            }
 
            public void mouseExited(MouseEvent e) {
            	settingMenu.setForeground(Color.black);
            }
	});
		helpMenu = new JMenu("帮助");
		helpMenu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	helpMenu.setForeground(Color.red);
            }
 
            public void mouseExited(MouseEvent e) {
            	helpMenu.setForeground(Color.black);
            }
	});

		jMenuBar = new JMenuBar();
		startMenu.add(runMenuItem);
		startMenu.addSeparator();
		startMenu.add(exitMenuItem);
		settingMenu.add(connMenuItem);
		settingMenu.add(timingMenuItem);
		settingMenu.addSeparator();
		settingMenu.add(userinfoMenuItem);
		helpMenu.add(aboutMenuItem);
		jMenuBar.add(startMenu);
		jMenuBar.add(settingMenu);
		jMenuBar.add(helpMenu);
		frame.setJMenuBar(jMenuBar);

		databaseLabel = new MydatabaseLabel();
		databaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		databaseLabel.setForeground(Color.red);
		databaseLabel.setText("当前数据库为：Mart库 --- MartJS库");
		Thread thread = new Thread(databaseLabel);
		thread.start();

		log = new JTextArea();
		log.setText("操作过程打印...");
		log.setEditable(false);
		logPane = new JScrollPane(log);
		
		runInfoPanel = new JPanel();
		runInfoPanel.setLayout(new GridLayout(2, 1));
		stateLabel = new JLabel("即将运行");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		runInfoPanel.add(progressBar);
		runInfoPanel.add(stateLabel);
		
		frame.add(databaseLabel,BorderLayout.NORTH);
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
		
		if (e.getActionCommand().equals("立即运行")){
			System.out.println("运行...");
		}
		
		if (e.getActionCommand().equals("退出")){
			frame.dispose();
			System.exit(0);
		}
		
		if (e.getActionCommand().equals("连接参数设置")){
			ConfigJFrame configJFrame = new ConfigJFrame();
			configJFrame.createJDialog();
		}
		
		if (e.getActionCommand().equals("设置定时任务")){
			new TimingJFrame();
		}
		
		if (e.getActionCommand().equals("用户信息设置")){
			new UsersettingJFrame();
		}
		
		if (e.getActionCommand().equals("关于")){
			new AboutJFrame();
		}
		
	}

	public void run() {
		for (int i=0;i<=100; i++) {
			if(i==1){
				stateLabel.setText("正在运行中...");
			}
			progressBar.setValue(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i==100){
				stateLabel.setText("运行结束！");
			}
		}
	}

}
