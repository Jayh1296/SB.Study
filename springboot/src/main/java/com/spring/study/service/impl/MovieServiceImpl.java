package com.spring.study.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.study.dto.MovieDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Movie;
import com.spring.study.entity.MovieImage;
import com.spring.study.entity.QMovie;
import com.spring.study.repository.MovieImageRepository;
import com.spring.study.repository.MovieRepository;
import com.spring.study.service.IMovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements IMovieService{
//	RequiredArgsConstructor : 4.3버전 부터는 Autowired 없이 가능한데 대신 해주는 기능
	private final MovieRepository movieRepository;
	private final MovieImageRepository imageRepository;
	
	@Transactional
	@Override
	public Long register(MovieDTO movieDTO) {
		
		Map<String, Object> entityMap = dtoToEntity(movieDTO);
//		Movie 객체 < Object 다양한 객체 : Movie라는 객체에 Object를 담을 수 없기 때문에 캐스팅
		Movie movie = (Movie)entityMap.get("movie");
		
		List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
		
		movieRepository.save(movie);
		movieImageList.forEach(movieImage -> {
			imageRepository.save(movieImage);
			
		});
		
		return movie.getMno();
	}

	@Override
	public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());
		
//		검색 조건
//		BooleanBuilder booleanBuilder = getSearch(requestDTO);
		
		Page<Object[]> result = movieRepository.getListPage(pageable);
		
//		검색 조건을 포함한 페이지 결과로 변환
//		Page<Object[]> result = movieRepository.findAll(booleanBuilder, pageable);
		
		Function<Object[], MovieDTO> fn = (arr -> entitiesToDto((Movie)arr[0],
																(List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
																(Double)arr[2],
																(Long)arr[3]));
		
		return new PageResultDTO<>(result, fn);
	}

//	private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
//		String type = requestDTO.getType();
//		
//		BooleanBuilder booleanBuilder = new BooleanBuilder();
//		
//		QMovie qMovie = QMovie.movie;
//		
//		String keyword = requestDTO.getKeyword();
//		
////		gt : mno 값이 0보다 커야 생성
//		BooleanExpression expression = qMovie.mno.gt(0L);
//		
//		booleanBuilder.and(expression);
////		검색 조건이 존재하지 않을 경우
//		if (type == null || type.trim().length() == 0) {
//			return booleanBuilder;
//		}
//		
////		검색 조건이 있을 경우
//		BooleanBuilder conditionBuilder = new BooleanBuilder();
////		다이나믹 쿼리 사용 (영화 제목에 키워드와 비슷한 값들을 찾아옴)
//		if (type.contains("t")) {
//			conditionBuilder.or(qMovie.title.contains(keyword));
//		}
//		
//		return null;
//	}

	@Override
	public MovieDTO getMovie(Long mno) {
		
		List<Object[]> result = movieRepository.getMoviewWithAll(mno);
		
		Movie movie = (Movie)result.get(0)[0];
		
		List<MovieImage> movieImageList = new ArrayList<>();
		
		result.forEach(arr -> {
			MovieImage movieImage = (MovieImage)arr[1];
			
			movieImageList.add(movieImage);
		});
		
//		평점
		Double avg = (Double)result.get(0)[2];
		
//		댓글 조회수
		Long reviewCnt = (Long)result.get(0)[3];
		
		return entitiesToDto(movie, movieImageList, avg, reviewCnt);
	}

}
