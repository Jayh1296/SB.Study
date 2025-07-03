package com.spring.study.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.study.entity.Movie;
import com.spring.study.entity.Review;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class ReviewRepositoryTests {
	@Autowired
	private ReviewRepository reviewRepository;

//	@Test
//	void testInsertMovieReviews() {
//		IntStream.rangeClosed(1, 200).forEach(i -> {
//			Long mno = (long)(Math.random() * 100) + 1;
//			
//			Long mid = (long)(Math.random() * 100) + 1;
//			
//			Member member = Member.builder().mId(mid).build();
//			
//			Review movieReview = Review.builder().member(member)
//												.movie(Movie.builder().mno(mno).build())
//												.grade((int)(Math.random() * 5) + 1)
//												.text("이 영화에 대한 느낌....." + i)
//												.build();
//			
//			reviewRepository.save(movieReview);
//		});
//	}
	
	@Test
	public void testGetMovieReviews() {
		Movie movie = Movie.builder().mno(99L).build();
		
		List<Review> result = reviewRepository.findByMovie(movie);
		
		result.forEach(movieReview -> {
			log.info(movieReview.getReviewnum());
			log.info(movieReview.getGrade());
			log.info(movieReview.getText());
			log.info(movieReview.getMember().getEmail());
		});
	}

}
