package com.tripor.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tripor.board.model.dto.BoardDto;
import com.tripor.util.DBUtil;

public class BoardDaoImpl implements BoardDao {
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
			sql.append("from article \n");
			sql.append("where article_id = ?");
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("article_id"));
				boardDto.setUserId(rs.getString("member_id"));
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
			sql.append("select article_id, member_id, subject, content, hit, register_date \n");
			sql.append("from article \n");
			sql.append("where subject like ?;");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, "%" + subject + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("article_id"));
				boardDto.setUserId(rs.getString("member_id"));
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
			sql.append("select article_id, member_id, subject, content, hit, register_date \n");
			sql.append("from article \n");
			sql.append("where member_id=?;");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("article_id"));
				boardDto.setUserId(rs.getString("member_id"));
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
	public List<BoardDto> searchAll(Map<String, Object> map) throws SQLException {
		List<BoardDto> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String key = (String) map.get("key");
		String word = (String) map.get("word");

		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select article_id, member_id, subject, content, hit, register_date \n");
			sql.append("from article \n");
			if (!key.isEmpty() && !word.isEmpty()) {
				// 제목은 LIKE, 그외는 == 연산
				if ("subject".equals(key)) {
					sql.append("where subject like concat('%', ? , '%') \n");
				} else {
					sql.append("where ").append(key).append("=?\n");
				}
			}
			sql.append("order by article_id desc \n");
			sql.append("limit ?, ?");
			ps = con.prepareStatement(sql.toString());
			int idx = 0;
			if (!key.isEmpty() && !word.isEmpty()) {
				ps.setString(++idx, word); // word가 없다면, 생략되나봄. 증감연산자 동작 X
			}
			ps.setInt(++idx, (int) map.get("start"));
			ps.setInt(++idx, (int) map.get("listsize"));
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setBoardNo(rs.getInt("article_id"));
				boardDto.setUserId(rs.getString("member_id"));
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
	public int searchCount(Map<String, Object> map) throws SQLException {
		int cnt = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String key = (String) map.get("key");
		String word = (String) map.get("word");
		try {
			con = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select count(article_id) from article \n");
			if (!key.isEmpty() && !word.isEmpty()) {
				// 제목은 LIKE, 그외는 == 연산
				if ("subject".equals(key)) {
					sql.append("where subject like concat('%', ? , '%') \n");
				} else {
					sql.append("where ").append(key).append("=?\n");
				}
			}
			ps = con.prepareStatement(sql.toString());
			if (!key.isEmpty() && !word.isEmpty()) {
				ps.setString(1, word);
			}
			rs = ps.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
			return cnt;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public int update(BoardDto boardDto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("update article set subject=?, content=? where article_id=?");

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
			sb.append("update article set hit=hit+1 where article_id=?");

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
			sb.append("delete from article where article_id = ?");
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
			sql.append("insert into article (member_id, subject, content) \n");
			sql.append("values (?, ?, ?)");
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, boardDto.getUserId());
			ps.setString(2, boardDto.getSubject());
			ps.setString(3, boardDto.getContent());
			return ps.executeUpdate();
		} finally {
			dbUtil.close(ps, con);
		}
	}

}
