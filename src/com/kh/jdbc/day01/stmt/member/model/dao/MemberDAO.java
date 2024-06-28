package com.kh.jdbc.day01.stmt.member.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.kh.jdbc.day01.stmt.member.model.vo.Member;

public class MemberDAO {
	// JDBC를 이용하여 Oracle DB에 접속하는 클래스
	// JDBC 코딩이 있어야 함
	
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERNAME = "BENQJDBC";
	private final String PASSWORD = "BENQJDBC";
	
	public Member selectOne(String memberId) {
		Member member = null;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DBMS 연결 생성
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 3. Statement 생성
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 전송, 5. 결과값 받기
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			if(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setMemberPw(rset.getString("MEMBER_PW"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setGender(rset.getString("GENDER"));
				member.setAge(rset.getInt("AGE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setRegDate(rset.getDate("REG_DATE"));
			}
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	public List<Member> selectList() {
		// 1. 왜 mList에 member가 들어가나요?
		// 2. rset은 mList에 못들어가나요?
		// 3. rset을 member로 어떻게 바꾸나요?
		// 3.1 Member 클래스에는 필드와 getter/setter 필요
		// 3.2 ResultSet의 getString(0, getInt(), getDate() 필요
		List<Member> mList = new ArrayList<Member>();
		try {
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. DBMS 연결 생성
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 3. Statement 생성
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 전송, 5. 결과값 받기
			String query = "SELECT * FROM MEMBER_TBL";
			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			while(rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setMemberPw(rset.getString("MEMBER_PW"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setGender(rset.getString("GENDER"));
				member.setAge(rset.getInt("AGE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setRegDate(rset.getDate("REG_DATE"));
				mList.add(member);
//				System.out.println("이름 : " + rset.getString("MEMBER_NAME"));
//				System.out.println("아이디 : " + rset.getString("MEMBER_ID"));
//				System.out.println("이메일 : " + rset.getString("EMAIL"));
//				System.out.println("나이 : " + rset.getString("AGE"));
//				System.out.println("등록일 : " + rset.getString("REG_DATE"));
			}
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mList;
	}
	
	public void insertMember(Member member) {
		try {
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. DBMS 연결 생성
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 3. Statement 생성
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 전송, 5. 결과값 받기
			String query = "INSERT INTO MEMBER_TBL \r\n"
							+ "VALUES('"
							+member.getMemberId()+"', '"
							+member.getMemberPw()+"', '"
							+member.getMemberName()+"', '"
							+member.getGender()+"', '"
							+member.getAge()+"', '"
							+member.getEmail()+"', '"
							+member.getPhone()+"', '"
							+member.getAddress()+"', '"
							+member.getHobby()+"', DEFAULT)";
//			ResultSet rset = stmt.executeQuery(query); // SELECT 할 때만 사용, ResultSet은 SELECT의 결과
			int result = stmt.executeUpdate(query); // DML 호출하는 메소드
			// 후처리
			if(result > 0) {
				// 성공 메시지 출력
				System.out.println("데이터 등록 성공");
				// commit;
			} else
			{
				// 실패 메시지 출력
				System.out.println("데이터 등록 실패");
				// rollback;
			}
			// 6. 자원해제
			conn.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
