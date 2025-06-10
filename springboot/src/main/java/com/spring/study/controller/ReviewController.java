package com.spring.study.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.study.dto.ReviewDTO;
import com.spring.study.service.IReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
	
	private final IReviewService reviewService;
	
//	경로를 받아서 넘겨주는 어노테이션
	@GetMapping("/{mno}/all")
	public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno) {
		log.info("=================== Review List ===================");
		
		log.info("mno : {}", mno);
		
		List<ReviewDTO> reviewDtoList = reviewService.getListOfMovie(mno);
		
		return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
	}
	
	@PostMapping("/{mno}")
	public ResponseEntity<Long> addReview(@RequestBody ReviewDTO movieReviewDTO) {
		log.info("=================== add Movie Review ===================");
		
		log.info("movieReviewDTO : {}", movieReviewDTO);
		
		Long reviewnum = reviewService.register(movieReviewDTO);
		
		return new ResponseEntity<>(reviewnum, HttpStatus.OK);
	}
	
	@PutMapping("/{mno}/{reviewnum}")
	public ResponseEntity<Long> modifyReview(@PathVariable("reviewnum") Long reviewnum, 
											 @RequestBody ReviewDTO movieReviewDTO) {
		log.info("=================== modify Review ===================");
		
		log.info("movieReviewDTO : {}", movieReviewDTO);
		
		reviewService.modify(movieReviewDTO);
		
		return new ResponseEntity<Long>(reviewnum, HttpStatus.OK);
	}
	
	@DeleteMapping("/{mno}/{reviewnum}")
	public ResponseEntity<Long> removeReview(@PathVariable("reviewnum") Long reviewnum) {
		log.info("=================== remove Review ===================");
		
		log.info("reviewnum : {}", reviewnum);
		
		reviewService.remove(reviewnum);
		
		return new ResponseEntity<Long>(reviewnum, HttpStatus.OK);
	}
}
