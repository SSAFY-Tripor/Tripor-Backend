package com.tripor.trip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="TripDto (여행 정보)")
public class TripDto {
	@Schema(description="콘텐츠 ID")
	private String contentId;
	@Schema(description="콘텐츠 타입 ID")
	private int contentTypeId;
	@Schema(description="장소")
	private String title;
	@Schema(description="주소")
	private String addr;
	@Schema(description="이미지 주소")
	private String firstImage;
	@Schema(description="시도 코드")
	private int sidoCode;
	@Schema(description="구군 코드")
	private int gugunCode;
	@Schema(description="위도")
	private String latitude; // 위도 (-90 ~ 90, 적도가 0도이며, 한국은 33 ~ 43도에 위치)
	@Schema(description="경도")
	private String longitude; // 경도 (-180 ~ 180, 한국은 124 ~ 132도에 위치)
	@Schema(description="번호")
	private String tel;
	@Schema(description="장소 설명")
	private String description;

	public TripDto(String contentId, int contentTypeId, String title, String addr, String firstImage, int sidoCode,
			int gugunCode, String latitude, String longitude, String tel, String description) {
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
		this.tel = tel;
		this.description = description;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TripDto [contentId=" + contentId + ", contentTypeId=" + contentTypeId + ", title=" + title + ", addr="
				+ addr + ", firstImage=" + firstImage + ", sidoCode=" + sidoCode + ", gugunCode=" + gugunCode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", tel=" + tel + ", description="
				+ description + "]";
	}

}
