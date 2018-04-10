package com.test.design.bridge;

public class MysqlConnection implements Connection{
	public MysqlConnection(){
		System.out.println("调用MysqlConnection构造函数");
	}

	@Override
	public Object execute() {
		System.out.println("MysqlConnection 开始执行...");
		return null;
	}
}
