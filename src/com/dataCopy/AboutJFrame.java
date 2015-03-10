package com.dataCopy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutJFrame implements ActionListener{
	
	private JDialog jDialog = new JDialog(Main.frame, "设置", ModalityType.APPLICATION_MODAL);
	
	private JPanel panel;
	private JPanel buttonPanel;
	private JLabel infoLabel;
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
		infoLabel = new JLabel("关于");
		okButton = new JButton("确定");
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
		panel.add(infoLabel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		jDialog.add(panel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("确定")){
			jDialog.dispose();
		}
	}
}
