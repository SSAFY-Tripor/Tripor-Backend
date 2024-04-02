package com.tripor.board.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tripor.board.model.dto.BoardDto;

public interface BoardDao {
	BoardDto searchByNo(int boardNo) throws SQLException;
	List<BoardDto> searchBySubject(String title) throws SQLException;
	List<BoardDto> searchByUserId(String userId) throws SQLException;
	List<BoardDto> searchAll(Map<String, Object> map) throws SQLException;
	int searchCount(Map<String, Object> map) throws SQLException;
	int update(BoardDto boardDto) throws SQLException;
	void updateHit(int boardNo) throws SQLException;
	int delete(int boardNo) throws SQLException;
	int insert(BoardDto boardDto) throws SQLException;
}
