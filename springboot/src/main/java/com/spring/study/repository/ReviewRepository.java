package com.spring.study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.study.entity.Member;
import com.spring.study.entity.Movie;
import com.spring.study.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

//	EntityGraph : 지연로딩이 아닌 즉시로딩 할 수 있게 끔
	@EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
	List<Review> findByMovie(Movie movie);

//	method 쿼리 방식
//	void deleteByMember(Member member);
	
//	어노테이션 쿼리로 수정 ( 내용 변경 발생 시 @Modifying 사용 )
	@Modifying
	@Query("DELETE FROM Review r "
		 + "WHERE r.member=:member")
	void deleteByMember(@Param("member") Member member);
}
