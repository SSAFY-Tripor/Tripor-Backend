package com.tripor.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberLoginDto {
	private String memberId;
	private String memberPw;
	private String saveid;
}
