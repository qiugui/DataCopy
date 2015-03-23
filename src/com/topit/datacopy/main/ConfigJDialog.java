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
import javax.swing.SwingConstants;

import com.topit.datacopy.config.ConnectConfig;
import com.topit.datacopy.config.Constants;
import com.topit.datacopy.manager.ConfigManager;

public class ConfigJDialog extends JDialog implements ActionListener{
	 
	private static final long serialVersionUID = -7999996733536015771L;
	//“导入数据库配置”组件信息
	private JLabel importLabel;
	private JLabel importIPLabel;
	private JLabel importPortLabel;
	private JLabel importDatabaseLabel;
	private JLabel importUsernameLabel;
	private JLabel importPasswordLabel;
	
	private JTextField importIPText;
	private JTextField importPortText;
	private JTextField importDatabaseText;
	private JTextField importUsernameText;
	private JPasswordField importPassword;
	
	//“导出数据库配置”组件信息
	private JLabel exportLabel;
	private JLabel exportIPLabel;
	private JLabel exportPortLabel;
	private JLabel exportDatabaseLabel;
	private JLabel exportUsernameLabel;
	private JLabel exportPasswordLabel;
	
	private JTextField exportIPText;
	private JTextField exportPortText;
	private JTextField exportDatabaseText;
	private JTextField exportUsernameText;
	private JPasswordField exportPassword;

	private JLabel taskNameLabel;
	private JTextField taskNameText;
	
	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	
	// 设置界面的临时属性变量
	private static ConnectConfig sourceDB_temp;
	private static ConnectConfig targetDB_temp;
	
	 /**   
	 * @Title: createJDialog   
	 * @Description: 创建JDialog          
	 */
	 
	public ConfigJDialog(){
		this.setModal(true);
		 this.setSize(400, 350);
		 this.setTitle("连接参数设置");
		 Dimension dialogSize = this.getSize();
		 this.setLocation((MainJFrame.displaySize.width-dialogSize.width)/2,(MainJFrame.displaySize.height-dialogSize.height)/2);
		 this.setResizable(false);
		 this.initialize();
		 this.dataInit();
		 this.editable(false);
		 this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		 this.setVisible(true);		 
	 }
	 
	 /**   
	 * @Title: init   
	 * @Description: 初始化JDialog的布局           
	 */
	 
	public void initialize(){
		 //初始化组件信息
		importLabel = new JLabel("导入数据库配置");
		importLabel.setHorizontalAlignment(SwingConstants.LEFT);
		importIPLabel = new JLabel("IP地址");
		importIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importPortLabel = new JLabel("端口号");
		importPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importDatabaseLabel = new JLabel("数据库名");
		importDatabaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importUsernameLabel = new JLabel("用户名");
		importUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		importPasswordLabel = new JLabel("密    码");
		importPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		importIPText = new JTextField(10);
		importPortText = new JTextField(10);
		importDatabaseText = new JTextField(10);
		importUsernameText = new JTextField(10);
		importPassword = new JPasswordField(10);

		
		exportLabel = new JLabel("导出数据库配置");
		exportLabel.setHorizontalAlignment(SwingConstants.LEFT);
		exportIPLabel = new JLabel("IP地址");
		exportIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportPortLabel = new JLabel("端口号");
		exportPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportDatabaseLabel = new JLabel("数据库名");
		exportDatabaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportUsernameLabel = new JLabel("用户名");
		exportUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exportPasswordLabel = new JLabel("密    码");
		exportPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		exportIPText = new JTextField(10);
		exportPortText = new JTextField(10);
		exportDatabaseText = new JTextField(10);
		exportUsernameText = new JTextField(10);
		exportPassword = new JPasswordField(10);
		
		taskNameLabel = new JLabel("设置任务名称");
		taskNameText = new JTextField(20);
		
		buttonPanel = new JPanel();
		okButton = new JButton("修改");
		okButton.addActionListener(this);
		cancelButton = new JButton("关闭");
		cancelButton.addActionListener(this);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		//组件信息初始完成
		
		//设置布局
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		this.add(importLabel);
		this.add(exportLabel);
		this.add(importIPLabel);
		this.add(importIPText);
		this.add(exportIPLabel);
		this.add(exportIPText);
		this.add(importPortLabel);
		this.add(importPortText);
		this.add(exportPortLabel);
		this.add(exportPortText);
		this.add(importDatabaseLabel);
		this.add(importDatabaseText);
		this.add(exportDatabaseLabel);
		this.add(exportDatabaseText);
		this.add(importUsernameLabel);
		this.add(importUsernameText);
		this.add(exportUsernameLabel);
		this.add(exportUsernameText);
		this.add(importPasswordLabel);
		this.add(importPassword);
		this.add(exportPasswordLabel);
		this.add(exportPassword);
		this.add(taskNameLabel);
		this.add(taskNameText);
		this.add(buttonPanel);
		
		//定义一个GridBagConstraints，用来控制添加进的组件的显示位置
		GridBagConstraints gbc = new GridBagConstraints();
		//设置组件所在的区域比组件本身要大时的显示情况 
		//BOTH：使组件完全填满其显示区域
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		//设置组件水平所占用的格子数，如果为0，说明该组件是该行的最后一个
		gbc.gridwidth = 2;
		//设置组件水平的拉伸幅度，如果为0，说明不拉伸，不为0，就随着窗口增大进行拉伸（0到1之间）
		gbc.weightx = 0;
		//设置组件垂直的拉伸幅度，如果为0，说明不拉伸，不为0，就随着窗口增大进行拉伸，0到1之间
		gbc.weighty = 0;
		//设置组件
		layout.setConstraints(importLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importIPLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importIPText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportIPLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportIPText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPortLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPortText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportPortLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportPortText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importDatabaseLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importDatabaseText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportDatabaseLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportDatabaseText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importUsernameLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importUsernameText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportUsernameLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportUsernameText, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPasswordLabel, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(importPassword, gbc);
		gbc.gridwidth = 1;
		layout.setConstraints(exportPasswordLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(exportPassword, gbc);
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridwidth = 0;
		layout.setConstraints(taskNameLabel, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(taskNameText, gbc);
		gbc.gridwidth = 0;
		layout.setConstraints(buttonPanel, gbc);
	 }

	/**   
	 * @Title: editable   
	 * @Description: 控制编辑框能否编辑        
	 */
	 
	public void editable(boolean editable){
		importIPText.setEditable(editable);
		importPortText.setEditable(editable);
		importDatabaseText.setEditable(editable);
		importUsernameText.setEditable(editable);
		importPassword.setEditable(editable);
		exportIPText.setEditable(editable);
		exportPortText.setEditable(editable);
		exportDatabaseText.setEditable(editable);
		exportUsernameText.setEditable(editable);
		exportPassword.setEditable(editable);
		taskNameText.setEditable(editable);
	}
	
	public void dataInit(){
		try {
			sourceDB_temp = ConfigManager.getDBConfig(Constants.DBConnection.SOURCEDB);
			targetDB_temp = ConfigManager.getDBConfig(Constants.DBConnection.TARGETDB);
		} catch (Exception e) {
			Constants.logger.error("初始化数据库信息失败！"+e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "初始化数据库信息失败！请联系管理员！", "提示", JOptionPane.ERROR_MESSAGE);
		}

		importIPText.setText(sourceDB_temp.getIp());
		importPortText.setText(sourceDB_temp.getPort());
		importDatabaseText.setText(sourceDB_temp.getDbName());
		importUsernameText.setText(sourceDB_temp.getUser());
		importPassword.setText(sourceDB_temp.getPassword());
		
		exportIPText.setText(targetDB_temp.getIp());
		exportPortText.setText(targetDB_temp.getPort());
		exportDatabaseText.setText(targetDB_temp.getDbName());
		exportUsernameText.setText(targetDB_temp.getUser());
		exportPassword.setText(targetDB_temp.getPassword());
		taskNameText.setText(Constants.taskName);
	}
	

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("修改")){
			this.editable(true);
			this.dataInit();
			okButton.setText("完成");
			cancelButton.setEnabled(false);
		}
		if (e.getActionCommand().equals("完成")){
			Object[] options = {"保存","放弃"};   
			int response=JOptionPane.showOptionDialog(this, "是否保存更改配置？", "提示",JOptionPane.OK_CANCEL_OPTION,   
			JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(response==0) { 
				try {
					if(!importIPText.getText().equals(sourceDB_temp.getIp())){
						ConfigManager.setConfig(Constants.DBConnection.SOURCEDB, Constants.DBConnection.IP, importIPText.getText());
						Constants.logger.warn("源数据库IP地址更改！");
					}
					if(!importPortText.getText().equals(sourceDB_temp.getPort())){
						ConfigManager.setConfig(Constants.DBConnection.SOURCEDB, Constants.DBConnection.PORT, importPortText.getText());
						Constants.logger.warn("源数据库端口号更改！");
					}
					if(!importDatabaseText.getText().equals(sourceDB_temp.getDbName())){
						ConfigManager.setConfig(Constants.DBConnection.SOURCEDB, Constants.DBConnection.DBNAME, importDatabaseText.getText());
						Constants.logger.warn("源数据库更改！");
					}
					if(!importUsernameText.getText().equals(sourceDB_temp.getUser())){
						ConfigManager.setConfig(Constants.DBConnection.SOURCEDB, Constants.DBConnection.USER, importUsernameText.getText());
						Constants.logger.warn("源数据库用户名更改！");
					}
					if(!(new String(importPassword.getPassword())).equals(sourceDB_temp.getPassword())){
						ConfigManager.setConfig(Constants.DBConnection.SOURCEDB, Constants.DBConnection.PASSWORD, new String(importPassword.getPassword()));
						Constants.logger.warn("源数据库密码更改！");
					}
					if(!exportIPText.getText().equals(targetDB_temp.getIp())){
						ConfigManager.setConfig(Constants.DBConnection.TARGETDB, Constants.DBConnection.IP, exportIPText.getText());
						Constants.logger.warn("目标数据库IP地址更改！");
					}
					if(!exportPortText.getText().equals(targetDB_temp.getPort())){
						ConfigManager.setConfig(Constants.DBConnection.TARGETDB, Constants.DBConnection.PORT, exportPortText.getText());
						Constants.logger.warn("目标数据库端口号更改！");
					}
					if(!exportDatabaseText.getText().equals(targetDB_temp.getDbName())){
						ConfigManager.setConfig(Constants.DBConnection.TARGETDB, Constants.DBConnection.DBNAME, exportDatabaseText.getText());
						Constants.logger.warn("目标数据库更改！");
					}
					if(!exportUsernameText.getText().equals(targetDB_temp.getUser())){
						ConfigManager.setConfig(Constants.DBConnection.TARGETDB, Constants.DBConnection.USER, exportUsernameText.getText());
						Constants.logger.warn("目标数据库用户名更改！");
					}
					if(!(new String(exportPassword.getPassword())).equals(targetDB_temp.getPassword())){
						ConfigManager.setConfig(Constants.DBConnection.TARGETDB, Constants.DBConnection.PASSWORD, new String(exportPassword.getPassword()));
						Constants.logger.warn("目标数据库密码更改！");
					}
					
					Constants.taskName=taskNameText.getText();
					MainJFrame.taskNameLabel.setText("当前任务为："+Constants.taskName);
					this.dataInit();
				} catch (Exception e1) {
					Constants.logger.error("修改数据库信息失败！"+e1.getMessage(), e1);
					JOptionPane.showMessageDialog(this, "修改数据库信息失败！请联系管理员！", "提示", JOptionPane.ERROR_MESSAGE);
				}
			} else if(response==1) {
				this.dataInit();
			}
			this.editable(false);
			okButton.setText("修改");	
			cancelButton.setEnabled(true);
		}
		if (e.getActionCommand().equals("关闭")){
			this.dispose();
		}
	}
}

 