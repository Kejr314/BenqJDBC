package com.kh.jdbc.day03.pstmt.member.controller;

import com.kh.jdbc.day03.pstmt.member.model.dao.MemberDAO;
import com.kh.jdbc.day03.pstmt.member.model.vo.Member;

public class MemberController {
	
	MemberDAO mDao;
	
	public MemberController() {
		mDao = new MemberDAO();
	}
	
	// 회원가입
	public int insertMember(Member member) {
		int result = mDao.insertMember(member);
		return result;
	}
	// 로그인
	public Member checkLogin(Member member) {
		Member result = mDao.selectOne(member);
		return result;
	}

	// 삭제
	public int removeMember(String memberId) {
		int result = mDao.deleteMember(memberId);
		return result;
	}

	// 회원 유무
	public Member checkMember(String memberId) {
		Member result = mDao.selectOne(memberId);
		return result;
	}

	// 수정
	public int updateMember(Member member) {
		int result = mDao.registerMember(member);
		return result;
	}

}
