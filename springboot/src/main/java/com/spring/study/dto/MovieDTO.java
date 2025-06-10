package com.spring.study.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

	private Long mno;
	
	private String title;
	
	@Builder.Default
	private List<MovieImageDTO> imageDtoList = new ArrayList<>();
	
	private double avg;
	
	private int reviewCnt;
	
	private LocalDateTime regDate;
	
	private LocalDateTime modDate;
}
