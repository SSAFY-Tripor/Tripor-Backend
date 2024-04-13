package com.tripor.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tripor.member.model.dto.MemberDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.util.DBUtil;

public class MemberDaoImpl implements MemberDao {
	private static MemberDao instance = new MemberDaoImpl();

	private MemberDaoImpl() {
	}

	public static MemberDao getInstance() {
		return instance;
	}

	private DBUtil dbUtil = DBUtil.getInstance();

	@Override
	public MemberDto searchByIdAndPassword(String userId, String userPw) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MemberDto memberDto = null;
		try {
			con = dbUtil.getConnection();
			String sql = "select * from member where member_id = ? and member_pw = ?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, userPw);
			rs = ps.executeQuery();
			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setUserId(rs.getString("member_id"));
				memberDto.setUserPw(rs.getString("member_pw"));
				memberDto.setUserName(rs.getString("member_name"));
				memberDto.setEmailId(rs.getString("email_id"));
				memberDto.setEmailDomain(rs.getString("email_domain"));
				memberDto.setSido(rs.getInt("sido"));
				memberDto.setGugun(rs.getInt("gugun"));
				memberDto.setJoinDate(rs.getString("join_date"));
			}
			return memberDto;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public MemberDto searchById(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MemberDto memberDto = null;
		try {
			con = dbUtil.getConnection();
			String sql = "select * from member where member_id = ?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setUserId(rs.getString("member_id"));
				memberDto.setUserPw(rs.getString("member_pw"));
				memberDto.setUserName(rs.getString("member_name"));
				memberDto.setEmailId(rs.getString("email_id"));
				memberDto.setEmailDomain(rs.getString("email_domain"));
				memberDto.setSido(rs.getInt("sido"));
				memberDto.setGugun(rs.getInt("gugun"));
				memberDto.setJoinDate(rs.getString("join_date"));
			}
			return memberDto;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public int delete(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbUtil.getConnection();
			String sql = "delete from member where member_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			return ps.executeUpdate();
		} finally {
			dbUtil.close(ps, con);
		}
	}

	@Override
	public int insert(MemberDto memberDto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbUtil.getConnection();
			String sql = "insert into member (member_id, member_pw, member_name, email_id, email_domain, sido, gugun) "
					+ "values (?, ?, ?, ?, ?, ?, ?);";
			ps = con.prepareStatement(sql);
			ps.setString(1, memberDto.getUserId());
			ps.setString(2, memberDto.getUserPw());
			ps.setString(3, memberDto.getUserName());
			ps.setString(4, memberDto.getEmailId());
			ps.setString(5, memberDto.getEmailDomain());
			ps.setInt(6, memberDto.getSido());
			ps.setInt(7, memberDto.getGugun());
			return ps.executeUpdate();
		} finally {
			dbUtil.close(ps, con);
		}
	}

	@Override
	public int update(MemberDto memberDto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbUtil.getConnection();
			String sql = "update member set member_name=?, email_id=?, email_domain=?, member_pw=? where member_id=?;";
			ps = con.prepareStatement(sql);
			ps.setString(1, memberDto.getUserName());
			ps.setString(2, memberDto.getEmailId());
			ps.setString(3, memberDto.getEmailDomain());
			ps.setString(4, memberDto.getUserPw());
			ps.setString(5, memberDto.getUserId());
			return ps.executeUpdate();
		} finally {
			dbUtil.close(ps, con);
		}
	}
	
	

}
