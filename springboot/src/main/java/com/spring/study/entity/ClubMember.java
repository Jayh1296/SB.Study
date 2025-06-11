package com.spring.study.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember {

	@Id
	private String email;
	
	private String password;
	
	private String name;
//	소셜 로그인 시 정보를 받아옴 이전 가입 정보와 일치한다면 합쳐서 관리
	private boolean fromSocial;
//	권한을 부여하기 위해 세팅 : 지연 방식
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<ClubMemberRole> roleSet = new HashSet<>();
//	멤버에게 권한을 추가 시키는 메소드
	public void addMemberRole(ClubMemberRole clubMemberRole) {
		roleSet.add(clubMemberRole);
	}
	
}
