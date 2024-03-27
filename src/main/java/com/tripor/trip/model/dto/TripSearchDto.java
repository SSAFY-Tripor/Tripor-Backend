package com.tripor.trip.model.dto;

public class TripSearchDto {
	private String sido;
	private String gugun;
	private String type;

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TripSearchDto() {
		super();
	}

	public TripSearchDto(String sido, String gugun, String type) {
		super();
		this.sido = sido;
		this.gugun = gugun;
		this.type = type;
	}

	@Override
	public String toString() {
		return "TripSearchDto [sido=" + sido + ", gugun=" + gugun + ", type=" + type + "]";
	}

}
