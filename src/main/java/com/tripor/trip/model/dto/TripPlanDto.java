package com.tripor.trip.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="TripPlanDto (여행 일정 정보)")
public class TripPlanDto {
	@Schema(description="일정 ID")
	int planId;
	@Schema(description="일정 이름")
	String planName;
	@Schema(description="여행 계획 리스트")
	List<Integer> tripList;
	@Schema(description="사용자 ID")
	String memberId;
	@Schema(description="일정 등록 날짜")
	String planRegisterDate;

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public List<Integer> getTripList() {
		return tripList;
	}

	public void setTripList(List<Integer> tripList) {
		this.tripList = tripList;
	}

	public String getPlanRegisterDate() {
		return planRegisterDate;
	}

	public void setPlanRegisterDate(String planRegisterDate) {
		this.planRegisterDate = planRegisterDate;
	}

	public TripPlanDto() {
		super();
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public TripPlanDto(int planId, String planName, List<Integer> tripList, String memberId, String planRegisterDate) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.tripList = tripList;
		this.memberId = memberId;
		this.planRegisterDate = planRegisterDate;
	}

	@Override
	public String toString() {
		return "TripPlanDto [planId=" + planId + ", planName=" + planName + ", tripList=" + tripList + ", memberId="
				+ memberId + ", planRegisterDate=" + planRegisterDate + "]";
	}

}
