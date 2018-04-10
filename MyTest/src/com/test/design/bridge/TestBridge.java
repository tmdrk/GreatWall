package com.test.design.bridge;

public class TestBridge {
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.test.design.bridge.MysqlDriver");
		Connection con = ConnectionManager.getConnection();
		con.execute();
		
		Class.forName("com.test.design.bridge.OracleDriver");
		Connection con1 = ConnectionManager.getConnection();
		con1.execute();
		
		Class.forName("com.test.design.bridge.MysqlDriver");
		Connection con2 = ConnectionManager.getConnection();
		con2.execute();
	}
}
