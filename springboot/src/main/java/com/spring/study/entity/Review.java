package com.spring.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@SequenceGenerator(
		name = "REVIEW_SEQ_GEN",
		sequenceName = "review_seq",
		initialValue = 1,
		allocationSize = 1
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"})
public class Review extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
							   generator = "REVIEW_SEQ_GEN")
	private Long reviewnum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	private int grade;
	
	private String text;
	
	public void changeGrade(int grade) {
		this.grade = grade;
	}
	
	public void changeText(String text) {
		this.text = text;
	}
}












