package com.tripor.member.model.service;

import java.util.Map;

import com.tripor.member.model.dto.MemberDto;

public interface MemberService {
	int idCheck(String memberId) throws Exception;

	MemberDto loginMember(Map<String, String> map) throws Exception;

	MemberDto getMember(String memberId) throws Exception;

	void joinMember(MemberDto memberDto) throws Exception;

	void updateMember(MemberDto memberDto) throws Exception;

	void deleteMember(String memberId) throws Exception;

	void saveRefreshToken(String memberId, String refreshToken) throws Exception;

	Object getRefreshToken(String memberId) throws Exception;

	void deleRefreshToken(String memberId) throws Exception;
}
