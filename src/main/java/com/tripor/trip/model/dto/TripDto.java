package com.tripor.trip.model.dto;

public class TripDto {
	private String contentId;
	private int contentTypeId;
	private String title;
	private String addr;
	private String firstImage;
	private int sidoCode;
	private int gugunCode;
	private String latitude;
	private String longitude;
	private String overview;

	public TripDto(String contentId, int contentTypeId, String title, String addr, String firstImage, int sidoCode,
			int gugunCode, String latitude, String longitude, String overview) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.addr = addr;
		this.firstImage = firstImage;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.overview = overview;
	}

	public TripDto() {
		super();
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "TripDto [contentId=" + contentId + ", contentTypeId=" + contentTypeId + ", title=" + title + ", addr="
				+ addr + ", firstImage=" + firstImage + ", sidoCode=" + sidoCode + ", gugunCode=" + gugunCode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", overview=" + overview + "]";
	}

}
