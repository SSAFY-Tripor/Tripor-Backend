package com.tripor.member.model.service;

import com.tripor.member.model.dao.MemberDao;
import com.tripor.member.model.dao.MemberDaoImpl;

public class MemberServiceImpl implements MemberService{
	private static MemberService instance = new MemberServiceImpl();
	private MemberDao memberDao = MemberDaoImpl.getInstance();
	private MemberServiceImpl() {}
	
	public static MemberService getInstance() {
		return instance;
	}

}
