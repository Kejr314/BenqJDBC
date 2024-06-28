package com.kh.jdbc.day02.stmt.member.model.dao;

import java.sql.*;
import java.util.*;

import com.kh.jdbc.day02.stmt.member.model.vo.Member;

public class MemberDAO {
	// JDBC 코딩 절차
	// JDBC를 통해 DB의 데이터를 가져옴
	
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERNAME = "BENQJDBC";
	private final String PASSWORD = "BENQJDBC";
	
	public int insertMember(Member member) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String query = "INSERT INTO MEMBER_TBL VALUES('"
					+member.getMemberId()+"', '"
					+member.getMemberPw()+"', '"
					+member.getMemberName()+"', '"
					+member.getGender()+"', "
					+member.getAge()+", '"
					+member.getEmail()+"', '"
					+member.getPhone()+"', '"
					+member.getAddress()+"', '"
					+member.getHobby()+"', DEFAULT)";
			// DML의 경우 성공한 행의 갯수가 리턴, 메소드는 executeUpdate()
			result = stmt.executeUpdate(query);
			
//			if(result > 0) { // 자동 commit 상태
//				// commit
//			} else {
//				// rollback
//			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
					e.printStackTrace();
			} 
		}
		return result;
	}

	// MemberController의 printAllMember 메소드
	public List<Member> selectList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		// DB에서 가져온 값 넘겨줘야 함
		List<Member> mList = null;
		try {
			Class.forName(DRIVER_NAME); // 드라이버 등록
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 연결 생성
			stmt = conn.createStatement(); // 워크시트 열기
			String query = "SELECT * FROM MEMBER_TBL"; // 쿼리문 작성
			// 실행
			rset = stmt.executeQuery(query); // SELECT는 executeQuery(query)
			// 후처리, 여러개 임으로 while 사용, 전부 가져올 때까지 루프
			mList = new ArrayList<Member>();
			while (rset.next()) {
				// rset은 바로 사용하지 못함으로 Member
				Member member = this.rsetToMember(rset);
				// 비어있으면 안됨
				// resultset에서 값을 가져와야 함으로 rset.getString("컬럼명")
				// member에 담고 List에 담아야 함
				mList.add(member);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원해제
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 호출한 곳에서 사용해야 함으로 return mList 
		// 호출한 곳 : MemberController > printAllMember()
		return mList;
	}

	// 반복해서 사용하는 코드임으로 '모듈화' 시킴
	public Member rsetToMember(ResultSet rset) throws SQLException {
		Member member = new Member();
		// resultset에서 값을 가져와야 함으로 rset.getString("컬럼명")
		member.setMemberId(rset.getString("MEMBER_ID"));
		member.setMemberPw(rset.getString("MEMBER_PW"));
		member.setMemberName(rset.getString("MEMBER_NAME"));
		member.setGender(rset.getString("GENDER"));
		member.setAge(rset.getInt("AGE"));
		member.setEmail(rset.getString("EMAIL"));
		member.setPhone(rset.getString("PHONE"));
		member.setAddress(rset.getString("ADDRESS"));
		member.setHobby(rset.getString("HOBBY"));
		// member에 모두 담았고 호출한 곳에서 사용해야 함으로 return member
		return member;
	}
	
	public Member selectOne(String memberId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // SELECT이기 때문에
		// try 안에서 쓴 변수는 return이 되지 않기 때문에 try 밖에서 Member member = null;
		Member member = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
			// SQL에서 작성하면 조회하려는 memberId에 작은 따음표('')로 감싸줌
			// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = 'memberId';
			// 따라서 작은 따음표('')를 작성해줘야 함
			rset = stmt.executeQuery(query);
			if(rset.next()) member = rsetToMember(rset);
			member = rsetToMember(rset);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 호출한 곳에서 사용해야 함으로 return member
		// 호출한 곳 : MemberController > printOneMember() > Member member = mDao.selectOne(memberId);
		return member;
	}

	public int deleteMember(String memberId) {
		// finally에서 close()
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		try {
			// 1. 드라이버 등록, checked Exception임으로 try ~ catch
			Class.forName(DRIVER_NAME);
			// 2.  연결 생성, checked Exception임으로 catch 추가
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false); // 자동 커밋 해체 
			// 3. Statement 생성
			stmt = conn.createStatement();
			// 문자열은 작은따음표(')로 감싸야 함으로
			String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
			// 4. query문 전송 및 5. 결과 받기
			// DML의 결과값은 성공한 행의 갯수니까 int result
			// 쿼리 실행 메소드는 DML이니까 executeUpdate(query);
			result = stmt.executeUpdate(query);
			// query 성공하면 commit, 실패하면 rollback
			if(result > 0) { // commit, 영구 저장
				conn.commit();
			} else { // rollback, 최근 commit 시점으로 이동(원복)
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// try 안에서 쓴 변수는 return 안됨으로 try 밖에서 int result = 0;
		// 호출한 곳에서 사용해야 함으로 return result
		// 호출한 곳 : MemebrController > removeMember() > int result = mDao.deleteMember(memberId);
		return result;
	}

	public int updateMember(Member modifyInfo) {
		// finally에서 close()
		Connection conn = null;
		Statement stmt = null;
		// try
		int result = 0;
		try {
			// 1. 드라이버 등록, checked Exception임으로 try ~ catch
			Class.forName(DRIVER_NAME);
			// 2.  연결 생성, checked Exception임으로 cath 추가
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false); // 자동 커밋 해체 
			// 3. Statement 생성
			stmt = conn.createStatement();
			// 문자열은 작은따음표(')로 감싸야 함으로
			String query = "UPDATE MEMBER_TBL SET MEMBER_PW = ' " + modifyInfo.getMemberPw() 
							+ "', EMAIL = '" + modifyInfo.getEmail() 
							+ "', PHONE = '" + modifyInfo.getPhone() 
							+ "', ADDRESS = '" + modifyInfo.getAddress()
							+ "', HOBBY = '" + modifyInfo.getHobby()
							+ "' WHERE MEMBER_ID = '" + modifyInfo.getMemberId() + "'";
			// 4. query문 전송 및 5. 결과 받기
			// DML의 결과값은 성공한 행의 갯수니까 int result
			// 쿼리 실행 메소드는 DML이니까 executeUpdate(query);
			result = stmt.executeUpdate(query);
			if(result > 0) { // commit, 영구 저장
				conn.commit();
			} else { // rollback, 최근 commit 시점으로 이동(원복)
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}


