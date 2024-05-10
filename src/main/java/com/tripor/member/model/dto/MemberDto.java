package com.tripor.member.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="MemberDto (회원정보)")
public class MemberDto {
	@Schema(description="아이디")
	private String memberId;
	@Schema(description="비밀번호")
	private String memberPw;
	@Schema(description="이름")
	private String memberName;
	@Schema(description="이메일아이디")
	private String emailId;
	@Schema(description="이메일도메인")
	private String emailDomain;
	@Schema(description="시도")
	private int sido;
	@Schema(description="구군")
	private int gugun;
	@Schema(description="가입일")
	private String joinDate;
	@Schema(description = "refreshToken")
	private String refreshToken;

	public MemberDto(String memberId, String memberPw, String memberName, String emailId, String emailDomain, int sido,
			int gugun, String joinDate, String refreshToken) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.sido = sido;
		this.gugun = gugun;
		this.joinDate = joinDate;
		this.refreshToken = refreshToken;
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

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "MemberDto [memberId=" + memberId + ", memberPw=" + memberPw + ", memberName=" + memberName
				+ ", emailId=" + emailId + ", emailDomain=" + emailDomain + ", sido=" + sido + ", gugun=" + gugun
				+ ", joinDate=" + joinDate + ", refreshToken=" + refreshToken + "]";
	}

}
