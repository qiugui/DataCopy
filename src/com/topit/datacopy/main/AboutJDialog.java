package com.topit.datacopy.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutJDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = -1442882899717290247L;

	
	private JPanel panel;
	private JPanel buttonPanel;
	private JScrollPane aboutScrollPane;
	private JTextArea aboutTextArea;
	private JButton okButton;
	
	public AboutJDialog(){
		this.setModal(true);
		this.setTitle("关于");
		 this.setSize(300, 200);
		 Dimension dialogSize = this.getSize();
		 this.setLocation((MainJFrame.displaySize.width-dialogSize.width)/2,(MainJFrame.displaySize.height-dialogSize.height)/2);
		 this.setResizable(false);
		 this.initialize();
		 this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		 this.setVisible(true);	
	}
	
	public void initialize(){
		panel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel();
		aboutTextArea = new JTextArea();
		aboutTextArea.setLineWrap(true);
		aboutTextArea.setEditable(false);
		aboutTextArea.setText("\t        关于");
		aboutTextArea.append("\n    Version Beta1.0");
		aboutTextArea.append("\n    功能：将更新过的数据进行迁移");
		aboutTextArea.append("\n    日期：2015年03月10日");
		aboutTextArea.append("\n    联系我们");
		aboutTextArea.append("\n      裘韡海：qiu.wh@topsports.com.cn");
		aboutTextArea.append("\n      邱    桂：qiu.g@topsports.com.cn");
		aboutTextArea.append("\n      高达川：gao.dc@topsports.com.cn");
		aboutScrollPane = new JScrollPane(aboutTextArea);
		okButton = new JButton("确定");
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
		panel.add(aboutScrollPane, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(panel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("确定")){
			this.dispose();
		}
	}
}
