package com.spring.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@SequenceGenerator(
		name = "MEMBER_SEQ_GEN",
		sequenceName = "member_seq",
		initialValue = 1,
		allocationSize = 1
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "m_member")
public class Member extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
							   generator = "MEMBER_SEQ_GEN")
	private Long mId;
	
	private String email;
	
	private String pw;
	
	private String nickname;
}










