package com.tripor.util;

public enum BoardSize {
	LIST(20), NAVIGATION(5);
	
	private int boardSize;
	private BoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
	public int getBoardSize() {
		return boardSize;
	}
}
