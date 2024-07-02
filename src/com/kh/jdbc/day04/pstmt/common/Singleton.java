package com.kh.jdbc.day04.pstmt.common;

// Singleton
public class Singleton {
	
	// static & 클래스 타입인 멤버변수 필요
	private static Singleton instance;
	
	// static & public & return 타입 = Singleton인 메소드 필요
	public static Singleton getInstance() {
		// 메소드 안에서는 if문으로 null 체크
		if(instance == null) { // null이면 객체 생성
			instance = new Singleton();
		}
		// null이 아니면 return
		return instance;
	}
}
