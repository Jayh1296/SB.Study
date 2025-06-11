package com.spring.study.guestbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.spring.study.dto.GuestbookDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Guestbook;
import com.spring.study.service.IGuestbookService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class GuestbookServiceTests {
	
	private IGuestbookService guestbookService;
	
//	생성자 주입 방식 (불변 객체를 명확하게 명시할 수 있음, 권장 > 안정적이기 때문에)
	@Autowired
	public GuestbookServiceTests(IGuestbookService service) {
		this.guestbookService = service;
	}
	
//	@Test
//	void testRegister() {
//		log.info("------------------SAMPLE DATA------------------------");
//		GuestbookDTO guestbookDTO = GuestbookDTO.builder()
//												.title("Sample Title")
//												.content("Sample Content")
//												.writer("user10")
//												.build();
//		
//		log.info(guestbookService.register(guestbookDTO));
//	}
	
//	@Test
//	public void testList() {
//		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
//		
//		PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);
//		
////		boolean 형일 경우 get이 아닌 is
//		log.info("PREV : " + resultDTO.isPrev());
//		log.info("NEXT : " + resultDTO.isNext());
//		log.info("TOTAL : " + resultDTO.getTotalPage());
//		
//		for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
//			log.info(guestbookDTO);
//		}
//	}
	
	@Test
	public void testSearch() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
													  .page(1)
													  .size(10)
													  .type("tc")
													  .keyword("Sample")
													  .build();
		PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);
		
		log.info("PREV : " + resultDTO.isPrev());
		log.info("NEXT : " + resultDTO.isNext());
		log.info("TOTAL : " + resultDTO.getTotalPage());
		
		log.info("============================================================================");
		for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
			log.info(guestbookDTO);
		}
		log.info("============================================================================");
		resultDTO.getPageList().forEach(i -> log.info(i));
	
	}

}
