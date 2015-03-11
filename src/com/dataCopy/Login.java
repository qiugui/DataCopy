 package com.dataCopy;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 public class Login implements ActionListener{
	 
	 private static JFrame jFrame;
	 public static final Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
	 
	 private Mypanel imagePanel;
	 private JLabel loginLabel;
	 private JTextField loginTextField;
	 private JLabel passwordLabel;
	 private JPasswordField passwordField;
	 private JCheckBox keeppasswordCheckBox;
	 private JButton okButton;
	 
	 public static void main(String[] args) {
		new Login();
	}
	 
	 public Login() {
		jFrame = new JFrame("登陆");
		jFrame.setSize(275, 200);
		Dimension jFrameSize = jFrame.getSize();
		if (jFrameSize.width > displaySize.width)  
			jFrameSize.width = displaySize.width;
		jFrame.setLocation((displaySize.width-jFrameSize.width)/2,(displaySize.height-jFrameSize.height)/2);
		jFrame.setResizable(false);
		this.initialize();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

	}

	private void initialize() {
		//初始化组件
		 imagePanel = new Mypanel();
		 loginLabel = new JLabel("用户名");
		 loginTextField = new JTextField(10);
		 passwordLabel = new JLabel("密  码");
		 passwordField = new JPasswordField(10);
		 keeppasswordCheckBox = new JCheckBox("记住密码");
		 okButton = new JButton("登陆");
		 okButton.addActionListener(this);
		 
		 GridBagLayout layout = new GridBagLayout();
		 jFrame.setLayout(layout);
		 
		 jFrame.add(imagePanel);
		 jFrame.add(loginLabel);
		 jFrame.add(loginTextField);
		 jFrame.add(passwordLabel);
		 jFrame.add(passwordField);
		 jFrame.add(keeppasswordCheckBox);
		 jFrame.add(okButton);
		 
		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.fill = GridBagConstraints.BOTH;
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
		 layout.setConstraints(keeppasswordCheckBox, gbc);
		 gbc.gridwidth = 0;
		 layout.setConstraints(okButton, gbc);
	}
	
	class Mypanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paint (Graphics g){
			super.paint(g);
			Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/admin.gif"));
			g.drawImage(image, 0, 0,100,100, this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("登陆")){
			jFrame.dispose();
			Main main = new Main();
			Thread thread = new Thread(main);
			thread.start();
		} 
	}
	
	
}

 