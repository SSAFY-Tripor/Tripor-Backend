package com.tripor.trip.model.dto;

import java.util.List;

public class TripPlanDto {
	int planId;
	String planName;
	List<String> tripList;
	String userId;
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
	public List<String> getTripList() {
		return tripList;
	}
	public void setTripList(List<String> tripList) {
		this.tripList = tripList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public TripPlanDto(int planId, String planName, List<String> tripList, String userId) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.tripList = tripList;
		this.userId = userId;
	}
	
	
}
