package com.spring.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.spring.study.dto.MovieDTO;
import com.spring.study.dto.MovieImageDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Movie;
import com.spring.study.entity.MovieImage;

public interface IMovieService {

	Long register(MovieDTO movieDTO);
	
	PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);
	
//	영화 상세
	MovieDTO getMovie(Long mno);
	
//	JPA 화면에서 넘어온 것을 여기서 페이지로 만들어줌
	default MovieDTO entitiesToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
		MovieDTO movieDTO = MovieDTO.builder()
									.mno(movie.getMno())
									.title(movie.getTitle())
									.regDate(movie.getRegDate())
									.modDate(movie.getModDate())
									.build();

		List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
			return MovieImageDTO.builder()
								.imgName(movieImage.getImgName())
								.path(movieImage.getPath())
								.uuid(movieImage.getUuid())
								.build();

		}).collect(Collectors.toList());
		
		movieDTO.setImageDtoList(movieImageDTOList);
		movieDTO.setAvg(avg);
		movieDTO.setReviewCnt(reviewCnt.intValue());
		
		return movieDTO;
	}
	
//	인터페이스 구현체를 만들지 않고 여기서 정의
//	여기서 활용한 것은 DTO를 Entity로 처리 할 수 있게 해줌
	default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
		Map<String, Object> entityMap = new HashMap<>();
		
		Movie movie = Movie.builder()
								.mno(movieDTO.getMno())
								.title(movieDTO.getTitle())
								.build();
		
		entityMap.put("movie", movie);
		
		List<MovieImageDTO> imageDtoList = movieDTO.getImageDtoList();
		
		if (imageDtoList != null && imageDtoList.size() > 0) {
			List<MovieImage> movieImageList = imageDtoList.stream()
												.map(movieImageDTO -> {
													MovieImage movieImage = MovieImage.builder()
																					.path(movieImageDTO.getPath())
																					.imgName(movieImageDTO.getImgName())
																					.uuid(movieImageDTO.getUuid())
																					.movie(movie).build();
													return movieImage;
												}).collect(Collectors.toList());
			entityMap.put("imgList", movieImageList);
		}
		
		return entityMap;
	}
}
