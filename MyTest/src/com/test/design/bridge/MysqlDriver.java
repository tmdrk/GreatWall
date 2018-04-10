package com.test.design.bridge;

public class MysqlDriver implements Driver{
	static{
		System.out.println("执行MysqlDriver静态区域块");
		ConnectionManager.register(new MysqlDriver());
	}
	@Override
	public Connection getConnection() {
		return new MysqlConnection();
	}

}
