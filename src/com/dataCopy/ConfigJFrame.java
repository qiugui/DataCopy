 package com.dataCopy;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

 public class ConfigJFrame implements ActionListener,ItemListener,CaretListener{
	 
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
	
	//导入导出库选择
	private JPanel inOutDatabasePanel;
	private JLabel inOutDatabaselLabel;
	private ButtonGroup buttonGroup;
	private JRadioButton jRadioButton1;
	private JRadioButton jRadioButton2;
	
	private JPanel buttonpPanel;
	private JButton okButton;
	private JButton cancelButton;
	
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
		 jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		importIPText.setText("192.168.216.123");
		importIPText.addCaretListener(this);
		importPortText = new JTextField(10);
		importPortText.addCaretListener(this);
		importUsernameText = new JTextField(10);
		importUsernameText.addCaretListener(this);
		importPassword = new JPasswordField(10);
		importPassword.setText("123456");
		importPassword.addCaretListener(this);
		
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
		exportIPText.addCaretListener(this);
		exportPortText = new JTextField(10);
		exportPortText.addCaretListener(this);
		exportUsernameText = new JTextField(10);
		exportUsernameText.addCaretListener(this);
		exportPassword = new JPasswordField(10);
		exportPassword.addCaretListener(this);
		
		tableTextArea = new JTextArea(7,15);
		tableTextArea.setEditable(false);
		tablePane = new JScrollPane(tableTextArea);
		
		inOutDatabasePanel = new JPanel(new GridLayout(3, 1));
		inOutDatabaselLabel = new JLabel("选择导入、导出数据库");
		inOutDatabaselLabel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup = new ButtonGroup();
		jRadioButton1 = new JRadioButton("Mart库 --- MartJS库");
		jRadioButton1.addItemListener(this);
		jRadioButton2 = new JRadioButton("AD库 --- ADJS库");
		jRadioButton2.addItemListener(this);
		buttonGroup.add(jRadioButton1);
		buttonGroup.add(jRadioButton2);
		inOutDatabasePanel.add(inOutDatabaselLabel);
		inOutDatabasePanel.add(jRadioButton1);
		inOutDatabasePanel.add(jRadioButton2);
		
		buttonpPanel = new JPanel();
		okButton = new JButton("确定");
		okButton.addActionListener(this);
		cancelButton = new JButton("取消");
		cancelButton.addActionListener(this);
		buttonpPanel.add(okButton);
		buttonpPanel.add(cancelButton);
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
		jDialog.add(inOutDatabasePanel);
		jDialog.add(tablePane);
		jDialog.add(buttonpPanel);
		
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
		gbc.gridwidth = 0;
		layout.setConstraints(inOutDatabasePanel, gbc);
		gbc.gridwidth = 0;
		gbc.weighty = 1;
		layout.setConstraints(tablePane, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(buttonpPanel, gbc);
	 }

	public void itemStateChanged(ItemEvent e) {
		if(jRadioButton1.isSelected()){
			tableTextArea.setText("展示Mart库的表");
		}
		if(jRadioButton2.isSelected()){
			tableTextArea.setText("");
			tableTextArea.setText("展示AD库的表");
		}
		 
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("确定")){
			jDialog.dispose();
		}
		if (e.getActionCommand().equals("取消")){
			jDialog.dispose();
		}
	}

	public void caretUpdate(CaretEvent e) {
		 if (e.getSource().equals(importIPText)){
			 System.out.println("importIPText被更改");
		 }
		 if (e.getSource().equals(importPortText)){
			 System.out.println("importPortText被更改");
		 }
		 if (e.getSource().equals(importUsernameText)){
			 System.out.println("importUsernameText被更改");
		 }
		 if (e.getSource().equals(importPassword)){
			 System.out.println("importPassword被更改"+new String(importPassword.getPassword()));
		 }
		 if (e.getSource().equals(exportIPText)){
			 System.out.println("exportIPText被更改");
		 }
		 if (e.getSource().equals(exportPortText)){
			 System.out.println("exportPortText被更改");
		 }
		 if (e.getSource().equals(exportUsernameText)){
			 System.out.println("exportUsernameText被更改");
		 }
		 if (e.getSource().equals(exportPassword)){
			 System.out.println("exportPassword被更改"+new String(exportPassword.getPassword()));
		 }
	}
}

 