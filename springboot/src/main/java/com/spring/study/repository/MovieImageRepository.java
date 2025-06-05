package com.spring.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.study.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>{

}
