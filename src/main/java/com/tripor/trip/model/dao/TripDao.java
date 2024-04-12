package com.tripor.trip.model.dao;

import java.util.List;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;

public interface TripDao {
	List<SidoDto> searchAllSido() throws Exception;
	List<GugunDto> searchGugunBySido(int sidoCode) throws Exception;
	List<TripDto> searchAll(TripSearchDto param) throws Exception;
	List<TripDto> searchAll(String keyword) throws Exception;
	List<TripDto> searchByPlanId(int planId) throws Exception;
	int insertTripPlan(List<String> tripList, String userId, String planName) throws Exception;
	List<TripPlanDto> searchPlansByUserId(String userId) throws Exception;
	TripDto searchByContentId(int contentId) throws Exception;
	TripPlanDto searchPlanByPlanId(int planId) throws Exception;
	int deleteTripPlan(int planId) throws Exception;
}
