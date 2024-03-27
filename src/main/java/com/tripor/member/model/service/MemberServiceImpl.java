package com.tripor.member.model.service;

import com.tripor.member.model.dao.MemberDao;
import com.tripor.member.model.dao.MemberDaoImpl;
import com.tripor.member.model.dto.MemberDto;

public class MemberServiceImpl implements MemberService{
	private static MemberService instance = new MemberServiceImpl();
	private MemberDao memberDao = MemberDaoImpl.getInstance();
	private MemberServiceImpl() {}
	
	public static MemberService getInstance() {
		return instance;
	}

	@Override
	public int join(MemberDto memberDto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberDto login(String userId, String userPw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findPassword(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
