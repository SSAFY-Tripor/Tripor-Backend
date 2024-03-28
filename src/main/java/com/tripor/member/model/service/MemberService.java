package com.tripor.member.model.service;

import com.tripor.member.model.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {
	int join(MemberDto memberDto);
	MemberDto login(String userId, String userPw);
	MemberDto findById(String userId);
	String findPassword(String userId);
	void remove(String userId);
	void modify(MemberDto memberDto);
}
