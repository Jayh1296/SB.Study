package com.spring.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.study.dto.ClubMemberDTO;
import com.spring.study.service.ClubMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
	
	private final ClubMemberService service;

	@GetMapping("/login")
	public void login() {
		log.info("로그인 페이지........");
	}
	
	@GetMapping("/join")
	public String join(Model model) {
		log.info("회원가입 페이지..........");
		
		model.addAttribute("dto", new ClubMemberDTO());
		return "member/join";
	}
	
	@PostMapping("/join")
	public String joinProcess(@ModelAttribute("dto") ClubMemberDTO dto) {
		log.info("회원 가입 처리: {}", dto.getName());
		
		service.joinUser(dto);
		
		return "redirect:/member/login?joined";
	}
}