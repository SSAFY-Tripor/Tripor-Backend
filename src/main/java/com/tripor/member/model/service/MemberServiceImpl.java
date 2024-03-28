package com.tripor.member.model.service;

import java.io.IOException;
import java.sql.SQLException;

import com.tripor.member.model.dao.MemberDao;
import com.tripor.member.model.dao.MemberDaoImpl;
import com.tripor.member.model.dto.MemberDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class MemberServiceImpl implements MemberService{
	private static MemberService instance = new MemberServiceImpl();
	private MemberDao memberDao = MemberDaoImpl.getInstance();
	private MemberServiceImpl() {}
	
	public static MemberService getInstance() {
		return instance;
	}

	@Override
	public int join(MemberDto memberDto) {
		try {
			return memberDao.insert(memberDto);
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public MemberDto login(String userId, String userPw) {
		MemberDto loginUser;
		try {
			loginUser = memberDao.searchByIdAndPassword(userId, userPw);
			if(loginUser == null) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return loginUser;
	}
	
	@Override
	public String findPassword(String userId) {
		MemberDto user;
		try {
			user = memberDao.searchById(userId);
			return user.getUserPw();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MemberDto findById(String userId) {
		MemberDto user;
		try {
			user = memberDao.searchById(userId);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
