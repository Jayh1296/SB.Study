package com.spring.study.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.spring.study.dto.ReviewDTO;
import com.spring.study.entity.Movie;
import com.spring.study.entity.Review;
import com.spring.study.repository.ReviewRepository;
import com.spring.study.service.IReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
	
	private final ReviewRepository reviewRepository;
	
	@Override
	public List<ReviewDTO> getListOfMovie(Long mno) {
		Movie movie = Movie.builder().mno(mno).build();
		
		List<Review> result = reviewRepository.findByMovie(movie);
		
		return result.stream().map(movieReview 
				-> entityToDto(movieReview)).collect(Collectors.toList());
	}

	@Override
	public Long register(ReviewDTO movieReviewDTO) {
		Review movieReview = dtoToEntity(movieReviewDTO);
		
		reviewRepository.save(movieReview);
		
		return movieReview.getReviewnum();
	}

	@Override
	public void modify(ReviewDTO movieReviewDTO) {
		Optional<Review> result = reviewRepository.findById(movieReviewDTO.getReviewnum());
		
//		Optional 로 존재 여부 판단
		if (result.isPresent()) {
			Review movieReview = result.get();
			movieReview.changeGrade(movieReviewDTO.getGrade());
			movieReview.changeText(movieReviewDTO.getText());
			
			reviewRepository.save(movieReview);
		}
	}

	@Override
	public void remove(Long reviewnum) {
		reviewRepository.deleteById(reviewnum);
	}

}
