package com.rootmind.helper;

import com.rootmind.wrapper.AbstractWrapper;

public class PopoverWrapper extends AbstractWrapper implements Comparable<PopoverWrapper>  {
	
	
	public String code=null;
	public String desc=null;
	public String tableName=null;
	public String filter=null;
	public String tableFilter=null;
	public Boolean recordFound=false;
	
	
	public PopoverWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PopoverWrapper(String code, String desc, String tableName,
			String filter, Boolean recordFound) {
		super();
		this.code = code;
		this.desc = desc;
		this.tableName = tableName;
		this.filter = filter;
		this.recordFound = recordFound;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}


	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}


	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}


	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}


	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}


	/**
	 * @return the recordFound
	 */
	public Boolean getRecordFound() {
		return recordFound;
	}


	/**
	 * @param recordFound the recordFound to set
	 */
	public void setRecordFound(Boolean recordFound) {
		this.recordFound = recordFound;
	}

	@Override
	public int compareTo(PopoverWrapper o) {
		// TODO Auto-generated method stub
		return this.code.toLowerCase().compareTo(o.getCode().toLowerCase());
	}

}
