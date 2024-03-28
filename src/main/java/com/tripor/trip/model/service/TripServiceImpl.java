package com.tripor.trip.model.service;

import java.util.List;

import com.google.gson.Gson;
import com.tripor.trip.model.dao.TripDao;
import com.tripor.trip.model.dao.TripDaoImpl;
import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripSearchDto;

public class TripServiceImpl implements TripService{
	TripDao tripDao = TripDaoImpl.getInstance();
	private static TripService instance = new TripServiceImpl();
	private TripServiceImpl() {}
	public static TripService getInstance() {
		return instance;
	}
	
	
	@Override
	public String getTripList(TripSearchDto param) throws Exception {
		List<TripDto> list = tripDao.searchAll(param);
		Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
	}
	@Override
	public String getAllSido() throws Exception {
		List<SidoDto> list = tripDao.searchAllSido();
		Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
	}
	@Override
	public String getAllGugun(int sidoCode) throws Exception {
		List<GugunDto> list = tripDao.searchGugunBySido(sidoCode);
		Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
	}
	
	
}
