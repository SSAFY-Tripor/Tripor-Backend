package com.tripor.trip.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;

@Mapper
public interface TripMapper {
	List<SidoDto> findSidoAll();

	List<GugunDto> findGugunBySido(int sidoCode);

	TripDto findByContentId(int contentId);
	
	void insertTripPlan(TripPlanDto tripPlanDto);
	
	void insertPlanContentRelation(Map<String, Object> map);
}
