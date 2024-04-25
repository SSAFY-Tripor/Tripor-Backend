package com.tripor.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripor.member.model.dto.MemberDto;
import com.tripor.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper memberMapper;

	@Override
	public int idCheck(String memberId) throws Exception {
		return memberMapper.countMemberById(memberId);
	}

	@Override
	public MemberDto loginMember(Map<String, String> map) throws Exception {
		return memberMapper.findByIdAndPassword(map);
	}

	@Override
	public MemberDto getMember(String memberId) throws Exception {
		return memberMapper.findById(memberId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		System.out.println(memberDto);
		memberMapper.insertMember(memberDto);
	}

	@Override
	public void updateMember(MemberDto memberDto) throws Exception {
		memberMapper.updateMember(memberDto);
	}

	@Override
	public void deleteMember(String memberId) throws Exception {
		memberMapper.deleteMember(memberId);
	}

}
