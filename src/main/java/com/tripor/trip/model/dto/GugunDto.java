package com.tripor.trip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="GugunDto (구군 정보)")
public class GugunDto {
	@Schema(description="구군 코드")
	int gugunCode;
	@Schema(description="구군 이름")
	String gugunName;
	@Schema(description="시도 코드")
	int sidoCode;

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	@Override
	public String toString() {
		return "GugunDto [gugunCode=" + gugunCode + ", gugunName=" + gugunName + ", sidoCode=" + sidoCode + "]";
	}

	public GugunDto(int gugunCode, String gugunName, int sidoCode) {
		super();
		this.gugunCode = gugunCode;
		this.gugunName = gugunName;
		this.sidoCode = sidoCode;
	}

	public GugunDto() {
		super();
	}

}

