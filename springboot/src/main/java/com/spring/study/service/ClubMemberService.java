package com.spring.study.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.study.dto.ClubMemberDTO;
import com.spring.study.entity.ClubMember;

public interface ClubMemberService {
	
	public void joinUser(ClubMemberDTO dto);
	
	default ClubMember entity(ClubMemberDTO dto, PasswordEncoder passwordEncoder) {
		return ClubMember.builder().email(dto.getEmail())
								   .name(dto.getName())
								   .password(passwordEncoder.encode(dto.getPassword()))
								   .fromSocial(false)
								   .build();
	}
	
	default ClubMemberDTO dto(ClubMember entity) {
		return ClubMemberDTO.builder().email(entity.getEmail())
									  .name(entity.getName())
									  .password("")
									  .build();
	}

}
