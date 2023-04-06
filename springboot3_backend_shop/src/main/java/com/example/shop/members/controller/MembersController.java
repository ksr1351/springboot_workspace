package com.example.shop.members.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.members.dto.AuthInfo;
import com.example.shop.members.dto.MembersDTO;
import com.example.shop.members.service.MembersService;


//@CrossOrigin(origins = {"http://localhost:3000"})
@CrossOrigin("*")

@RestController
public class MembersController {

	@Autowired //MembersService으로 되어있는 빈을 참조해서 사용한다
	private MembersService membersService;
	
	@Autowired
	private BCryptPasswordEncoder encodePassword;

	public MembersController() {

	}

	// 회원가입
	@PostMapping("/member/signup")
	public String addMember(@RequestBody MembersDTO membersDTO) { // json으로 넘길 때는 @RequestBody넣기
																	
		membersDTO.setMemberPass(encodePassword.encode(membersDTO.getMemberPass())); //dto에 있는 memberspass를 가져와서 인코딩해주고 다시 dto에 담아서 보내준다.(비번 노출 방지)
		AuthInfo authInfo = membersService.addmemberProcess(membersDTO);
		return null;
	}



}// end class
