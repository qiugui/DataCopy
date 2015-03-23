 package com.topit.datacopy.main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.User;
import com.topit.datacopy.manager.UserManager;
import com.topit.datacopy.utils.MD5Encrypt;

 public class UsersettingJDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -1727255630805034721L;
	
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
	 private JButton cancelButton;
	 
	 public UsersettingJDialog() {
		 this.setModal(true);
		 this.setSize(250, 175);
		 this.setTitle("用户信息设置");
		 Dimension dialogSize = this.getSize();
		 this.setLocation((MainJFrame.displaySize.width-dialogSize.width)/2,(MainJFrame.displaySize.height-dialogSize.height)/2);
		 this.setResizable(false);
		 this.initialize();
		 this.dataInit();
		 this.editable(false);
		 this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		 this.setVisible(true);

	}
	 
	private void initialize() {

		//初始化控件信息
		 usernameLabel = new JLabel("用  户  名");
		 usernameTextField = new JTextField(10);
		 usernameTextField.setEditable(false);
		 oldPasswordLabel = new JLabel("原  密  码");
		 oldPassword = new JPasswordField(10);
		 newPasswordLabel = new JLabel("新  密  码");
		 newPassword = new JPasswordField(10);
		 confirmPasswordLabel = new JLabel("确认密码");
		 confirmPassword = new JPasswordField(10);
		 
		 buttonPanel = new JPanel();
		 okButton = new JButton("修改");
		 okButton.addActionListener(this);
		 cancelButton = new JButton("关闭");
		 cancelButton.addActionListener(this);
		 buttonPanel.add(okButton);
		 buttonPanel.add(cancelButton);
		 
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		this.add(usernameLabel);
		this.add(usernameTextField);
		this.add(oldPasswordLabel);
		this.add(oldPassword);
		this.add(newPasswordLabel);
		this.add(newPassword);
		this.add(confirmPasswordLabel);
		this.add(confirmPassword);
		this.add(buttonPanel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(2, 2, 2, 2);
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
	
	private void dataInit() {
		usernameTextField.setText(Constants.loginUsername);
		oldPassword.setText("");
		newPassword.setText("");
		confirmPassword.setText("");
	}
	
	public void editable(boolean editable){
		 oldPassword.setEditable(editable);
		 newPassword.setEditable(editable);;
		 confirmPassword.setEditable(editable);;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("修改")){
			this.editable(true);
			okButton.setText("完成");
			cancelButton.setEnabled(false);
		}
		if (e.getActionCommand().equals("完成")){
			Object[] options = {"保存","放弃"};   
			int response=JOptionPane.showOptionDialog(this, "是否保存更改？\n需重新登陆系统！", "提示",JOptionPane.OK_CANCEL_OPTION,   
			JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(response==0) { 
				try {
					String username = Constants.loginUsername;
					String password = new String(oldPassword.getPassword());
					User user = new User(username,MD5Encrypt.encipher(password));
					UserManager userManager = new UserManager();
					if (userManager.exist(user)){
						String newpassword = new String(newPassword.getPassword());
						String confirmpassword = new String(confirmPassword.getPassword());
						if ("".equals(newpassword) || "".equals(confirmpassword)){
							JOptionPane.showMessageDialog(this, "密码不能为空!", "提示", JOptionPane.WARNING_MESSAGE);
						} else {
							if (!newpassword.equals(confirmpassword)){
								JOptionPane.showMessageDialog(this, "两次密码输入不相同!", "提示", JOptionPane.WARNING_MESSAGE);
							} else {
								Constants.logger.warn("修改用户密码！");
								user.setPassword(MD5Encrypt.encipher(newpassword));
								userManager.resetUser(user);
								this.dispose();
								System.exit(0);
							}
						}
					} else {
						JOptionPane.showMessageDialog(this, "原密码输入不正确!", "提示", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e1) {
					Constants.logger.error("修改用户密码失败！"+e1.getMessage(), e1);
					JOptionPane.showMessageDialog(this, "修改用户密码失败！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE);
				}

			} else if(response==1) {
				this.editable(false);
				this.dataInit();
				okButton.setText("修改");	
				cancelButton.setEnabled(true); 
			}

		}
		if (e.getActionCommand().equals("关闭")){
			this.dispose();
		}
	}
}

 