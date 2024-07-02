package com.kh.jdbc.day03.pstmt.member.view;

import java.util.*;

import com.kh.jdbc.day03.pstmt.member.controller.MemberController;
import com.kh.jdbc.day03.pstmt.member.model.vo.Member;

public class MemberView {
	MemberController mController;
	
	public MemberView() {
		mController = new MemberController();
	}
	
	public void startProgram() {
		finish:
		while(true) {
			int choice = this.mainMenu();
			switch(choice) {
			case 1:
				Member member = this.inputInfo();
				// 회원정보를 입력 받기 
				// 입력받은 정보를 객체에 저장
				// 객체를 controller로 전달
				int result = mController.insertMember(member);
				if(result > 0) {
					printerMessage("회원가입 성공");
				} else {
					printerMessage("회원가입 실패");
				}
				break;
			case 2:
				member = this.inputLoginInfo();
				// 입력한 아이디와 비밀번호가 있는지 확인
				member = mController.checkLogin(member);
				if(member != null) {
					this.printOnMember(member);
				} else {
					printerMessage("존재하지 않는 정보입니다.");
				}
				break;
			case 3:
				String memberId = inputMemnerId();
				member = mController.checkMember(memberId);
				if(member != null) {
					// 수정하기
					member = modeifyMember();
					member.setMemberId(memberId);
					result = mController.updateMember(member);
					if(result > 0) {
						printerMessage("정보 수정 성공");
					}
				} else {
					printerMessage("존재하지 않는 정보입니다.");
				}
				break;
			case 4:
				memberId = inputMemnerId(); 
				member = mController.checkMember(memberId);
				if(member != null) {
					result = mController.removeMember(memberId);
					if(result > 0) {
						this.printerMessage("삭제 성공");
					}
				} else {
					this.printerMessage("존재하지 않는 정보입니다.");
				}
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				printerMessage("프로그램 종료");
				break finish;
			}
		}
	}
	
	private Member modeifyMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 정보 수정 ======");
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		System.out.print("이메일 : ");
		sc.nextLine();
		String email = sc.nextLine();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address = sc.nextLine();
		System.out.print("취미 : ");
		String hobby = sc.next();
		
		Member member = new Member(memberPw, email, phone, address, hobby);
		return member;
	}

	private String inputMemnerId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		return memberId;
	}

	private int mainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 관리 프로그램 =====");	
		System.out.println("1. 회원 가입");
		System.out.println("2. 로그인");
		System.out.println("3. 회원 정보 수정");
		System.out.println("4. 회원 탈퇴");
		System.out.println("9. 프로그램 종료 ");
		System.out.print("메뉴 선택 : ");
		int input = sc.nextInt();
		return input; // 호출하는 곳에서 사용하기 때문에
	}

	private void printOnMember(Member member) {
		System.out.println("===== 회원 정보 출력 =====");
	    System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, "
	    		+ "전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %tF\n",
	    		member.getMemberName(), member.getAge(), member.getMemberId(),  member.getGender(),  
	            member.getEmail(), member.getPhone(), member.getAddress(), member.getHobby(), member.getRegDate());
	}
	
	private Member inputInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 정보 입력 ======");
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
		sc.nextLine();
		String email = sc.nextLine();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address = sc.nextLine();
		System.out.print("취미 : ");
		String hobby = sc.next();
		// return 여러개 안됨 > 객체 이용
		// 매개변수 생성자 사용
		Member member = new Member(memberId, memberPw, memberName, gender, age, email,
				phone, address, hobby );
		
		return member; // 호출한 곳에서 사용해야하기 때문에
	}

	private Member inputLoginInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 로그인 정보 입력 =====");
		System.out.print("아이디 : ");
		String memberId = sc.next();
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		// 리턴 여러개 안되니까 객체 이용
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		return member;
	}


	private void printerMessage(String message) {
		System.out.println("[결과] : " + message);
	}



}