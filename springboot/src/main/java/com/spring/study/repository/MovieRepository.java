package com.spring.study.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.study.entity.Movie;
import com.spring.study.search.SearchMovieRepository;
// 인터페이스 형태로 다중 상속 받을 수 있게함
public interface MovieRepository extends JpaRepository<Movie, Long>, SearchMovieRepository{
//	영화 리스트 페이징 + 이미지 + 평점 평균 + 리뷰 수
//	쿼리문이지만 컬럼, 테이블이 아닌 ENTITY
	@Query("SELECT m, mi, "
		 + "	   avg(coalesce(r.grade, 0)), "
		 + "	   count(distinct r) "
		 + "FROM Movie m LEFT JOIN MovieImage mi ON mi.movie = m "
		 + "AND mi.inum = (SELECT MAX(mi2.inum) "
		 + "			   FROM MovieImage mi2	"
		 + "			   WHERE mi2.movie = m) "
		 + "LEFT JOIN Review r On r.movie = m	"
		 + "GROUP BY m, mi")
	Page<Object[]> getListPage(Pageable pageable);
	
	// 영화 상세 정보 + 모든 이미지 + 평점 평균 + 리뷰 수
	@Query("SELECT m,"
		  + "	   mi, "
		  + "	   AVG(coalesce(r.grade, 0)), "
		  + "	   COUNT(r) "
		  + "FROM Movie m LEFT OUTER JOIN MovieImage mi ON mi.movie = m "
		  + "LEFT OUTER JOIN Review r ON r.movie = m "
		  + "WHERE m.mno = :mno "
		  + "GROUP BY m, mi")
	List<Object[]> getMoviewWithAll(@Param("mno") Long mno);
	
}
