package com.kh.jdbc.day01.stmt.member.view;

import java.util.*;

import com.kh.jdbc.day01.stmt.member.controller.*;
import com.kh.jdbc.day01.stmt.member.model.vo.*;

public class MemberView {
	
	MemberController mController;
	
	public MemberView() {
		mController = new MemberController();
	}
	
	public void startProgram() {
//		mController.insertMember();
//		List<Member> mList = mController.listMember();
//		this.displayMemberList(mList);
		
		int choice = 0;
		끝 :
		while(true) {
			choice = this.printMainMenu();
			switch (choice) {
			case 0: break 끝;
			case 1: // 회원가입
				Member member = this.inputMember();
				mController.insertMember(member);
				break;
			case 2: // 회원 전체조회
				List<Member> mList = mController.listMember();
				this.displayMemberList(mList);
				break;
			case 3: // 회원 검색(아이디로 조회)
				String memberId = this.inputMemberId();
				member = mController.printOneMember(memberId);
				this.displayOne(member);
				break;
			default: this.displayMessage("메뉴를 다시 선택해주세요.");
			}
		}
		
	}
	
	private String inputMemberId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		return memberId;
	}

	private void displayOne(Member member) {
	    System.out.println("=== === 회원 정보 출력(아이디로 검색) === ===");
	    System.out.printf("아이디 : %s, 이름 : %s, 성별 : %s, 나이 : %d, 이메일 : %s, 전화번호 : %s, 주소 : %s, 가입날짜 : %tF\n",
	            member.getMemberId(), member.getMemberName(), member.getGender(), member.getAge(), 
	            member.getEmail(), member.getPhone(), member.getAddress(), member.getRegDate());
	}

	private Member inputMember() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("===== 회원 정보 입력 =====");
		
		System.out.print("아이디 : ");
		String memberId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		
		System.out.print("이름 : ");
		String memberName = sc.next();
		
		System.out.print("성별 : ");
		String gender = sc.next();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호 : ");
		String phone = sc.next();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		sc.nextLine(); // 개행 문자들을 미리 읽도록 추가
		/* nextLine() 메서드는 한 줄 전체를 입력으로 받기 때문에 공백 문자나 개행 문자를 포함한 문자열을 모두 입력받을 수 있음
		그러나 이로 인해 이전에 다른 next() 메서드를 사용한 후 nextLine()을 호출하는 경우 
		개행 문자(\n)가 남아있을 수 있어서 원하는 입력이 정상적으로 이루어지지 않는 오류가 발생할 수 있다 */
		
		System.out.print("취미 : ");
		String hobby = sc.nextLine();
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setGender(gender);
		member.setAge(age);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby);
		
		return member;
	}

	private void displayMessage(String msg) {
		System.out.println(msg);
	}

	private int printMainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 관리 프로그램 =====");
		System.out.println("1. 회원가입");
		System.out.println("2. 회원 전체조회");
		System.out.println("3. 회원 검색(아이디로 조회)");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int value = sc.nextInt();
		return value;
	}

	public void displayMemberList(List<Member> mList) {
		System.out.println("===== 회원 정보 전체 출력 =====");
		for(Member member : mList) {
			System.out.println("아이디 : " + member.getMemberId());
			System.out.println("이름 : " + member.getMemberName());
			System.out.println("성별 : " + member.getGender());
			System.out.println("나이 : " + member.getAge());
			System.out.println("이메일 : " + member.getEmail());
			System.out.println("전화번호 : " + member.getPhone());
			System.out.println("주소 : " + member.getAddress());
			System.out.println("취미 : " + member.getHobby());
			System.out.println("---------------------------------");
		}
	}
}