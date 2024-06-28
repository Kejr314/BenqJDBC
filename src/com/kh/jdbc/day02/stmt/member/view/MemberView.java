package com.kh.jdbc.day02.stmt.member.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day02.stmt.member.controller.MemberController;
import com.kh.jdbc.day02.stmt.member.model.vo.Member;

public class MemberView {
	
	MemberController mController; // View 클래스에서 계속 사용할 예정, 필드로 둠
	
	public MemberView() { // 생성자에서 초기화해줌
		mController = new MemberController();
	}
	
	public void startProgram() {
		end:
		while(true) {
		int value = this.printMainMenu();
			switch (value) {
			case 1: // 1을 입력하면 회원 정보를 입력 받아야 함
				Member member = this.inputMember(); // inutMember() 실행시켜 정보 
				mController.insertMember(member);
				break;
			case 2:  // 2를 입력하면 회원 전체 정보를 출력해야 함
				// 1. DB에서 데이터 가져오기
				// 전체 회원 정보 -> 여러개 -> list > List<Member>
				List<Member> mList = mController.printAllMember();
				// 2. view의 메소드를 이용해서 출력하기
				this.printAllMembers(mList);
				break;
			case 3: // 3을 입력하면 회원의 정보를 검색해야 함(아이디로 검색)
				// 사용자가 검색한 아이디 입력받아야 함으로 inputMember()
				String memberId = this.inputMemberId();
				// 입력받은 아이디로 DB에서 검색해 와야 함으로 printOneMember()
				// controller로 전달해야 함으로
				member = mController.printOneMember(memberId);
				this.printOnemember(member);
				break;
			case 4: // 회원의 정보를 수정해야 함(아이디로 정보가 존재하는지 확인 후 있으면 수정 없으면 안함)
				// 사용자가 수정할 아이디 입력받아야 함으로 inputMember();
				memberId = inputMemberId();
				// 존재하는 정보만 수정로직을 타야하니까 printONeMember(0 호출
				member = mController.printOneMember(memberId);
				// DB에서 데이터를 가져왔는지 체크해야 함으로 if(member != null)
				// 데이터가 없다면 member는 null
				if(member !=null) {
					// 수정할 정보를 입력해야 함으로 modifyMember(member); member를 전달해야 함
					// 즉, 수정할 정보를 가지고 있는 member객체가 필요함
					Member modifyInfo = this.inputModifyInfo();
					// UPDATE를 할 때 가장 중요한 것 : WHERE 조건절, WHERE에 들어갈 데이터를 전달해줘야 함
					// modifyMember(modifyInfo)에서 modifyInfo에 memberId를 꼭 넣어줘야 함. -> modifyInfo.setMemberId(memberId)
					modifyInfo.setMemberId(memberId);
					// DML의 결과는 int -> int result
					int result = mController.modifyMember(modifyInfo);
					if (result > 0) {
						this.displayMessage("수정 성공");
					} else {
						this.displayMessage("수정 실패");
					}
				} else {
					this.displayMessage("존재하지 않는 정보입니다.");
				}
				break;
			case 5: // 5를 입력하면 회원의 정보를 삭제해야 함(아이디로 삭제)
				// 사용자가 삭제할 아이디 입력받아야 함으로 inputMember()
				memberId = this.inputMemberId();
				// 존재하는 정보만 삭제 로직을 타야하니까 printOneMember() 호출
				// memberId 전달해야하믕로 printONeMember(memberId);
				// DB에서 가져오노 값이 저장되어야 하니까 
				member = mController.printOneMember(memberId);
				// DB에서 데이터를 가져왔는지 체크해야 함으로 if(member != null)
				// 데이터가 없다면 member는 null
				if(member != null) {
					// 입력받은 아이디로 DB에서 삭제해야 함으로 removeMember();
					// Controller로 전달해야 함으로 removeMember(memberId)
					// DML의 결과는 int -> int result
					int result = mController.removeMember(memberId);
					if(result > 0) {
						this.displayMessage("삭제 성공");
					} else {
						this.displayMessage("삭제 실패");
					}					
				} else {
					this.displayMessage("존재하지 않는 정보입니다.");
				}
				break;
			case 0:
				break end;
			}
		}
	}

	private Member inputModifyInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 정보 수정 =====");
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
		
		Member member = new Member();
		member.setMemberPw(memberPw);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby);
		
		return member;
	}

	private void displayMessage(String msg) {
		System.out.println("[서비스 결과] : " + msg);
	}

	private void printOnemember(Member member) {
		System.out.println("===== 회원 정보 출력 =====");
		    System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, "
		    		+ "전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %tF\n",
		    		member.getMemberName(), member.getAge(), member.getMemberId(),  member.getGender(),  
		            member.getEmail(), member.getPhone(), member.getAddress(), member.getHobby(), member.getRegDate());
		
	}

	private String inputMemberId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		// 호출한 곳에서 사용해야 함으로 
		// 호출한 곳 : MemberView > StartProgram() > this.printOnemember(member);
		return memberId;
	}

	// MemberView의 startProgram() 의 this.printAllMembers(mList);
	private void printAllMembers(List<Member> mList) {
		 System.out.println("===== 회원 정보 전체 출력 =====");
		 for(Member member : mList) {
		    System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, "
		    		+ "전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %tF\n",
		    		member.getMemberName(), member.getAge(), member.getMemberId(),  member.getGender(),  
		            member.getEmail(), member.getPhone(), member.getAddress(), member.getHobby(), member.getRegDate());
		
		 }
	}

	private Member inputMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 정보 등록 =====");
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
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setGender(gender);
		member.setAge(age);
		member.setEmail(email);
		member.setAddress(address);
		member.setHobby(hobby);
		
		return member;
	}

	public int printMainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원 관리 프로그램 =====");
		System.out.println("1. 회원가입");
		System.out.println("2. 전체 회원 조회");
		System.out.println("3. 회원 검색");
		System.out.println("4. 회원 정보 수정");
		System.out.println("5. 회원 탈퇴");
		System.out.println("0. 종료");
		System.out.print("메뉴 선택 : ");
		int choice = sc.nextInt();
		return choice; // 다른 메소드에서 쓸 수 있도록 리턴
	}
}
