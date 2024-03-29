package com.tripor.trip.model.service;

import java.util.List;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripSearchDto;

public interface TripService {
	String getAllSido() throws Exception;

	String getAllGugun(int sidoCode) throws Exception;

	String getTripList(TripSearchDto param) throws Exception;
	String getTripList(String keyword) throws Exception;
}
