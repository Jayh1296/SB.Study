package com.spring.study.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.study.dto.MovieDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Movie;
import com.spring.study.entity.MovieImage;
import com.spring.study.service.IMovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

	private final IMovieService movieService;
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/register")
	public String register(MovieDTO movieDTO, RedirectAttributes rtts) {
		log.info("movieDTO : {}", movieDTO);
		
		Long mno = movieService.register(movieDTO);
		
		rtts.addFlashAttribute("msg", mno);
		
		return "redirect:/movie/list";
	}
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		model.addAttribute("result", movieService.getList(pageRequestDTO));
	}
	
	@GetMapping("/read")
	public void read(@RequestParam("mno") long mno, 
					 @ModelAttribute("requestDTO") PageRequestDTO requestDTO, 
					 Model model) {
		log.info("mno : {}", mno);
		
		MovieDTO movieDTO = movieService.getMovie(mno);
		
		model.addAttribute("dto", movieDTO);
	}
}














