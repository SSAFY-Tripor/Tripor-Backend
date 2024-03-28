package com.tripor.board.model.dto;

public class BoardDto {
	private int boardNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private String registerDate;
	
	public BoardDto(String userId, String subject, String content) {
		super();
		this.userId = userId;
		this.subject = subject;
		this.content = content;
	}

	public BoardDto(int boardNo, String userId, String subject, String content, int hit, String registerDate) {
		super();
		this.boardNo = boardNo;
		this.userId = userId;
		this.subject = subject;
		this.content = content;
		this.hit = hit;
		this.registerDate = registerDate;
	}

	public BoardDto() {
		super();
	}

	@Override
	public String toString() {
		return "BoardDto [boardNo=" + boardNo + ", userId=" + userId + ", subject=" + subject + ", content=" + content
				+ ", hit=" + hit + ", registerDate=" + registerDate + "]";
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getregisterDate() {
		return registerDate;
	}

	public void setregisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

}
