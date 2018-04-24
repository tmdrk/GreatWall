package com.test.json;

import com.alibaba.fastjson.JSON;
import com.test.entity.User;

public class JsonTest {
	public static void main(String[] args) {
		Student stu = new Student("110000","2,2","10000");
		String jsonStr = JSON.toJSONString(stu);
		System.out.println(jsonStr);
//		jsonObject = JSON.parseObject(params, OrderAllforGet.class);
		String param = "{\"userId\":1,\"userName\":\"huhu\"}";
		User u = JSON.parseObject(param, User.class);
		System.out.println(u);
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