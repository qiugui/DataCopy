 package com.dataCopy;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
 public class ConfigJFrame {
	 
	 private JDialog jDialog = new JDialog(Main.frame, "设置", ModalityType.APPLICATION_MODAL);
	 
	 //“导入数据库配置”组件信息
	private JLabel importLabel;
	private JLabel importIPLabel;
	private JLabel importPortLabel;
	private JLabel importDatabaseLabel;
	private JLabel importUsernameLabel;
	private JLabel importPasswordLabel;
	
	private JTextField importIPText;
	private JTextField importPortText;
	private JTextField importDatabaseText;
	private JTextField importUsernameText;
	private JPasswordField importPassword;
	
	//“导出数据库配置”组件信息
	private JLabel exportLabel;
	private JLabel exportIPLabel;
	private JLabel exportPortLabel;
	private JLabel exportDatabaseLabel;
	private JLabel exportUsernameLabel;
	private JLabel exportPasswordLabel;
	
	private JTextField exportIPText;
	private JTextField exportPortText;
	private JTextField exportDatabaseText;
	private JTextField exportUsernameText;
	private JPasswordField exportPassword;
	
	//显示导出数据表 组件
	JScrollPane tablePanel;
	JTextArea tableTextArea;
	
	//“设置自动运行时间”组件信息
	private JPanel uiPanel;
	private JPanel buttonPanel;
	private JPanel time1Panel;
	private JPanel time2Panel;
	private JPanel time3Panel;
	
	private JLabel autoRuntimeLabel;
	private JTextField hour1;
	private JTextField minute1;
	private JTextField second1;
	
	private JTextField hour2;
	private JTextField minute2;
	private JTextField second2;
	
	private JTextField hour3;
	private JTextField minute3;
	private JTextField second3;

	private JButton okButton;
	
	 /**   
	 * @Title: createJDialog   
	 * @Description: 创建JDialog          
	 */
	 
	public void createJDialog(){
		 jDialog.setSize(400, 350);
		 Dimension dialogSize = jDialog.getSize();
		 jDialog.setLocation((Main.displaySize.width-dialogSize.width)/2,(Main.displaySize.height-dialogSize.height)/2);
		 jDialog.setResizable(false);
		 this.init();
		 jDialog.setVisible(true);		 
	 }
	 
	 /**   
	 * @Title: init   
	 * @Description: 初始化JDialog的布局           
	 */
	 
	public void init(){
		 //初始化组件信息
		importLabel = new JLabel("导入数据库配置");
		importIPLabel = new JLabel("IP地址");
		importIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importPortLabel = new JLabel("端口号");
		importPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importDatabaseLabel = new JLabel("数据库名称");
		importDatabaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importUsernameLabel = new JLabel("用户名");
		importUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importPasswordLabel = new JLabel("密码");
		importPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		importIPText = new JTextField(10);
		importPortText = new JTextField(10);
		importDatabaseText = new JTextField(10);
		importUsernameText = new JTextField(10);
		importPassword = new JPasswordField(10);
		
		exportLabel = new JLabel("导出数据库配置");
		exportIPLabel = new JLabel("IP地址");
		exportIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportPortLabel = new JLabel("端口号");
		exportPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportDatabaseLabel = new JLabel("数据库名称");
		exportDatabaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportUsernameLabel = new JLabel("用户名");
		exportUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportPasswordLabel = new JLabel("密码");
		exportPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		exportIPText = new JTextField(10);
		exportPortText = new JTextField(10);
		exportDatabaseText = new JTextField(10);
		exportUsernameText = new JTextField(10);
		exportPassword = new JPasswordField(10);
		
		tableTextArea = new JTextArea();
		tableTextArea.setSize(220, 125);
		tablePanel = new JScrollPane(tableTextArea);
		
		uiPanel = new JPanel(new GridLayout(5, 1));
		time1Panel = new JPanel();
		time2Panel = new JPanel();
		time3Panel = new JPanel();
		
		autoRuntimeLabel = new JLabel("设置自动运行时间");
		autoRuntimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hour1 = new JTextField(3);
		minute1 = new JTextField(3);
		second1 = new JTextField(3);
		hour1.setText("00");
		minute1.setText("00");
		second1.setText("00");
		time1Panel.add(hour1);
		time1Panel.add(new JLabel(":"));
		time1Panel.add(minute1);
		time1Panel.add(new JLabel(":"));
		time1Panel.add(second1);
		
		hour2 = new JTextField(3);
		minute2 = new JTextField(3);
		second2 = new JTextField(3);
		hour2.setText("00");
		minute2.setText("00");
		second2.setText("00");
		time2Panel.add(hour2);
		time2Panel.add(new JLabel(":"));
		time2Panel.add(minute2);
		time2Panel.add(new JLabel(":"));
		time2Panel.add(second2);
		
		hour3 = new JTextField(3);
		minute3 = new JTextField(3);
		second3 = new JTextField(3);
		hour3.setText("00");
		minute3.setText("00");
		second3.setText("00");
		time3Panel.add(hour3);
		time3Panel.add(new JLabel(":"));
		time3Panel.add(minute3);
		time3Panel.add(new JLabel(":"));
		time3Panel.add(second3);
		
		buttonPanel = new JPanel();
		okButton = new JButton("确定");
		buttonPanel.add(okButton);
		uiPanel.add(autoRuntimeLabel);
		uiPanel.add(time1Panel);
		uiPanel.add(time2Panel);
		uiPanel.add(time3Panel);
		uiPanel.add(buttonPanel);
		//组件信息初始完成
		
		//设置布局
		GridBagLayout layout = new GridBagLayout();
		jDialog.setLayout(layout);
		
		jDialog.add(importLabel);
		jDialog.add(exportLabel);
		jDialog.add(importIPLabel);
		jDialog.add(importIPText);
		jDialog.add(exportIPLabel);
		jDialog.add(exportIPText);
		jDialog.add(importPortLabel);
		jDialog.add(importPortText);
		jDialog.add(exportPortLabel);
		jDialog.add(exportPortText);
		jDialog.add(importDatabaseLabel);
		jDialog.add(importDatabaseText);
		jDialog.add(exportDatabaseLabel);
		jDialog.add(exportDatabaseText);
		jDialog.add(importUsernameLabel);
		jDialog.add(importUsernameText);
		jDialog.add(exportUsernameLabel);
		jDialog.add(exportUsernameText);
		jDialog.add(importPasswordLabel);
		jDialog.add(importPassword);
		jDialog.add(exportPasswordLabel);
		jDialog.add(exportPassword);
		jDialog.add(tablePanel);
		jDialog.add(uiPanel);
		
		//定义一个GridBagConstraints，用来控制添加进的组件的显示位置
		GridBagConstraints gbc = new GridBagConstraints();
		//设置组件所在的区域比组件本身要大时的显示情况 
		//BOTH：使组件完全填满其显示区域
		gbc.fill = GridBagConstraints.CENTER;
		
		//设置组件水平所占用的格子数，如果为0，说明该组件是该行的最后一个
		gbc.gridwidth = 2;
		//设置组件水平的拉伸幅度，如果为0，说明不拉伸，不为0，就随着窗口增大进行拉伸（0到1之间）
		gbc.weightx = 0;
		//设置组件垂直的拉伸幅度，如果为0，说明不拉伸，不为0，就随着窗口增大进行拉伸，0到1之间
		gbc.weighty = 0;
		//设置组件
		layout.setConstraints(importLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importIPLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importIPText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportIPLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportIPText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPortLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPortText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportPortLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportPortText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importDatabaseLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importDatabaseText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportDatabaseLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportDatabaseText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importUsernameLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importUsernameText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportUsernameLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportUsernameText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPasswordLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPassword, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportPasswordLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportPassword, gbc);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		layout.setConstraints(tablePanel, gbc);
		gbc.gridwidth = 0;
		gbc.weighty = 0;
		layout.setConstraints(uiPanel, gbc);
		
	 }
}

 