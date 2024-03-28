package com.tripor.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tripor.board.model.dto.BoardDto;
import com.tripor.util.DBUtil;

public class BoardDaoImpl implements BoardDao{
	private static BoardDao instance = new BoardDaoImpl();
	DBUtil dbUtil = DBUtil.getInstance();

	private BoardDaoImpl() {
	};

	public static BoardDao getInstance() {
		return instance;
	}

	@Override
	public BoardDto searchByNo(int boardNo) throws SQLException {
		BoardDto boardDto = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select * \n");
			sql.append("from board \n");
			sql.append("where board_no = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("board_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setregisterDate(rs.getString("register_date"));
			}
		} finally {
			dbUtil.close(rs, ps, con);
		}
		return boardDto;
	}

	@Override
	public List<BoardDto> searchBySubject(String subject) throws SQLException {
		List<BoardDto> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select board_no, user_id, subject, content, hit, register_date \n");
			sql.append("from board \n");
			sql.append("where subject like ?;");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, "%" + subject + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("board_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setregisterDate(rs.getString("register_date"));
				list.add(boardDto);
			}
		} finally {
			dbUtil.close(rs, ps, con);
		}
		return list;
	}

	@Override
	public List<BoardDto> searchByUserId(String userId) throws SQLException {
		List<BoardDto> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select board_no, user_id, subject, content, hit, register_date \n");
			sql.append("from board \n");
			sql.append("where user_id=?;");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("board_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setregisterDate(rs.getString("register_date"));
				list.add(boardDto);
			}
		} finally {
			dbUtil.close(rs, ps, con);
		}
		return list;
	}

	@Override
	public List<BoardDto> searchAll() throws SQLException {
		List<BoardDto> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select board_no, user_id, subject, content, hit, register_date \n");
			sql.append("from board \n");
			sql.append("order by board_no desc");
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("board_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setregisterDate(rs.getString("register_date"));
				list.add(boardDto);
			}
		} finally {
			dbUtil.close(rs, ps, con);
		}
		return list;
	}

	@Override
	public int update(BoardDto boardDto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("update board set subject=?, content=? where board_no=?");

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, boardDto.getSubject());
			ps.setString(2, boardDto.getContent());
			ps.setInt(3, boardDto.getBoardNo());
			return ps.executeUpdate();
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public void updateHit(int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("update board set hit=hit+1 where board_no=?");

			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, boardNo);
			ps.executeUpdate();
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public int delete(int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("delete from board where board_no = ?");
			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, boardNo);
			return ps.executeUpdate();
		} finally {
			dbUtil.close(ps, con);
		}
	}

	@Override
	public int insert(BoardDto boardDto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into board (user_id, subject, content) \n");
			sql.append("values (?, ?, ?)");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, boardDto.getUserId());
			ps.setString(2, boardDto.getSubject());
			ps.setString(3, boardDto.getContent());
			System.out.println(boardDto);
			return ps.executeUpdate();
		} finally {
			dbUtil.close(ps, con);
		}
	}

}
