package com.tripor.trip.model.dto;

import java.util.List;

public class TripPlanDto {
	int planId;
	String planName;
	List<TripDto> tripList;
	String planUserId;
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

	public List<TripDto> getTripList() {
		return tripList;
	}

	public void setTripList(List<TripDto> tripList) {
		this.tripList = tripList;
	}

	public String getPlanUserId() {
		return planUserId;
	}

	public void setPlanUserId(String planUserId) {
		this.planUserId = planUserId;
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

	public TripPlanDto(int planId, String planName, List<TripDto> tripList, String planUserId,
			String planRegisterDate) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.tripList = tripList;
		this.planUserId = planUserId;
		this.planRegisterDate = planRegisterDate;
	}

	@Override
	public String toString() {
		return "TripPlanDto [planId=" + planId + ", planName=" + planName + ", tripList=" + tripList + ", planUserId="
				+ planUserId + ", planRegisterDate=" + planRegisterDate + "]";
	}

}
