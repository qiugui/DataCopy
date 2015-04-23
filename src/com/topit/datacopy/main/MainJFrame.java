package com.topit.datacopy.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JProgressBar;


import com.topit.datacopy.config.AutoTask;
import com.topit.datacopy.config.Constants;
import com.topit.datacopy.manager.AutoTaskManager;
import com.topit.datacopy.manager.ConfigManager;
import com.topit.datacopy.utils.SqlUtil;


/** 
* @ClassName: Main 
* @Description: 主程序
* @author qiugui 
* @date 2015年3月10日 下午12:01:07 
*  
*/ 
public class MainJFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = -2611435109011302541L;

	public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//“菜单”组件信息
	private JMenuBar jMenuBar;
	private JMenu startMenu;
	private JMenu settingMenu;
	private JMenu helpMenu;
	private JMenuItem runMenuItem;
	private JMenuItem stopMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem connMenuItem;
	private JMenuItem timingMenuItem;
	private JMenuItem userinfoMenuItem;
	private JMenuItem logMenuItem;
	private JMenuItem aboutMenuItem;
	
	//数据库信息
	public static MyTaskNameLabel taskNameLabel;
	
	//“日志”组件信息
	private JScrollPane logPane;
	public static JTextArea log;
	
	//“UI”组件信息
	private JPanel runInfoPanel;
	public static JLabel stateLabel;
	public static JProgressBar progressBar;  
	public static JLabel nextTimeLabel;
	
	
	public class MyTaskNameLabel extends JLabel implements Runnable {

		private static final long serialVersionUID = 1L;

		public void run() {
			for (int i=1;i<=3;i++){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					 e.printStackTrace();
				}
				if (i==1) {
					taskNameLabel.setForeground(Color.green);
				} else if (i==2) {
					taskNameLabel.setForeground(Color.blue);
				} else {
					taskNameLabel.setForeground(Color.red);
					i=0;	
				}
			}
		}
	}
	
	public MainJFrame() {

		this.setTitle("数据库同步");
		this.setSize(450, 300);
		Dimension frameSize = this.getSize();
		if (frameSize.width > displaySize.width)  
			frameSize.width = displaySize.width;
		this.setLocation((displaySize.width-frameSize.width)/2,(displaySize.height-frameSize.height)/2);
		this.setResizable(false);
		this.initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.startAutoTask();
		evaluateNextTime();
	}


	/**   
	 * @Title: initialize   
	 * @Description: 初始化JFrame布局          
	 */
	 
	private void initialize() {
		//初始化组件信息
		runMenuItem = new JMenuItem("立即运行");
		runMenuItem.addActionListener(this);
		stopMenuItem = new JMenuItem("结束运行");
		stopMenuItem.addActionListener(this);
		exitMenuItem = new JMenuItem("退出");
		exitMenuItem.addActionListener(this);
		connMenuItem = new JMenuItem("连接参数设置");
		connMenuItem.addActionListener(this);
		timingMenuItem = new JMenuItem("定时任务设置");
		timingMenuItem.addActionListener(this);
		userinfoMenuItem = new JMenuItem("用户信息设置");
		userinfoMenuItem.addActionListener(this);
		logMenuItem = new JMenuItem("打开日志文件");
		logMenuItem.addActionListener(this);
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
		startMenu.add(stopMenuItem);
		startMenu.addSeparator();
		startMenu.add(exitMenuItem);
		settingMenu.add(connMenuItem);
		settingMenu.add(timingMenuItem);
		settingMenu.addSeparator();
		settingMenu.add(userinfoMenuItem);
		helpMenu.add(logMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutMenuItem);
		jMenuBar.add(startMenu);
		jMenuBar.add(settingMenu);
		jMenuBar.add(helpMenu);
		this.setJMenuBar(jMenuBar);

		taskNameLabel = new MyTaskNameLabel();
		taskNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		taskNameLabel.setForeground(Color.red);
		taskNameLabel.setText("当前任务为："+Constants.taskName);
		Thread thread = new Thread(taskNameLabel);
		thread.start();

		log = new JTextArea();
		log.setText("操作过程打印...");
		log.setEditable(false);
		logPane = new JScrollPane(log);
		
		runInfoPanel = new JPanel();
		runInfoPanel.setLayout(new GridLayout(2, 1));
		stateLabel = new JLabel("当前状态：定时任务");
		nextTimeLabel = new JLabel();
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		GridBagLayout layout = new GridBagLayout();
		runInfoPanel.setLayout(layout);
		runInfoPanel.add(progressBar);
		runInfoPanel.add(stateLabel);
		runInfoPanel.add(nextTimeLabel);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1;
		gbc.gridwidth=0;
		layout.setConstraints(progressBar, gbc);
		gbc.gridwidth=1;
		layout.setConstraints(stateLabel, gbc);
		stateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		gbc.gridwidth=0;
		layout.setConstraints(nextTimeLabel, gbc);
		nextTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.add(taskNameLabel,BorderLayout.NORTH);
		this.add(logPane, BorderLayout.CENTER);;
		this.add(runInfoPanel,BorderLayout.SOUTH);

	}
	
	
	public static void evaluateNextTime(){
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			List<String> allTimes = new ArrayList<String>();
			String now = format.format(new Date());
			allTimes.add(now);
			HashMap<Integer, String> times = ConfigManager.getAutoTasks();
			for (int i=0;i<times.size();i++) {
				if(!"".equals(times.get(i+1))){
					allTimes.add(times.get(i+1));
				}
			}
			if(allTimes.size()==1){
				stateLabel.setText("当前状态：请设置定时任务！");
				nextTimeLabel.setText("提示：还未设置定时任务！");
			}else{
				Collections.sort(allTimes);
				for (int i=0;i<allTimes.size();i++){
					if(now.equals(allTimes.get(i))){
						if((i+1)==allTimes.size()){
							nextTimeLabel.setText("下次任务时间："+allTimes.get(0));
							return;
						}else{
							//避免任务执行完，时间仍在同一分钟内，显示出错
							if(now.equals(allTimes.get(i+1))){
								if(i+2>=allTimes.size()){
									nextTimeLabel.setText("下次任务时间："+allTimes.get(0));
								}else{
									nextTimeLabel.setText("下次任务时间："+allTimes.get(i+2));
								}
							}else{
								nextTimeLabel.setText("下次任务时间："+allTimes.get(i+1));
							}
							return;
						}
					}
				}
			}
		} catch (Exception e) {
			nextTimeLabel.setText("计算下次任务时间出错！");
			 Constants.logger.warn("计算下次任务时间出错！"+e.getMessage(),e);
		}
	}

	/**   
	 * @Title: startAutoTask   
	 * @Description: 启动所有定时任务           
	 */
	 
	public void startAutoTask(){
		try {
			HashMap<Integer, String> times = ConfigManager.getAutoTasks();
			for (int i=0;i<times.size();i++) {
				String time = times.get(i+1);
				if(!"".equals(time)){
					AutoTaskManager.addJob("任务"+(i+1), AutoTask.class, time);
				}
			}

			AutoTaskManager.startJobs();
		} catch (Exception e) {
			Constants.logger.error("启动定时任务失败！"+e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "启动定时任务失败！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**   
	 * <p>Title: actionPerformed</p>   
	 * <p>Description: 监听事件</p>   
	 * @param e   
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)   
	 */
	 
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("立即运行")){
			if("当前状态：自动运行中".equals(stateLabel.getText())){
				JOptionPane.showMessageDialog(this, "自动运行正在进行中，请勿操作！", "警告", JOptionPane.WARNING_MESSAGE);
			} else {	
				try {
					AutoTaskManager.addImmediateJob();
				} catch (Exception e1) {
					Constants.logger.error(e1.getMessage(), e1);
					JOptionPane.showMessageDialog(this, "启动手动运行任务失败！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE); 
				}
			}
		}
		
		if (e.getActionCommand().equals("结束运行")){
			if(!"当前状态：定时任务".equals(stateLabel.getText())){
				SqlUtil._interrupted = true;
				/*ThreadPool.getThreadPool().destroy(true);*/
				/*MainJFrame.stateLabel.setText("当前状态：定时任务");
				MainJFrame.progressBar.setValue(0);*/
			} else {
				JOptionPane.showMessageDialog(this, "非运行状态，无需中断！", "警告", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if (e.getActionCommand().equals("退出")){
			this.dispose();
			System.exit(0);
		}
		
		if (e.getActionCommand().equals("连接参数设置")){
			new ConfigJDialog();
		}
		
		if (e.getActionCommand().equals("定时任务设置")){
			new TimingJDialog();
		}
		
		if (e.getActionCommand().equals("用户信息设置")){
			new UsersettingJDialog();
		}
		
		if (e.getActionCommand().equals("打开日志文件")){
			try {
				java.awt.Desktop.getDesktop().open(new File("./logs"));
			} catch (Exception e1) {
				Constants.logger.error("打开日志文件夹出现错误！"+e1.getMessage(),e1);
				JOptionPane.showMessageDialog(this, "打开日志文件夹出现错误！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE); 
			}  
		}
		
		if (e.getActionCommand().equals("关于")){
			new AboutJDialog();
		}
	}
}
