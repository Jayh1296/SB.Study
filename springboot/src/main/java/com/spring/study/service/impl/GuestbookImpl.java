package com.spring.study.service.impl;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.study.dto.GuestbookDTO;
import com.spring.study.dto.PageRequestDTO;
import com.spring.study.dto.PageResultDTO;
import com.spring.study.entity.Guestbook;
import com.spring.study.entity.QGuestbook;
import com.spring.study.repository.GuestbookRepository;
import com.spring.study.service.IGuestbookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service("IGuestbookService")
@Log4j2
@RequiredArgsConstructor
public class GuestbookImpl implements IGuestbookService{
	
	private final GuestbookRepository repository;
	
	@Override
	public Long register(GuestbookDTO dto) {
		log.info("dto-------------------------------");
		log.info("dto ====> {}", dto);
		
		Guestbook entity = dtoToEntity(dto);
		
		log.info("entity ====> {}", entity);
		
		repository.save(entity);
		
		return entity.getGno();
	}

	@Override
	public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
		
		BooleanBuilder booleanBuilder = getSearch(requestDTO);
		
//		Page<Guestbook> result = repository.findAll(pageable);
//		검색 결과 값과 페이지를 같이 반환하여 결과에 따라 페이지는 변화함
		Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);
		
		Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
		
		return new PageResultDTO<>(result, fn);
	}

//	검색 기능 (조건에 맞춰 멀티 검색하여 반환)
	private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
		
		String type = requestDTO.getType();
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		QGuestbook qGuestbook = QGuestbook.guestbook;
		
		String keyword = requestDTO.getKeyword();
		
		BooleanExpression expression = qGuestbook.gno.gt(0L);
		
		booleanBuilder.and(expression);
		
//		검색 조건이 없을 경우
		if (type == null || type.trim().length() == 0) {
			return booleanBuilder;
		}
		
		BooleanBuilder conditionBuilder = new BooleanBuilder();
//		t, c, w 는 화면에서 넘어오는 값
		if (type.contains("t")) {
			conditionBuilder.or(qGuestbook.title.contains(keyword));
		}
		if (type.contains("c")) {
			conditionBuilder.or(qGuestbook.content.contains(keyword));
		}
		if (type.contains("w")) {
			conditionBuilder.or(qGuestbook.writer.contains(keyword));
		}
		
		booleanBuilder.and(conditionBuilder);
		
		return booleanBuilder;
	}

}
