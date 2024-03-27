package com.tripor.trip.model.dto;

public class SidoDto {
	int sidoCode;
	String sidoName;

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}

	public SidoDto(int sidoCode, String sidoName) {
		super();
		this.sidoCode = sidoCode;
		this.sidoName = sidoName;
	}

	public SidoDto() {
		super();
	}

	@Override
	public String toString() {
		return "SidoDto [sidoCode=" + sidoCode + ", sidoName=" + sidoName + "]";
	}

}
