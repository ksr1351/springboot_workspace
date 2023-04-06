package com.example.shop.security.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.shop.members.dao.MembersDAO;
import com.example.shop.members.dto.MembersDTO;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private MembersDAO membersDAO;

	public PrincipalDetailsService() {

	}

	
	//1. AuthenticationProvider에서 load에서 loadUserByUsername(String username)을 호출한다.
	//2. loadUserByUsername(String username)에서는 DB에서 username에 해당하는 데이터를 검색해서 UserDetails에 담아서
	//3. AuthenticationProvider에서 UserDetailes받아서 Autentication에 저장을 함으로써 결국 Security Session에
	
	@Override
	public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
		
		MembersDTO userEntity = membersDAO.selectByEmail(memberEmail);

		if (userEntity == null) {
			throw new UsernameNotFoundException(memberEmail);
		}

		return new PrincipalDetails(userEntity);
	}

}
