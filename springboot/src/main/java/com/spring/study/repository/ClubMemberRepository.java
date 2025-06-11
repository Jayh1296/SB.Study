package com.spring.study.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.study.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String>{

//	부여 받은 권한 확인
//	Optional : Not null를 인식하고 방지 (.isPresent... 등등 )
//	Param : 레거시에는 명시를 안하지만 부트에서는 명시를 해줘야함 (ItelliJ : Param 명시를 설정하는 부분이 존재)
//	EntityGraph : Role 부여 때문에 (fetch : Lazy 지연 로딩) 성능 저하를 처리되게 끔
	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraphType.LOAD)
	@Query("SELECT m FROM ClubMember m WHERE m.fromSocial=:social AND m.email=:email")
	Optional<ClubMember> findByEmail(@Param("email") String email,
									 @Param("social") boolean social);
	
}
