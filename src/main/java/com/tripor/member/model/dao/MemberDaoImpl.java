package com.tripor.member.model.dao;

public class MemberDaoImpl implements MemberDao{
	private static MemberDao instance = new MemberDaoImpl();
	private MemberDaoImpl() {}
	public static MemberDao getInstance() { return instance; }

}
