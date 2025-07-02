package com.spring.study.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.study.dto.ClubMemberDTO;
import com.spring.study.entity.ClubMember;
import com.spring.study.entity.ClubMemberRole;
import com.spring.study.repository.ClubMemberRepository;
import com.spring.study.service.ClubMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClubMembeerServiceImpl implements ClubMemberService{
	
	private final ClubMemberRepository clubMemberRepository;
	private final PasswordEncoder encoder;
	
	@Override
	public void joinUser(ClubMemberDTO dto) {
		log.info("회원가입 진행 서비스 ...");
		
		ClubMember member = entity(dto, encoder);
		member.addMemberRole(ClubMemberRole.USER);
		
		clubMemberRepository.save(member);
	}
	
}
