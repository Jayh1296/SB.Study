package com.spring.study.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.study.entity.ClubMember;
import com.spring.study.entity.ClubMemberRole;

import lombok.extern.log4j.Log4j2;
@SpringBootTest
@Log4j2
class ClubMemberTests {

	@Autowired
	private ClubMemberRepository clubMemberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Test
//	void testInsertDummies() {
////		1 ~ 80 : USER만 Role 부여
////		81 ~ 90 : USER, MANAGER 부여
////		91 ~ 100 : USER, MANAGER, ADMIN 부여
////		메소드 체이닝 기법 : 메소드.메소드.....로 메소드를 나열
//		IntStream.rangeClosed(1, 100).forEach(i -> {
//			ClubMember clubMember = ClubMember.builder()
//											  .email("user" + i + "@naver.com")
//											  .name("사용자" + i)
//											  .fromSocial(false)
//											  .password(passwordEncoder.encode("1111"))
//											  .build();
//											  
//			clubMember.addMemberRole(ClubMemberRole.USER);
//			
//			if ( i > 80 ) {
//				clubMember.addMemberRole(ClubMemberRole.MANAGER);
//			}
//			
//			if ( i > 90 ) {
//				clubMember.addMemberRole(ClubMemberRole.ADMIN);
//			}
//			
//			clubMemberRepository.save(clubMember);
//		});
//		
//	}
	
	@Test
	public void testRead() {
		Optional<ClubMember> result = clubMemberRepository.findByEmail("user95@naver.com", false);
		
		log.info(result);
		
		ClubMember clubMember = result.get();
		
		log.info("testRead() : {}", clubMember);
	}

}
