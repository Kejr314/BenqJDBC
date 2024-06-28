package com.kh.jdbc.day01.basic;

import java.sql.*;

public class JDBCRun {

	public static void main(String[] args) {
/*
	 < JDBC 코딩 절차 >
	 1. 드라이버 등록 - jar
	 2. DBMS 연결 생성 - KH/KH 확인
	 3. Statement 객체 생성 - 워크시트, 커리문 실행준비
	 4. SQL 전송 - 실행(CTRL+ ENTER)
	 5. 결과받기 - ResultSet
	 6. 자원해제
*/
		try {
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. DBMS 연결 생성
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KH", "KH"); // @호스트 이름:포트:SID, 사용자 이름, 비밀번호 in SQL에서 계정 정보
			// 3. Statement 객체 생성
			Statement stmt = conn.createStatement();
//			String query = "SELECT * FROM EMPLOYEE";
			String query = "SELECT * FROM DEPARTMENT";
			// 4. SQL 전송, 5. 결과받기
			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			while(rset.next()) { // 다음값이 있는지 체크
//				System.out.println("직원명 : " + rset.getString("EMP_NAME")); // getString("컬럼명")
			    System.out.println("부서명 : " + rset.getString("DEPT_TITLE"));
			};
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
			// 라이브러리 추가 안해주면 오류 발생함
			// Project > Properties > Java Build Path > Libraries > Modulepath 선택 후 [Add External JARs...] 버튼 클릭 > "ojdbc8.jar" 파일 선택 > [Apply and Close]
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

}
