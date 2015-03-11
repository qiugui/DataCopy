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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

 public class TimingJFrame implements ActionListener,CaretListener {
	
	 private JDialog jDialog = new JDialog(Main.frame, "设置定时任务", ModalityType.APPLICATION_MODAL);
		
	//“设置自动运行时间”组件信息
	private JPanel uiPanel;
	private JPanel time1Panel;
	private JPanel time2Panel;
	private JPanel time3Panel;
	
	private JLabel autoRuntimeLabel;
	private JTextField hour1;
	private JTextField minute1;
	
	private JTextField hour2;
	private JTextField minute2;
	
	private JTextField hour3;
	private JTextField minute3;

	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
		
	public TimingJFrame(){
		 jDialog.setSize(200, 200);
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
		hour1.setDocument(new NumberLenghtLimitedDmt(2));
		hour1.setText("00");
		hour1.addCaretListener(this);
		minute1 = new JTextField(3);
		minute1.setDocument(new NumberLenghtLimitedDmt(2));
		minute1.setText("00");
		minute1.addCaretListener(this);
		time1Panel.add(hour1);
		time1Panel.add(new JLabel("时"));
		time1Panel.add(minute1);
		time1Panel.add(new JLabel("分"));

		
		hour2 = new JTextField(3);
		hour2.setDocument(new NumberLenghtLimitedDmt(2));
		hour2.setText("00");
		hour2.addCaretListener(this);
		minute2 = new JTextField(3);
		minute2.setDocument(new NumberLenghtLimitedDmt(2));
		minute2.setText("00");
		minute2.addCaretListener(this);
		time2Panel.add(hour2);
		time2Panel.add(new JLabel("时"));
		time2Panel.add(minute2);
		time2Panel.add(new JLabel("分"));
		
		hour3 = new JTextField(3);
		hour3.setDocument(new NumberLenghtLimitedDmt(2));
		hour3.setText("00");
		hour3.addCaretListener(this);
		minute3 = new JTextField(3);
		minute3.setDocument(new NumberLenghtLimitedDmt(2));
		minute3.setText("00");
		minute3.addCaretListener(this);
		time3Panel.add(hour3);
		time3Panel.add(new JLabel("时"));
		time3Panel.add(minute3);
		time3Panel.add(new JLabel("分"));
		
		buttonPanel = new JPanel();
		okButton = new JButton("确定");
		okButton.addActionListener(this);
		cancelButton = new JButton("取消");
		cancelButton.addActionListener(this);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		uiPanel.add(autoRuntimeLabel);
		uiPanel.add(time1Panel);
		uiPanel.add(time2Panel);
		uiPanel.add(time3Panel);
		uiPanel.add(buttonPanel);
		
		jDialog.add(uiPanel);
	}

	//实现PlainDocument，使得文本框只能输入指定位数的数字
	public class NumberLenghtLimitedDmt extends PlainDocument {
		
		private static final long serialVersionUID = 1L;
		
		private int limit;
		
	   public NumberLenghtLimitedDmt(int limit) {
		   super();
	       this.limit = limit;
	   }
	   
	   public void insertString(int offset, String  str, 
			   AttributeSet attr) throws BadLocationException {   
	       if (str == null){
	        return;
	       }
	       if ((getLength() + str.length()) <= limit) {
	       
		       char[] upper = str.toCharArray();
		       int length=0;
		       for (int i = 0; i < upper.length; i++) {       
		           if (upper[i]>='0'&&upper[i]<='9'){           
		              upper[length++] = upper[i];
		           }
		       }
		         super.insertString(offset, new String(upper,0,length), attr);
	      }
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
		if (e.getSource().equals(hour1)){
			System.out.println("hour1被改变");
		}
		if (e.getSource().equals(minute1)){
			System.out.println("minute1被改变");
		}
		if (e.getSource().equals(hour2)){
			System.out.println("hour2被改变");
		}
		if (e.getSource().equals(minute2)){
			System.out.println("minute2被改变");
		}
		if (e.getSource().equals(hour3)){
			System.out.println("hour3被改变");
		}
		if (e.getSource().equals(minute3)){
			System.out.println("minute3被改变");
		}
	}
}

 