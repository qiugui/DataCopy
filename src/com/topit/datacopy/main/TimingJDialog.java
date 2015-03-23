 package com.topit.datacopy.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.topit.datacopy.config.AutoTask;
import com.topit.datacopy.config.Constants;
import com.topit.datacopy.manager.AutoTaskManager;
import com.topit.datacopy.manager.ConfigManager;

 public class TimingJDialog extends JDialog implements ActionListener {
			
	private static final long serialVersionUID = -9122550528390445261L;
	//“设置自动运行时间”组件信息
	private JPanel uiPanel;
	private JPanel time1Panel;
	private JPanel time2Panel;
	private JPanel time3Panel;
	
	private JLabel autoRuntimeLabel;
	private JLabel autoRuntime1Label;
	private JLabel autoRuntime2Label;
	private JLabel autoRuntime3Label;
	private JTextField hour1;
	private JTextField minute1;
	
	private JTextField hour2;
	private JTextField minute2;
	
	private JTextField hour3;
	private JTextField minute3;

	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	
	String oldTime1 = "";
	String oldTime2 = "";
	String oldTime3 = "";
	
	public TimingJDialog(){
		this.setModal(true);
		 this.setSize(200, 200);
		 this.setTitle("定时任务设置");
		 Dimension dialogSize = this.getSize();
		 this.setLocation((MainJFrame.displaySize.width-dialogSize.width)/2,(MainJFrame.displaySize.height-dialogSize.height)/2);
		 this.setResizable(false);
		 this.initialize();
		 this.dataInit();
		 this.editable(false);
		 this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		 this.setVisible(true);	
	}
	
	private void editable(boolean editable){
		hour1.setEditable(editable);
		minute1.setEditable(editable);
		hour2.setEditable(editable);
		minute2.setEditable(editable);
		hour3.setEditable(editable);
		minute3.setEditable(editable);
	}
	
	private void dataInit() {
		HashMap<Integer, String> times = null;
		try {
			times = ConfigManager.getAutoTasks();
		} catch (Exception e) {

			 e.printStackTrace();
			 
		}
		for (int i=0;i<times.size();i++){
			String time = times.get(i+1);
			if(!"".equals(time)){
				String[] t = time.split(":");
				if((i+1)==1){
					hour1.setText(t[0]);
					minute1.setText(t[1]);
				} else if ((i+1)==2){
					hour2.setText(t[0]);
					minute2.setText(t[1]);
				} else {
					hour3.setText(t[0]);
					minute3.setText(t[1]);
				}
			}
		}
		oldTime1 = hour1.getText()+":"+minute1.getText();
		oldTime2 = hour2.getText()+":"+minute2.getText();
		oldTime3 = hour3.getText()+":"+minute3.getText();
	}

	public void initialize(){
		//初始化组件信息
		uiPanel = new JPanel(new GridLayout(5, 1));
		time1Panel = new JPanel();
		time2Panel = new JPanel();
		time3Panel = new JPanel();
		
		autoRuntimeLabel = new JLabel("设置自动运行时间");
		autoRuntimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		autoRuntime1Label = new JLabel("任务一");
		hour1 = new JTextField(3);
		hour1.setDocument(new NumberLenghtLimitedDmt(2));
		minute1 = new JTextField(3);
		minute1.setDocument(new NumberLenghtLimitedDmt(2));
		time1Panel.add(autoRuntime1Label);
		time1Panel.add(hour1);
		time1Panel.add(new JLabel("时"));
		time1Panel.add(minute1);
		time1Panel.add(new JLabel("分"));

		autoRuntime2Label = new JLabel("任务二");
		hour2 = new JTextField(3);
		hour2.setDocument(new NumberLenghtLimitedDmt(2));
		minute2 = new JTextField(3);
		minute2.setDocument(new NumberLenghtLimitedDmt(2));
		time2Panel.add(autoRuntime2Label);
		time2Panel.add(hour2);
		time2Panel.add(new JLabel("时"));
		time2Panel.add(minute2);
		time2Panel.add(new JLabel("分"));
		
		autoRuntime3Label = new JLabel("任务三");
		hour3 = new JTextField(3);
		hour3.setDocument(new NumberLenghtLimitedDmt(2));
		minute3 = new JTextField(3);
		minute3.setDocument(new NumberLenghtLimitedDmt(2));
		time3Panel.add(autoRuntime3Label);
		time3Panel.add(hour3);
		time3Panel.add(new JLabel("时"));
		time3Panel.add(minute3);
		time3Panel.add(new JLabel("分"));
		
		buttonPanel = new JPanel();
		okButton = new JButton("修改");
		okButton.addActionListener(this);
		cancelButton = new JButton("关闭");
		cancelButton.addActionListener(this);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		uiPanel.add(autoRuntimeLabel);
		uiPanel.add(time1Panel);
		uiPanel.add(time2Panel);
		uiPanel.add(time3Panel);
		uiPanel.add(buttonPanel);
		
		this.add(uiPanel);
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
		if (e.getActionCommand().equals("修改")){
			//避免因dialog不关闭再次修改，造成oldtime*不改变
			this.dataInit();
			this.editable(true);
			okButton.setText("完成");
			cancelButton.setEnabled(false);
		}
		if (e.getActionCommand().equals("完成")){
			Object[] options = {"保存","放弃"};   
			int response=JOptionPane.showOptionDialog(this, "是否保存更改配置？", "提示",JOptionPane.OK_CANCEL_OPTION,   
			JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(response==0) { 
				String newTime1 = hour1.getText()+":"+minute1.getText();
				String newTime2 = hour2.getText()+":"+minute2.getText();
				String newTime3 = hour3.getText()+":"+minute3.getText();
				//newTime 与 oldTime不同，则表示定时任务被修改
				if (!newTime1.equals(oldTime1)){
					changeAutoTask("任务1",oldTime1,newTime1);
				} 
				if (!newTime2.equals(oldTime2)){
					changeAutoTask("任务2",oldTime2,newTime2);

				}
				if (!newTime3.equals(oldTime3)){
					changeAutoTask("任务3",oldTime3,newTime3);
				} 
				MainJFrame.stateLabel.setText("当前状态：定时任务");
				MainJFrame.evaluateNextTime();
			} else if(response==1) {
				//放弃更改，将值恢复成原先时间
				String[] time1 = oldTime1.split(":");
				String[] time2 = oldTime2.split(":");
				String[] time3 = oldTime3.split(":");
				if (time1.length==0){
					hour1.setText("");
					minute1.setText("");
				} else {
					hour1.setText(time1[0]);
					minute1.setText(time1[1]);
				}
				if (time2.length==0){
					hour2.setText("");
					minute2.setText("");
				} else {
					hour2.setText(time2[0]);
					minute2.setText(time2[1]);
				}
				if (time3.length==0){
					hour3.setText("");
					minute3.setText("");
				} else {
					hour3.setText(time3[0]);
					minute3.setText(time3[1]);
				}
			}
			this.editable(false);
			okButton.setText("修改");	
			cancelButton.setEnabled(true);
		}
		if (e.getActionCommand().equals("关闭")){
			this.dispose();
		}
	}

	private void changeAutoTask(String taskName,String oldTime,String newTime) {
		String targetTag = "";
		String jobName = "";
		if("任务1".equals(taskName)){
			targetTag = Constants.AutoTask.AUTORUNTIME1;
			jobName = "任务1";
		} else if("任务2".equals(taskName)){
			targetTag = Constants.AutoTask.AUTORUNTIME2;
			jobName = "任务2";
		} else {
			targetTag = Constants.AutoTask.AUTORUNTIME3;
			jobName = "任务3";
		}

		if (":".equals(oldTime)){
			try {
				Constants.logger.warn("新增定时任务："+jobName+"！");
				ConfigManager.setConfig(Constants.AutoTask.AUTORUNTIMES,targetTag,newTime);
				AutoTaskManager.addJob(jobName, AutoTask.class, newTime);
			} catch (Exception e1) {
				Constants.logger.error("新增定时任务失败！"+e1.getMessage(), e1);
				JOptionPane.showMessageDialog(this, "新增定时任务失败！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE);
			}
		} else if(":".equals(newTime)){
			try {
				Constants.logger.warn("移除定时任务："+jobName+"！");
				ConfigManager.setConfig(Constants.AutoTask.AUTORUNTIMES,targetTag,null);
				AutoTaskManager.removeJob(jobName);
			} catch (Exception e1) {
				Constants.logger.error("移除定时任务失败！"+e1.getMessage(), e1);
				JOptionPane.showMessageDialog(this, "移除定时任务失败！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			try {
				Constants.logger.warn("修改定时任务："+jobName+"！");
				ConfigManager.setConfig(Constants.AutoTask.AUTORUNTIMES,targetTag,newTime);
				AutoTaskManager.modifyJobTime(jobName, newTime);
			} catch (Exception e1) {
				Constants.logger.error("修改定时任务失败！"+e1.getMessage(), e1);
				JOptionPane.showMessageDialog(this, "修改定时任务失败！请联系管理员！", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}

 