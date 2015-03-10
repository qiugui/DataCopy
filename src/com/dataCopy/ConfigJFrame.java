 package com.dataCopy;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
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
	private JLabel importUsernameLabel;
	private JLabel importPasswordLabel;
	
	private JTextField importIPText;
	private JTextField importPortText;
	private JTextField importUsernameText;
	private JPasswordField importPassword;
	
	//“导出数据库配置”组件信息
	private JLabel exportLabel;
	private JLabel exportIPLabel;
	private JLabel exportPortLabel;
	private JLabel exportUsernameLabel;
	private JLabel exportPasswordLabel;
	
	private JTextField exportIPText;
	private JTextField exportPortText;
	private JTextField exportUsernameText;
	private JPasswordField exportPassword;
	
	//显示导出数据表 组件
	JScrollPane tablePane;
	JTextArea tableTextArea;
	
	 /**   
	 * @Title: createJDialog   
	 * @Description: 创建JDialog          
	 */
	 
	public void createJDialog(){
		 jDialog.setSize(400, 350);
		 Dimension dialogSize = jDialog.getSize();
		 jDialog.setLocation((Main.displaySize.width-dialogSize.width)/2,(Main.displaySize.height-dialogSize.height)/2);
		 jDialog.setResizable(false);
		 this.initialize();
		 jDialog.setVisible(true);		 
	 }
	 
	 /**   
	 * @Title: init   
	 * @Description: 初始化JDialog的布局           
	 */
	 
	public void initialize(){
		 //初始化组件信息
		importLabel = new JLabel("导入数据库配置");
		importIPLabel = new JLabel("IP地址");
		importIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importPortLabel = new JLabel("端口号");
		importPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importUsernameLabel = new JLabel("用户名");
		importUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importPasswordLabel = new JLabel("密码");
		importPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		importIPText = new JTextField(10);
		importPortText = new JTextField(10);
		importUsernameText = new JTextField(10);
		importPassword = new JPasswordField(10);
		
		exportLabel = new JLabel("导出数据库配置");
		exportIPLabel = new JLabel("IP地址");
		exportIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportPortLabel = new JLabel("端口号");
		exportPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportUsernameLabel = new JLabel("用户名");
		exportUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportPasswordLabel = new JLabel("密码");
		exportPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		exportIPText = new JTextField(10);
		exportPortText = new JTextField(10);
		exportUsernameText = new JTextField(10);
		exportPassword = new JPasswordField(10);
		
		tableTextArea = new JTextArea();
		tableTextArea.setSize(220, 125);
		tablePane = new JScrollPane(tableTextArea);
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
		jDialog.add(importUsernameLabel);
		jDialog.add(importUsernameText);
		jDialog.add(exportUsernameLabel);
		jDialog.add(exportUsernameText);
		jDialog.add(importPasswordLabel);
		jDialog.add(importPassword);
		jDialog.add(exportPasswordLabel);
		jDialog.add(exportPassword);
		jDialog.add(tablePane);
		
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
		gbc.gridwidth = 4;
		gbc.weighty = 1;
		layout.setConstraints(tablePane, gbc);
	 }
}

 