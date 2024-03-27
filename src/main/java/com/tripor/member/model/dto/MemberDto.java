package com.tripor.member.model.dto;

public class MemberDto {
	private String userId, userPw, userName, emailId, emailDomain;
	private int sido, gugun;
	private String joinDate;

	public MemberDto(String userId, String userPw, String userName, String emailId, String emailDomain, int sido,
			int gugun, String joinDate) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.sido = sido;
		this.gugun = gugun;
		this.joinDate = joinDate;
	}
	
	public MemberDto(String userId, String userPw, String userName, String emailId, String emailDomain, int sido,
			int gugun) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.sido = sido;
		this.gugun = gugun;
	}

	public MemberDto() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public int getSido() {
		return sido;
	}

	public void setSido(int sido) {
		this.sido = sido;
	}

	public int getGugun() {
		return gugun;
	}

	public void setGugun(int gugun) {
		this.gugun = gugun;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userPw=" + userPw + ", userName=" + userName + ", emailId=" + emailId
				+ ", emailDomain=" + emailDomain + ", sido=" + sido + ", gugun=" + gugun + ", joinDate=" + joinDate
				+ "]";
	}
}
