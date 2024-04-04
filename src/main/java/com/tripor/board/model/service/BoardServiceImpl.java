package com.tripor.board.model.service;

import java.util.List;
import java.util.Map;

import com.tripor.board.model.dao.BoardDao;
import com.tripor.board.model.dao.BoardDaoImpl;
import com.tripor.board.model.dto.BoardDto;
import com.tripor.util.BoardSize;
import com.tripor.util.PageNavigation;

public class BoardServiceImpl implements BoardService {
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
	public List<BoardDto> listBoard(Map<String, Object> map) throws Exception {
		int pgno = (int) map.get("pgno");
		int listSize = BoardSize.LIST.getBoardSize();
		int start = (pgno - 1) * listSize;
		map.put("start", start);
		map.put("listsize", listSize);
		return boardDao.searchAll(map);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, Object> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		int currentPage = (int) map.get("pgno");
		int naviSize = BoardSize.NAVIGATION.getBoardSize();
		int listSize = BoardSize.LIST.getBoardSize();
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = boardDao.searchCount(map);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / listSize + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = (currentPage <= naviSize);
		pageNavigation.setStartRange(startRange);
		boolean endRange = ((totalPageCount - 1) / naviSize * naviSize < currentPage);
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
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
