 package com.topit.datacopy.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.User;
import com.topit.datacopy.manager.UserManager;
import com.topit.datacopy.utils.MD5Encrypt;

 public class LoginJFrame extends JFrame implements ActionListener{
	 
	private static final long serialVersionUID = -8610312612092546270L;

	 public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	
	 private JPanel jPanel;
	 
	 private Mypanel imagePanel;
	 private JLabel loginLabel;
	 public static JTextField loginTextField;
	 private JLabel passwordLabel;
	 public static JPasswordField passwordField;
	 private JButton okButton;
	 
	 public static void main(String[] args) {
		new LoginJFrame();
	}
	 
	 public LoginJFrame() {
		 
		this.setTitle("登陆");
		this.setSize(300, 175);
		Dimension jFrameSize = this.getSize();
		if (jFrameSize.width > displaySize.width)  
			jFrameSize.width = displaySize.width;
		this.setLocation((displaySize.width-jFrameSize.width)/2,(displaySize.height-jFrameSize.height)/2);
		this.setResizable(false);
		this.initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void initialize() {
		//初始化组件
		 jPanel = new JPanel();
		 imagePanel = new Mypanel();
		 loginLabel = new JLabel("用户名");
		 loginTextField = new JTextField(10);
		 passwordLabel = new JLabel("密    码");
		 passwordField = new JPasswordField(10);
		 okButton = new JButton("登陆");
		 okButton.addActionListener(this);
		 
		 GridBagLayout layout = new GridBagLayout();
		 jPanel.setLayout(layout);
		 
		 jPanel.add(imagePanel);
		 jPanel.add(loginLabel);
		 jPanel.add(loginTextField);
		 jPanel.add(passwordLabel);
		 jPanel.add(passwordField);
		 jPanel.add(okButton);
		 
		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.insets = new Insets(5, 5, 5, 5);
		 gbc.gridwidth = 3;
		 gbc.gridheight = 4;
		 gbc.weightx = 0.1;
		 gbc.weighty = 0;
		 layout.setConstraints(imagePanel, gbc);
		 
		 gbc.weightx =0;
		 gbc.weighty = 0;
		 gbc.gridheight = 1;
		 gbc.gridwidth = 1;
		 layout.setConstraints(loginLabel, gbc);
		 gbc.gridwidth = 0;
		 layout.setConstraints(loginTextField, gbc);
		 gbc.gridwidth = 1;
		 layout.setConstraints(passwordLabel, gbc);
		 gbc.gridwidth = 0;
		 layout.setConstraints(passwordField, gbc);
		 gbc.gridwidth = 0;
		 layout.setConstraints(okButton, gbc);
		 
		 this.add(new JPanel(),BorderLayout.WEST);
		 this.add(jPanel,BorderLayout.CENTER);
		 this.add(new JPanel(),BorderLayout.EAST);
	}
	
	class Mypanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paint (Graphics g){
			super.paint(g);
			Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/admin.gif"));
			g.drawImage(image, 0, 0,90,90, this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("登陆")){
			String username = loginTextField.getText();
			String password = new String(passwordField.getPassword());
			if ("".equals(username) || "".equals(password)){
				JOptionPane.showMessageDialog(this, "用户名或密码不能为空!", "提示", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					User user = new User(username, MD5Encrypt.encipher(password));
					UserManager userManager = new UserManager();
					if(userManager.exist(user)){
						try {
							InetAddress address = InetAddress.getLocalHost();
							String ip = address.getHostAddress().toString();
							String hostname = address.getHostName().toString();
							Constants.logger.info("\n登陆IP地址："+ip+"\n登陆主机名："+hostname+" "+"\n登陆用户名："+user.getName());
							new MainJFrame();
							Constants.loginUsername = username;
							this.dispose();
						} catch (UnknownHostException exception) {
							Constants.logger.warn("用户IP信息未获得！"+exception.getMessage(),exception);
							JOptionPane.showMessageDialog(this, "未获得本机IP地址", "提示", JOptionPane.WARNING_MESSAGE);
							new MainJFrame();
							Constants.loginUsername = username;
							this.dispose();
						}
						
					} else {
						JOptionPane.showMessageDialog(this, "用户名或密码错误!", "提示", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					Constants.logger.error("登陆出现错误！"+e1.getMessage(),e1);
					JOptionPane.showMessageDialog(this, "登陆出现错误！请联系管理员！", "提示", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		} 
	}	
}

 