package com.kh.jdbc.day03.pstmt.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

import com.kh.jdbc.day03.pstmt.member.model.vo.Member;

public class MemberDAO {

	private String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private String USERNAME = "BENQJDBC";
	private String PASSWORD = "BENQJDBC";
	
	// 1. 회원가입
	public int insertMember(Member member) {
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName(DRIVER_NAME);
			
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			stmt = conn.createStatement();
//			String query = "INSERT INTO MEMBER_TBL VALUES ('"+member.getMemberId()+"', '"
//															+member.getMemberPw()+"', '"
//															+member.getMemberName()+"', '"
//															+member.getGender()+"', "
//															+member.getAge()+", '"
//															+member.getEmail()+"', '"
//															+member.getPhone()+"', '"
//															+member.getAddress()+"', '"
//															+member.getHobby()+"', DEFAULT)";
//			result = stmt.executeUpdate(query);
			
			String query = "INSERT INTO MEMBER_TBL VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
			pstmt = conn.prepareStatement(query);
			// 파라미터의 인덱스, 객체
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 2. 로그인
	public Member selectOne(Member member) {
		Connection conn = null;
//		Statement stmt = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		Member result = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			// Statement : 쿼리문을 그대로 실행. 입력값을 받지 않을 때 주로 사용
//			stmt = conn.createStatement();
//			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '"+member.getMemberId()+"' AND MEMBER_PW = '"+member.getMemberPw()+"'";
//			rset = stmt.executeQuery(query); // 쿼리를 실행할 때마다 쿼리 문자열을 전달
			
			// PreparedStatement : 쿼리문을 이용하여 컴파일을 미리 실행한 후 객체를 생성. 따라서, 쿼리문 작성 먼저 해줘야 함
			// 쿼리문에는 값이 들어가는 자리를 위치홀더로 표시해줘야 함
			// ? : 쿼리에서 파라미터를 나타내며, 나중에 실제 값으로 대체됨
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?";
			pstmt = conn.prepareStatement(query); // SQL 문을 미리 컴파일하고, 실행 시에 변수를 바인딩할 수 있는 기능을 제공
			pstmt.setString(1, member.getMemberId()); // 첫번째 '?'
			pstmt.setString(2, member.getMemberPw()); // 두번째 '?'
			rset = pstmt.executeQuery(); // 미리 준비된 쿼리를 실행. 쿼리 문자열은 객체 생성 시 한 번만 전달
			
			// 후처리 : rsetToMember
			if(rset.next())
				result = rsetToMember(rset);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private Member rsetToMember(ResultSet rset) throws SQLException {
		Member member = new Member();
		String memberId = rset.getString("MEMBER_ID");
		member.setMemberId(memberId);
		member.setMemberPw(rset.getString("MEMBER_PW"));
		member.setMemberName(rset.getString("MEMBER_NAME"));
		member.setGender(rset.getString("GENDER"));
		member.setAge(rset.getInt("AGE"));
		member.setEmail(rset.getString("EMAIL"));
		member.setPhone(rset.getString("PHONE"));
		member.setAddress(rset.getString("ADDRESS"));
		member.setHobby(rset.getString("HOBBY"));
		member.setRegDate(rset.getDate("REG_DATE"));
		return member;
	}

	public int deleteMember(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		int result = 0;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Member selectOne(String memberId) {
		Connection conn = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		Member result = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?";
			pstmt = conn.prepareStatement(query); // SQL 문을 미리 컴파일하고, 실행 시에 변수를 바인딩할 수 있는 기능을 제공
			pstmt.setString(1, memberId); // 첫번째 '?'
			rset = pstmt.executeQuery(); // 미리 준비된 쿼리를 실행. 쿼리 문자열은 객체 생성 시 한 번만 전달
			
			// 후처리 : rsetToMember
			if(rset.next())
				result = rsetToMember(rset);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int registerMember(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String query = "UPDATE MEMBER_TBL SET MEMBER_PW = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE MEMBER_ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberPw());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, member.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}











