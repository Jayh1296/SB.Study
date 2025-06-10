package com.spring.study.service;

import java.util.List;

import com.spring.study.dto.ReviewDTO;
import com.spring.study.entity.Member;
import com.spring.study.entity.Movie;
import com.spring.study.entity.Review;

public interface IReviewService {

	List<ReviewDTO> getListOfMovie(Long mno);
	Long register(ReviewDTO movieReviewDTO);
	void modify(ReviewDTO movieReviewDTO);
	void remove(Long reviewnum);
	
//	DTO를 Entity 전환
	default Review dtoToEntity(ReviewDTO movieReviewDTO) {
		Review movieReview = Review.builder()
								   .reviewnum(movieReviewDTO.getReviewnum())
								   .movie(Movie.builder().mno(movieReviewDTO.getMno()).build())
								   .member(Member.builder().mId(movieReviewDTO.getMid()).build())
								   .grade(movieReviewDTO.getGrade())
								   .text(movieReviewDTO.getText())
								   .build();
		return movieReview;
	}
	
//	Entity를 DTO로 전환
	default ReviewDTO entityToDto(Review movieReview) {
		ReviewDTO movieReviewDTO = ReviewDTO.builder()
											.reviewnum(movieReview.getReviewnum())
											.mno(movieReview.getReviewnum())
											.mid(movieReview.getMember().getMId())
											.nickname(movieReview.getMember().getNickname())
											.email(movieReview.getMember().getEmail())
											.grade(movieReview.getGrade())
											.text(movieReview.getText())
											.regDate(movieReview.getRegDate())
											.modDate(movieReview.getModDate())
											.build();
		
		return movieReviewDTO;
	}
}
