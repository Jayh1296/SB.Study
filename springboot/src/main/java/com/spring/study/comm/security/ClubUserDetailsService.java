package com.spring.study.comm.security;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.study.dto.ClubAuthMemberDTO;
import com.spring.study.entity.ClubMember;
import com.spring.study.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService{
	
	private final ClubMemberRepository clubMemberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("ClubUserDetailsService loadUserByUsername : {} ", username);
		
		Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);
		
		log.info("result : {} ", result);
		
//		없는 유저 예외 처리
		if (result.isEmpty()) {
			throw new UsernameNotFoundException("Check Email or Social" + username);
		}
		
		ClubMember clubMember = result.get();
		
		log.info("=================================================================");
		log.info("clubMember : {} ", clubMember);
		
		ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(clubMember.getEmail(),
																	clubMember.getPassword(),
																	clubMember.isFromSocial(),
																	clubMember.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
		
		clubAuthMemberDTO.setName(clubMember.getName());
		clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());
		return clubAuthMemberDTO;
	}

}
