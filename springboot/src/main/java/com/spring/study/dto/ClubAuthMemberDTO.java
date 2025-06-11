package com.spring.study.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User{

//	각 기능 알아보기
//	public ClubAuthMemberDTO(String username, String password, boolean enabled, boolean accountNonExpired,
//			boolean credentialsNonExpired, boolean accountNonLocked,
//			Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//		// TODO Auto-generated constructor stub
//	}
	private String email;
	
	private String name;
	
	private boolean fromSocial;
	
//	제네릭 기법 <? extends GratedAuthority> : GrantedAuthority를 상속 받아 그 타입으로 담음 (어떤 타입이 올지 모르기 때문에 ? 로 표기 )
	public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
							 Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		
		this.email = username;
		this.fromSocial = fromSocial;
	}
}
