package com.kh.jdbc.day02.stmt.member.controller;

import java.util.List;

import com.kh.jdbc.day02.stmt.member.model.dao.MemberDAO;
import com.kh.jdbc.day02.stmt.member.model.vo.Member;

public class MemberController {
	
	MemberDAO mDao;

	public MemberController() {
		mDao = new MemberDAO();
	}
	
	public void insertMember(Member member) {
		mDao.insertMember(member);
	}

	public List<Member> printAllMember() {
		List<Member> mList = mDao.selectList();
		// 호출한 곳에서 사용해야 함으로 return mList;
		// 호출한 곳 : MemberView > startProgram() > case2
		return mList;
	}
	
	public Member printOneMember(String memberId) {
		// 한 개임으로 List X
		// DAO로 전달해야 함으로
		Member member = mDao.selectOne(memberId);
		// 호출한 곳에서 사용해야 함으로, return member
		// 호출한 곳 : MemberView > startProgram() > member = mController.printOneMember(memberId);
		return member;
	}

	// View에서 memberId값 받아야 함으로 removeMember(String memberId)
	// return하는 값의 자료형 = int -> void가 아니라 int
	public int removeMember(String memberId) {
		// DML의 결과는 int -> int result
		// memberId 전달해야함으로 deletMemebr(memberId)
		int result = mDao.deleteMember(memberId);
		// 호출한 곳에서 사용해야 함으로 return result
		// 호출한 곳 : MemberView > startProgram() > int result = mController.removeMember(memberId);
		return result;
	}

	public int modifyMember(Member modifyInfo) {
		// DML의 결과는 int -> int result
		// modifyInfo 전달해야 함으로 updateMember(modifyInfo)
		int result = mDao.updateMember(modifyInfo);
		// 호출한 곳에서 사용해야 함으로 return result
		// 호출한 곳 : int result = mController.modifyMember(modifyInfo);
		return result;
	}

}
