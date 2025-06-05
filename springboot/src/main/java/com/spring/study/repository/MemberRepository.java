package com.spring.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.study.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
