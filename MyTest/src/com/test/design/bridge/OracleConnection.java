package com.test.design.bridge;

public class OracleConnection implements Connection{
	public OracleConnection(){
		System.out.println("调用OracleConnection构造函数");
	}
	@Override
	public Object execute() {
		System.out.println("OracleConnection 开始执行...");
		return null;
	}

}
