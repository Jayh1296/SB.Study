package com.spring.study.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.spring.study.entity.Member;
import com.spring.study.repository.MemberRepository;
import com.spring.study.repository.ReviewRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class MemberRepositoryTests {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
//	@Test
//	void testInsertMembers() {
//		IntStream.rangeClosed(1, 100).forEach(i -> {
//			Member member = Member.builder().email("user" + i + "@naver.com")
//											.pw("1111")
//											.nickname("reviewer" + i)
//											.build();
//			memberRepository.save(member);
//		});
//	}
	
//	Transactional : 두 개 ( 회원 삭제, 회원과 관계를 맺은 리뷰와 관계를 끊어냄) 기능이 모두 처리가 되어야 하기 때문에
//					실패 시 rollback 될 수 있도록 해줘야 함 ( 멤버만 삭제되고 리뷰와 관계를 끊어내지 못할 경우 무의미한 데이터가 남게 됨)
//	@Transactional
//	@Commit
//	@Test
//	void testDeleteMembers() {
//		Long mid = 1L;
//		
//		Member member = Member.builder().mId(mid).build();
//		
//		memberRepository.deleteById(mid);
//		reviewRepository.deleteByMember(member);
//	}

}














