 package com.topit.datacopy.config;
 
 /** 
* @ClassName: UpdatedInfo 
* @Description: 更新的记录对应的对象
* @author qiugui 
* @date 2015年3月16日 下午9:14:09 
*  
*/ 
public class UpdatedInfo {
	private String tablename;
	private String primarykey;
	private String primarykeyvalue;
	private String column;
	private String columntype;
	private String operation;
	private String oldvalue;
	private String newvalue;
	
	public UpdatedInfo() {
		super();
		 
	}
	public UpdatedInfo(String tablename, String primarykey,
			String primarykeyvalue, String column, String columntype,
			String operation, String oldvalue, String newvalue) {
		super();
		this.tablename = tablename;
		this.primarykey = primarykey;
		this.primarykeyvalue = primarykeyvalue;
		this.column = column;
		this.columntype = columntype;
		this.operation = operation;
		this.oldvalue = oldvalue;
		this.newvalue = newvalue;
	}
	
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getPrimarykey() {
		return primarykey;
	}
	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}
	public String getPrimarykeyvalue() {
		return primarykeyvalue;
	}
	public void setPrimarykeyvalue(String primarykeyvalue) {
		this.primarykeyvalue = primarykeyvalue;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getColumntype() {
		return columntype;
	}
	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public String getNewvalue() {
		return newvalue;
	}
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}
	
}

 