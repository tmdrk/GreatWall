package com.test.common.util.log;

public class LogVo {
	private String fieldName;
	private String oldValue;
	private String newValue;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	@Override
	public String toString() {
		return "LogVO [fieldName=" + fieldName + ", oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}
	
}
