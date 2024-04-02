package com.tripor.board.model.service;

import java.util.List;
import java.util.Map;

import com.tripor.board.model.dto.BoardDto;
import com.tripor.util.PageNavigation;

public interface BoardService {
	void writeBoard(BoardDto boardDto) throws Exception;

	List<BoardDto> listBoard(Map<String, Object> map) throws Exception;

	BoardDto getBoard(int boardNo) throws Exception;
	PageNavigation makePageNavigation(Map<String, Object> map) throws Exception;

	void updateHit(int boardNo) throws Exception;

	void modifyBoard(BoardDto boardDto) throws Exception;

	void deleteBoard(int boardNo) throws Exception;

}
