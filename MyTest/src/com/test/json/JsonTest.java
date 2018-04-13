package com.test.json;

import com.alibaba.fastjson.JSON;

public class JsonTest {
	public static void main(String[] args) {
		Student stu = new Student("110000","2,2","10000");
		String jsonStr = JSON.toJSONString(stu);
		System.out.println(jsonStr);
	}
}
class Student{
	String ebLocationId;
	String hukouTypeId;
	String salary;
	public Student(String ebLocationId,String hukouTypeId,String salary){
		this.ebLocationId = ebLocationId;
		this.hukouTypeId = hukouTypeId;
		this.salary = salary;
	}
	public String getEbLocationId() {
		return ebLocationId;
	}
	public void setEbLocationId(String ebLocationId) {
		this.ebLocationId = ebLocationId;
	}
	public String getHukouTypeId() {
		return hukouTypeId;
	}
	public void setHukouTypeId(String hukouTypeId) {
		this.hukouTypeId = hukouTypeId;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
}