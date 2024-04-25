package com.tripor.member.model.dto;

public class MemberDto {
	private String memberId, memberPw, memberName, emailId, emailDomain;
	private int sido, gugun;
	private String joinDate;

	public MemberDto(String memberId, String memberPw, String memberName, String emailId, String emailDomain, int sido,
			int gugun, String joinDate) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.sido = sido;
		this.gugun = gugun;
		this.joinDate = joinDate;
	}

	public MemberDto(String memberId, String memberPw, String memberName, String emailId, String emailDomain, int sido,
			int gugun) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.sido = sido;
		this.gugun = gugun;
	}

	public MemberDto() {
		// TODO Auto-generated constructor stub
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
		return "MemberDto [memberId=" + memberId + ", memberPw=" + memberPw + ", memberName=" + memberName
				+ ", emailId=" + emailId + ", emailDomain=" + emailDomain + ", sido=" + sido + ", gugun=" + gugun
				+ ", joinDate=" + joinDate + "]";
	}

}
