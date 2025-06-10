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

import com.spring.study.dto.MovieDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Movie;
import com.spring.study.entity.MovieImage;
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
		
		Page<Object[]> result = movieRepository.getListPage(pageable);
		
		Function<Object[], MovieDTO> fn = (arr -> entitiesToDto((Movie)arr[0],
																(List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
																(Double)arr[2],
																(Long)arr[3]));
		
		return new PageResultDTO<>(result, fn);
	}

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
