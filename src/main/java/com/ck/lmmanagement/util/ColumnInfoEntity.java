package com.ck.lmmanagement.util;

import java.util.List;

/**
 * Title: ColumnInfoEntity.java  
 * Description:  
 * Copyright: Copyright (c) 2018
 * Company: www.sf-express.com 
 * @author Kang Chen  
 * @date 2018年7月26日 下午4:53:35
 * @version 1.0  
 */
public class ColumnInfoEntity {

	/**
	 * 字段
	 */
	private String column;

	/**
	 * 字段描述名
	 */
	private String columnName;

	/**
	 * 子集合
	 */
	private List<ColumnInfoEntity> subColumnList;
	
	public ColumnInfoEntity(){}
	
	public ColumnInfoEntity(String columnName, String column){
		this.columnName = columnName;
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public List<ColumnInfoEntity> getSubColumnList() {
		return subColumnList;
	}

	public void setSubColumnList(List<ColumnInfoEntity> subColumnList) {
		this.subColumnList = subColumnList;
	}
	
	

}
