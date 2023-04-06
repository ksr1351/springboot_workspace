package com.example.shop.members.dto;

import org.springframework.stereotype.Component;

import com.example.shop.common.exception.WrongEmailPasswordException;

@Component
public class MembersDTO {
	private String memberEmail; //이메일
	private String memberPass; //비밀번호
	private String memberName; //이름
	private String memberPhone; //전화번호
	private int memberType; //회원구분 일반회원 1, 관리자 2
	private boolean rememberEmail; //자동로그인 체크
	private String authRole; //

	
	public MembersDTO() {

	}
	
	public String getAuthRole() {
		return authRole;
	}
	
	public void setAuthRole(String authRole) {
		this.authRole = authRole;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPass() {
		return memberPass;
	}

	public void setMemberPass(String memberPass) {
		this.memberPass = memberPass;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public boolean isRememberEmail() {
		return rememberEmail;
	}

	public void setRememberEmail(boolean rememberEmail) {
		this.rememberEmail = rememberEmail;
	}
	
	public boolean matchPassword(String memberPass) { //입력한 비밀번호 받기
		return this.memberPass.equals(memberPass); //db에서 검색된 비밀번호 
		//입력된 비밀번호와 db에서 검색된 비밀번호가 같을 경우 true, 아니면 false
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if(!memberPass.equals(oldPassword)) //본인 검증 (테이블에 저장되어있는 값)
			throw new WrongEmailPasswordException(); //기존의 비밀번호를 잘 못 입력할 경우 해당 익셉션 띄움
		this.memberPass=newPassword; //여기서 this는 테이블에서 가지고 온 비밀번호 
	}
	
	
}















