package com.kh.jdbc.day04.pstmt.common;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate { // 연결 역할
/*	
	private static String driverName;
	private static String url;
	private static String user;
	private static String password;
*/	
	// "resources/dev.properties : 데이터베이스 연결에 필요한 설정 정보가 있는 프로퍼티 파일
	private static final String FILE_NAME = "resources/dev.properties";
	private static Properties prop;
	private static Connection conn;
	
/*
	public JDBCTemplate() {
		try {
			prop = new Properties();
			prop.load(new FileReader("resources/dev.properties"));
			driverName = prop.getProperty("driverName");
			url = prop.getProperty(url);
			user = prop.getProperty(user);
			password = prop.getProperty(password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
		// ClassNotFoundException: JDBC 드라이버 클래스를 찾지 못할 때 발생
		// SQLException: 데이터베이스와의 연결에 실패했을 때 발생
		// IOException: 프로퍼티 파일을 읽는 데 실패했을 때 발생
		
		prop = new Properties(); // 객체 초키화
		Reader reader = new FileReader(FILE_NAME); // 객체 생성 > 프로퍼티 파일 읽기
		prop.load(reader); // 객체에 프로퍼티 파일의 내용 로드
		
		// 프로퍼티 파일에서 데이터베이스 연결에 필요한 정보를 읽어옴
		String driverName 	= prop.getProperty("driverName");
		String url 			= prop.getProperty("url");
		String user 		= prop.getProperty("user");
		String password 	= prop.getProperty("password");
		
		// conn 객체가 null 이거나 닫혀있는 경우에 새로운 연결 생성 -> 연결이 여러번 생성되는 것을 방지할 수 있음
		if(conn == null || conn.isClosed()) { 
			Class.forName(driverName); // 드라이버 클래스를 로드하는 과정에서 ClassNotFoundException이 발생할 수 있음
			conn = DriverManager.getConnection(url, user, password); // 데이터베이스와 연결하는 과정에서 SQLException이 발생할 수 있음
		}
		return conn;
	}
}
