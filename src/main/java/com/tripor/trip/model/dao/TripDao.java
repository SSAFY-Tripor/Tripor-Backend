package com.tripor.trip.model.dao;

import java.util.List;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripSearchDto;

public interface TripDao {
	List<SidoDto> searchAllSido() throws Exception;
	List<GugunDto> searchGugunBySido(int sidoCode) throws Exception;
	List<TripDto> searchAll(TripSearchDto param) throws Exception;
	List<TripDto> searchAll(String keyword) throws Exception;
}
