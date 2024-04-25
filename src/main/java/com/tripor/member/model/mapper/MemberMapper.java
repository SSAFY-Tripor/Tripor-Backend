package com.tripor.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripor.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	int countMemberById(String memberId);

	MemberDto findByIdAndPassword(Map<String, String> map);

	MemberDto findById(String memberId);

	void insertMember(MemberDto memberDto);

	void updateMember(MemberDto memberDto);

	void deleteMember(String memberId);
}
