package com.spring.study.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.spring.study.entity.Movie;
import com.spring.study.entity.Review;
import com.spring.study.repository.MovieImageRepository;
import com.spring.study.repository.MovieRepository;
import com.spring.study.repository.ReviewRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class MovieRepositoryTests {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieImageRepository imageRepository;
	
//	@Commit
//	@Transactional
//	@Test
//	void testInsertMovies() {
//		IntStream.rangeClosed(1, 100).forEach(i -> {
//			Movie movie = Movie.builder().title("Movie..." + i).build();
//			
//			log.info("-----------------------------------------");
//			movieRepository.save(movie);
//			
//			댓글 수만큼 첨부파일 더미 데이터 추가
//			int count = (int)(Math.random() * 5) + 1;
//			
//			for (int j = 0; j < count; j++) {
//				MovieImage movieImage = MovieImage.builder().uuid(UUID.randomUUID().toString())
//															.movie(movie)
//															.imgName("test" + j + ".jpg")
//															.build();
//				imageRepository.save(movieImage);
//			}
//			log.info("=================================================");
//		});
//	}
	
//	@Test
//	void testListPage() {
//		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
//		
//		Page<Object[]> result = movieRepository.getListPage(pageRequest);
//		
//		for (Object[] objects : result.getContent()) {
//			log.info(Arrays.toString(objects));
//		}
//	}
	
//	@Test
//	void testGetMoviewWithAll() {
//		List<Object[]> result = movieRepository.getMoviewWithAll(100L);
//		
//		log.info(result);
//		
//		for (Object[] objects : result) {
//			log.info(Arrays.toString(objects));
//		}
//		
//	}
	
//	@Test
//	public void testSearch1() {
//		movieRepository.search1();
//	}


}











