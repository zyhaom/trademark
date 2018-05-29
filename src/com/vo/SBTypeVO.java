package com.vo;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 *SELECT CONCAT ('private ',
               CASE
                WHEN data_type = 'varchar' THEN 'String '
                WHEN data_type = 'timestamp' THEN 'String '
                WHEN data_type = 'date' THEN 'String '
                WHEN data_type = 'bigint' THEN 'long '
                WHEN data_type = 'float' THEN 'float '
                WHEN data_type = 'int' THEN 'int '
                ELSE ''
               END,
               LOWER(SUBSTRING(column_name, 1, 1)),
               SUBSTRING(column_name, 2, LENGTH (column_name)),
               CASE
                WHEN data_type = 'varchar' THEN ' = null ;'
                WHEN data_type = 'timestamp' THEN ' = null ;'
                WHEN data_type = 'date' THEN ' = null ;'
                WHEN data_type = 'bigint' THEN ' = 0 ;'
                WHEN data_type = 'float' THEN ' = 0 ;'
                WHEN data_type = 'int' THEN ' = 0 ;'
                ELSE ''
               END,
               '//',
               column_comment) aaa
  FROM information_schema.columns
 WHERE table_schema = 'pic_search' AND table_name = 't_sbtype'
 */
public class SBTypeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String typeID = null ;//图片类型id
	private String dependTypeID = null ;//依赖的类型ID
	private String typeName = null ;//图片类别名称
	private String typeFolder = null ;//图片所在目录的全路径
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public String getDependTypeID() {
		return dependTypeID;
	}
	public void setDependTypeID(String dependTypeID) {
		this.dependTypeID = dependTypeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeFolder() {
		return typeFolder;
	}
	public void setTypeFolder(String typeFolder) {
		this.typeFolder = typeFolder;
	}
	

}
