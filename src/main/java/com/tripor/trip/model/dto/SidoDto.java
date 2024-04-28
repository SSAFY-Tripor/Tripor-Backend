package com.tripor.trip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="SidoDto (시도 정보)")
public class SidoDto {
	@Schema(description="시도 코드")
	int sidoCode;
	@Schema(description="시도 이름")
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
