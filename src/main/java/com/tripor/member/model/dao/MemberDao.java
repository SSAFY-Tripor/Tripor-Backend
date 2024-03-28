package com.tripor.member.model.dao;

import java.sql.SQLException;

import com.tripor.member.model.dto.MemberDto;

public interface MemberDao {
	MemberDto searchByIdAndPassword(String userId, String userPw) throws SQLException;
	MemberDto searchById(String userId) throws SQLException;
	int insert(MemberDto memberDto) throws SQLException;
	int delete(String userId) throws SQLException;
}
