package com.spring.study.service;

import com.spring.study.dto.GuestbookDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Guestbook;

public interface IGuestbookService {

//	화면에서 넘어온 값을 dto로 전달
	Long register(GuestbookDTO dto);
	
//	만들어놓은 PageDTO 활용, 페이지 결과DTO, 요청된 페이지DTO
	PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
	
	default Guestbook dtoToEntity(GuestbookDTO dto) {
		Guestbook entity = Guestbook.builder()
									.gno(dto.getGno())
									.title(dto.getTitle())
									.content(dto.getContent())
									.writer(dto.getWriter())
									.build();
		
		return entity;
	}
	
	default GuestbookDTO entityToDto(Guestbook entity) {
		GuestbookDTO dto = GuestbookDTO.builder()
								  .gno(entity.getGno())
								  .title(entity.getTitle())
								  .content(entity.getContent())
								  .writer(entity.getWriter())
								  .regDate(entity.getRegDate())
								  .modDate(entity.getModDate())
								  .build();
		
		return dto;
	}
}
