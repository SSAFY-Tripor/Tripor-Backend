package com.tripor.article.model.dto;

public class ArticleDto {
	private int articleId;
	private String memberId;
	private String subject;
	private String content;
	private int hit;
	private String registerDate;

	public ArticleDto(String memberId, String subject, String content) {
		super();
		this.memberId = memberId;
		this.subject = subject;
		this.content = content;
	}

	public ArticleDto(int articleId, String memberId, String subject, String content, int hit, String registerDate) {
		super();
		this.articleId = articleId;
		this.memberId = memberId;
		this.subject = subject;
		this.content = content;
		this.hit = hit;
		this.registerDate = registerDate;
	}

	public ArticleDto() {
		// TODO Auto-generated constructor stub
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "ArticleDto [articleId=" + articleId + ", memberId=" + memberId + ", subject=" + subject + ", content="
				+ content + ", hit=" + hit + ", registerDate=" + registerDate + "]";
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
