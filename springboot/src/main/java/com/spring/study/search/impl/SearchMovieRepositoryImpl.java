package com.spring.study.search.impl;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.JPQLQuery;
import com.spring.study.entity.Movie;
import com.spring.study.entity.QMovie;
import com.spring.study.entity.QReview;
import com.spring.study.search.SearchMovieRepository;

import lombok.extern.log4j.Log4j2;
// 상속과 구현체
@Log4j2
public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository{

	public SearchMovieRepositoryImpl() {
		super(Movie.class);
	}

//	@Override
//	public Movie search1() {
//		log.info("serach1.........................");
//		
//		Q-class 활용 > JPQL (moovie라는 객체 생성)
//		QMovie movie = QMovie.movie;
//		JPQLQuery를 활용한 - FROM 절
//		JPQLQuery<Movie> jpqlQuery = from(movie);
//		SELECT절과 WHERE절을 활용한 검색
//		jpqlQuery.select(movie).where(movie.mno.eq(143L));
//		
//		log.info("----------------------------------------------");
//		log.info(jpqlQuery);
//		log.info("----------------------------------------------");
//		
//		해당 영화를 가져옴
//		List<Movie> result = jpqlQuery.fetch();
//		
//		return null;
//	}
	
	@Override
	public Movie search1() {
		log.info("serach1.........................");
		
//		Q-class 활용 > JPQL (moovie라는 객체 생성)
		QMovie movie = QMovie.movie;
		QReview review = QReview.review;
		
		JPQLQuery<Movie> jpqlQuery = from(movie);
//		SELECT절과 WHERE절을 활용한 검색
		jpqlQuery.select(movie).where(movie.mno.eq(149L));
//		리뷰와 영화를 LEFT JOIN하여 결과를 가져옴 (이전 버전에서는 WITH)
		jpqlQuery.leftJoin(review).on(review.movie.eq(movie));
		
		log.info("----------------------------------------------");
		log.info(jpqlQuery);
		log.info("----------------------------------------------");
		
//		해당 영화를 가져옴
		List<Movie> result = jpqlQuery.fetch();
		
		return null;
	}
	
	

}
