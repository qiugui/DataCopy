 package com.dataCopy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

 public class TimingJFrame implements ActionListener {
	
	 private JDialog jDialog = new JDialog(Main.frame, "设置定时任务", ModalityType.APPLICATION_MODAL);
		
	//“设置自动运行时间”组件信息
	private JPanel uiPanel;
	private JPanel buttonPanel;
	private JPanel time1Panel;
	private JPanel time2Panel;
	private JPanel time3Panel;
	
	private JLabel autoRuntimeLabel;
	private JTextField hour1;
	private JTextField minute1;
	private JTextField second1;
	
	private JTextField hour2;
	private JTextField minute2;
	private JTextField second2;
	
	private JTextField hour3;
	private JTextField minute3;
	private JTextField second3;

	private JButton okButton;
		
	public TimingJFrame(){
		 jDialog.setSize(200, 175);
		 Dimension dialogSize = jDialog.getSize();
		 jDialog.setLocation((Main.displaySize.width-dialogSize.width)/2,(Main.displaySize.height-dialogSize.height)/2);
		 jDialog.setResizable(false);
		 this.initialize();
		 jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		 jDialog.setVisible(true);	
	}
	
	public void initialize(){
		//初始化组件信息
		uiPanel = new JPanel(new GridLayout(5, 1));
		time1Panel = new JPanel();
		time2Panel = new JPanel();
		time3Panel = new JPanel();
		
		autoRuntimeLabel = new JLabel("设置自动运行时间");
		autoRuntimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
		uiPanel.add(autoRuntimeLabel);
		uiPanel.add(time1Panel);
		uiPanel.add(time2Panel);
		uiPanel.add(time3Panel);
		uiPanel.add(buttonPanel);
		
		jDialog.add(uiPanel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("确定")){
			jDialog.dispose();
		}
	}
}

 