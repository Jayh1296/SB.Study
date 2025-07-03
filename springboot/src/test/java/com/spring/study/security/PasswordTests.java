package com.spring.study.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class PasswordTests {
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public PasswordTests(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Test
	void testEncode() {
		String password = "1111";
		
//		암호화 되어 password를 enPw가 생성
		String enPw = passwordEncoder.encode(password);
		
		log.info("enPw : {}", enPw);
		
		String pw = "1233";
		
		boolean matchResult = passwordEncoder.matches(password, enPw);
		
//		발급될 때마다 계속 바뀜 (1111을 주입하면 true 로 진입 가능하지만 DB에 들어가기 전에 암호화 되기 때문에 계속 바뀜)
//		$2a$10$MpNxOq9FbYjnB1KCFR106ujxJ1b3TKdmV4eJnRQxZ6pNPHE4DGc8i
//		$2a$10$6dtAj2bTpHc7g4vMWdROP.Iu2S/ilttFDVnGgFeZhbXb10x1aney.
		
		log.info("matchResult : {}" , matchResult);
		
	}
}
