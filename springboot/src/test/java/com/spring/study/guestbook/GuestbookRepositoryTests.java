package com.spring.study.guestbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.study.entity.Guestbook;
import com.spring.study.entity.QGuestbook;
import com.spring.study.repository.GuestbookRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class GuestbookRepositoryTests {
	@Autowired
	private GuestbookRepository guestbookRepository;

//	@Test
//	void testInsertDummies() {
//		IntStream.rangeClosed(1, 300).forEach(i -> {
//			Guestbook guestbook = Guestbook.builder()
//										   .title("Title....." + i)
//										   .content("Content...." + i)
//										   .writer("user" + (i % 10))
//										   .build();
//			
//			log.info(guestbookRepository.save(guestbook));
//		});
//	}
	
//	@Test
////	단일 검색 (제목)
//	public void testQuery1() {
////		검색에 따라 페이지 결과도 달라지게 함
////		페이지는 1페이지부터 시작하며 페이지 당 10개씩 보여주고 gno로 정렬하여 최근 등록된 순이 위로 보여진다.
//		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//		
//		QGuestbook qGuestbook = QGuestbook.guestbook;
////		검색 키워드 1로 초기화
//		String keyword = "1";
//		
////		BooleanBuilder : WHERE(조건절)에 사용
//		BooleanBuilder builder = new BooleanBuilder();
//		
////		제목에 키워드를 포함하고 있는 지
//		BooleanExpression expression = qGuestbook.title.contains(keyword);
//		
//		builder.and(expression);
////		검색 후 페이지 처리
//		Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//		
//		result.stream().forEach(guestbook -> {
//			log.info(guestbook);
//		});
//	}
	
//	멀티 검색 / 다이나믹 쿼리
	@Test
	public void testQuery2() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
		
		QGuestbook qGuestbook = QGuestbook.guestbook;
		
		String keyword = "1";
		String writer = "0";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		BooleanExpression exTitle = qGuestbook.title.contains(keyword);
		BooleanExpression exContent = qGuestbook.content.contains(keyword);
		BooleanExpression exWriter = qGuestbook.writer.contains(writer);
		
		BooleanExpression exAll = exTitle.or(exContent).or(exWriter);
		
//		조건 추가
		builder.and(qGuestbook.gno.gt(0L));
		builder.and(exAll);
		
		Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
		
		result.stream().forEach(guestbook -> {
			log.info(guestbook);
		});
	}

}
