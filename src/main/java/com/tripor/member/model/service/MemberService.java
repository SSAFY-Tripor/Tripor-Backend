package com.tripor.member.model.service;

import com.tripor.member.model.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {
	int join(MemberDto memberDto);
	MemberDto login(String userId, String userPw);
	String findPassword(String userId);
}
