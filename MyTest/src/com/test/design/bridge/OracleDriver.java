package com.test.design.bridge;

public class OracleDriver implements Driver{
	static{
		System.out.println("执行OracleDriver静态区域块");
		ConnectionManager.register(new OracleDriver());
	}
	@Override
	public Connection getConnection() {
		return new OracleConnection();
	}

}
