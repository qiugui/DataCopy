package com.dataCopy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AboutJFrame implements ActionListener{
	
	private JDialog jDialog = new JDialog(Main.frame, "设置", ModalityType.APPLICATION_MODAL);
	
	private JPanel panel;
	private JPanel buttonPanel;
	private JTextArea aboutTextArea;
	private JButton okButton;
	
	public AboutJFrame(){
		 jDialog.setSize(200, 200);
		 Dimension dialogSize = jDialog.getSize();
		 jDialog.setLocation((Main.displaySize.width-dialogSize.width)/2,(Main.displaySize.height-dialogSize.height)/2);
		 jDialog.setResizable(false);
		 this.initialize();
		 jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		 jDialog.setVisible(true);	
	}
	
	public void initialize(){
		panel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel();
		aboutTextArea = new JTextArea();
		aboutTextArea.setLineWrap(true);
		aboutTextArea.setEditable(false);
		aboutTextArea.setText("\t关于\n");
		aboutTextArea.append("\n    Version Beta1.0\n");
		aboutTextArea.append("\n    功能：将更新过的数据进行迁移\n");
		aboutTextArea.append("\n    日期：2015年03月10日");
		
		okButton = new JButton("确定");
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
		panel.add(aboutTextArea, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		jDialog.add(panel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("确定")){
			jDialog.dispose();
		}
	}
}
