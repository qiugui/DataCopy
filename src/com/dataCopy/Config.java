package com.dataCopy;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
public class Config {
	
	JPanel importPanel;
	JPanel exportPanel;
	JScrollPane tablePanel;
	JPanel uiPanel;
	JPanel time1Panel;
	JPanel time2Panel;
	JPanel time3Panel;
	JPanel buttonPanel;
	
	JLabel importLabel;
	JLabel importIPLabel;
	JLabel importPortLabel;
	JLabel importDatabaseLabel;
	JLabel importUsernameLabel;
	JLabel importPasswordLabel;
	
	JTextField importIPText;
	JTextField importPortText;
	JTextField importDatabaseText;
	JTextField importUsernameText;
	JPasswordField importPassword;
	
	JLabel exportLabel;
	JLabel exportIPLabel;
	JLabel exportPortLabel;
	JLabel exportDatabaseLabel;
	JLabel exportUsernameLabel;
	JLabel exportPasswordLabel;
	
	JTextField exportIPText;
	JTextField exportPortText;
	JTextField exportDatabaseText;
	JTextField exportUsernameText;
	JPasswordField exportPassword;
	
	JTextArea tableTextArea;
	
	JLabel autoRuntimeLabel;
	JTextField hour1;
	JTextField minute1;
	JTextField second1;
	
	JTextField hour2;
	JTextField minute2;
	JTextField second2;
	
	JTextField hour3;
	JTextField minute3;
	JTextField second3;

	JButton okButton;
	
	
	public void init(){
		JDialog jDialog = new JDialog(Main.frame, "设置", ModalityType.APPLICATION_MODAL);
		jDialog.setSize(400, 400);
		Dimension dialogSize = jDialog.getSize();
		jDialog.setLocation((Main.displaySize.width-dialogSize.width)/2,(Main.displaySize.height-dialogSize.height)/2);
		jDialog.setLayout(new GridLayout(2, 2));
		jDialog.setResizable(false);
		
		importPanel = new JPanel(new GridLayout(6, 2, 0, 5));
		exportPanel = new JPanel(new GridLayout(6, 2, 0, 5));
		uiPanel = new JPanel(new GridLayout(5, 1));
		time1Panel = new JPanel();
		time2Panel = new JPanel();
		time3Panel = new JPanel();
		
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
		
		importPanel.add(importLabel);
		importPanel.add(new JLabel());
		importPanel.add(importIPLabel);
		importPanel.add(importIPText);
		importPanel.add(importPortLabel);
		importPanel.add(importPortText);
		importPanel.add(importDatabaseLabel);
		importPanel.add(importDatabaseText);
		importPanel.add(importUsernameLabel);
		importPanel.add(importUsernameText);
		importPanel.add(importPasswordLabel);
		importPanel.add(importPassword);
		
		
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
		
		exportPanel.add(exportLabel);
		exportPanel.add(new JLabel());
		exportPanel.add(exportIPLabel);
		exportPanel.add(exportIPText);
		exportPanel.add(exportPortLabel);
		exportPanel.add(exportPortText);
		exportPanel.add(exportDatabaseLabel);
		exportPanel.add(exportDatabaseText);
		exportPanel.add(exportUsernameLabel);
		exportPanel.add(exportUsernameText);
		exportPanel.add(exportPasswordLabel);
		exportPanel.add(exportPassword);
		
		tableTextArea = new JTextArea();
		tableTextArea.setSize(220, 125);
		tablePanel = new JScrollPane(tableTextArea);
		
		autoRuntimeLabel = new JLabel("设置自动运行时间");
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
		
		jDialog.add(importPanel);
		jDialog.add(exportPanel);
		jDialog.add(tablePanel);
		jDialog.add(uiPanel);
		
		jDialog.setVisible(true);
	}
}
