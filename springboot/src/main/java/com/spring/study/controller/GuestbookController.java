package com.spring.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.study.dto.PageRequestDTO;
import com.spring.study.service.IGuestbookService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@AllArgsConstructor
public class GuestbookController {
	
	private final IGuestbookService service;

//	/guestbook/list로 들어오게 됨
//	@GetMapping({"/", "/list"})
//	public String list() {
//		log.info("list 페이지.......................");
//		
//		return "/guestbook/list";
//	}
	
	@GetMapping("/")
	public String index() {
		
		log.info("여기야!!!! 여긴 인덱스야!!!");
		return "redirect:/guestbook/list";
	}
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
	    log.info("🔥🔥 [GuestbookController] list() 호출 - pageRequestDTO = {}", pageRequestDTO);

	    var result = service.getList(pageRequestDTO);
	    log.info("📦 [GuestbookController] service.getList() 결과 = {}", result);

	    model.addAttribute("result", result);
	}
}
