 package com.dataCopy;

import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 public class UsersettingJFrame implements ActionListener{

	 private JDialog jDialog = new JDialog(Main.frame, "用户信息设置", ModalityType.APPLICATION_MODAL);
	
	 private JLabel usernameLabel;
	 private JTextField usernameTextField;
	 private JLabel oldPasswordLabel;
	 private JPasswordField oldPassword;
	 private JLabel newPasswordLabel;
	 private JPasswordField newPassword;
	 private JLabel confirmPasswordLabel;
	 private JPasswordField confirmPassword;
	 private JPanel buttonPanel;
	 private JButton okButton;
	 
	 public UsersettingJFrame() {
		 jDialog.setSize(200, 175);
		 Dimension dialogSize = jDialog.getSize();
		 jDialog.setLocation((Main.displaySize.width-dialogSize.width)/2,(Main.displaySize.height-dialogSize.height)/2);
		 jDialog.setResizable(false);
		 this.initialize();
		 jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		 jDialog.setVisible(true);

	}
	 
	private void initialize() {

		//初始化控件信息
		 usernameLabel = new JLabel("用户名");
		 usernameTextField = new JTextField(10);
		 oldPasswordLabel = new JLabel("原密码");
		 oldPassword = new JPasswordField(10);
		 newPasswordLabel = new JLabel("新密码");
		 newPassword = new JPasswordField(10);
		 confirmPasswordLabel = new JLabel("确认密码");
		 confirmPassword = new JPasswordField(10);
		 buttonPanel = new JPanel();
		 okButton = new JButton("确认修改");
		 okButton.addActionListener(this);
		 buttonPanel.add(okButton);
		 
		GridBagLayout layout = new GridBagLayout();
		jDialog.setLayout(layout);
		
		jDialog.add(usernameLabel);
		jDialog.add(usernameTextField);
		jDialog.add(oldPasswordLabel);
		jDialog.add(oldPassword);
		jDialog.add(newPasswordLabel);
		jDialog.add(newPassword);
		jDialog.add(confirmPasswordLabel);
		jDialog.add(confirmPassword);
		jDialog.add(buttonPanel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.CENTER;
		
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		layout.setConstraints(usernameLabel, gbc);
		
		gbc.gridwidth = 0;
		layout.setConstraints(usernameTextField, gbc);
		
		gbc.gridwidth = 1;
		layout.setConstraints(oldPasswordLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(oldPassword, gbc);
		
		gbc.gridwidth = 1;
		layout.setConstraints(newPasswordLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(newPassword, gbc);
		
		gbc.gridwidth = 1;
		layout.setConstraints(confirmPasswordLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(confirmPassword, gbc);
		
		gbc.gridwidth = 0;
		layout.setConstraints(buttonPanel, gbc);
		 
	}

	public void actionPerformed(ActionEvent e) {
		 if(e.getActionCommand().equals("确认修改")){
			 jDialog.dispose();
		 }
	}

}

 