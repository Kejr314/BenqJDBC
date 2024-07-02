package com.kh.jdbc.day04.pstmt.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTemplate_Old { // 연결 역할

	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "BENQJDBC";
	private static final String PASSWORD = "BENQJDBC";
	
	private static Connection conn;
	
	// singleton
	// 메소드 static
	// getConnection() : 데이터베이스와의 연결을 반환
	// 메소드를 호출하는 코드가 ClassNotFoundException, SQLException 예외들을 처리해야 한다는 것 = ClassNotFoundException, SQLException 던진다.
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(conn == null || conn.isClosed()) { // conn이 초기화되지 않았거나 이미 닫혀 있을 경우
			Class.forName(DRIVER_NAME); 
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
		}
		return conn;
	}
}
