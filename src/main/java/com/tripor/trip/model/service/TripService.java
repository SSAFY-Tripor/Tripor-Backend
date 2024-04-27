package com.tripor.trip.model.service;

import java.util.List;
import java.util.Map;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;

public interface TripService {
	List<SidoDto> getAllSido() throws Exception;

	List<GugunDto> getAllGugun(int sidoCode) throws Exception;

	TripDto getTrip(int contentId) throws Exception;

	// sido, gugun, type 에 따른 위치
	// keyword 에 따른 위치
	// planId 에 따른 위치
	// sort 존재시 palnId에 따른 위치 TSP 적용
	List<TripDto> getTripList(Map<String, Object> map) throws Exception;

//	// 시도, 구군, 타입의 따른 위치
//	List<TripDto> getTripList(TripSearchDto param) throws Exception;
//
//	// 검색창 키워드 위치
//	List<TripDto> getTripList(String keyword) throws Exception;
//
//	// 계획 번호의 포함된 여행지 리스트
//	List<TripDto> getTripList(int planId) throws Exception;

//	// 계획 번호 포함 + 최단 경로 적용한 순서로 정렬
//	String getTripList(int planId, String mode) throws Exception;

	void registerTripPlan(TripPlanDto tripPlanDto) throws Exception;

	List<TripPlanDto> getTripPlan(String memberId) throws Exception;

	TripPlanDto getTripPlanDetail(int planId) throws Exception;

	void removeTripPlan(int planId) throws Exception;
}
