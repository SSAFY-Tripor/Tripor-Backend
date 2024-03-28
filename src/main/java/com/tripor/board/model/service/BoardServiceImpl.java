package com.tripor.board.model.service;

import java.util.List;

import com.tripor.board.model.dao.BoardDao;
import com.tripor.board.model.dao.BoardDaoImpl;
import com.tripor.board.model.dto.BoardDto;

public class BoardServiceImpl implements BoardService{
	private static BoardService boardService = new BoardServiceImpl();
	private BoardDao boardDao;
	
	private BoardServiceImpl() {
		boardDao = BoardDaoImpl.getInstance();
	}

	public static BoardService getInstance() {
		return boardService;
	}

	@Override
	public void writeBoard(BoardDto boardDto) throws Exception {
		boardDao.insert(boardDto);
	}
	

	@Override
	public List<BoardDto> listBoard() throws Exception {
		return boardDao.searchAll();
	}

	@Override
	public BoardDto getBoard(int boardNo) throws Exception {
		return boardDao.searchByNo(boardNo);
	}

	@Override
	public void updateHit(int boardNo) throws Exception {
		boardDao.updateHit(boardNo);
	}

	@Override
	public void modifyBoard(BoardDto boardDto) throws Exception {
		boardDao.update(boardDto);
	}

	@Override
	public void deleteBoard(int boardNo) throws Exception {
		boardDao.delete(boardNo);
	}

}
