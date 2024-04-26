package com.tripor.trip.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;

@Mapper
public interface TripMapper {
	List<SidoDto> findSidoAll();

	List<GugunDto> findGugunBySido(int sidoCode);

	List<TripDto> findByPlanId(int planId);

	List<TripDto> findByOption(TripSearchDto tripSearchDto);

	List<TripDto> findAll(String keyword);

	TripDto findByContentId(int contentId);

	TripPlanDto findTripPlanByPlanId(int planId);
	
	int findContentIdByPlanId(int planId);

	void insertTripPlan(TripPlanDto tripPlanDto);

	void insertPlanContentRelation(Map<String, Object> map);
}
