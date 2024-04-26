package com.tripor.trip.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;
import com.tripor.trip.model.mapper.TripMapper;

class Node implements Comparable<Node> {
	int next;
	double weight;

	public Node(int next, double weight) {
		super();
		this.next = next;
		this.weight = weight;
	}

	@Override
	public int compareTo(Node o) {
		return Double.compare(this.weight, o.weight);
	}

}

@Service
public class TripServiceImpl implements TripService {
	
	@Autowired
	TripMapper tripMapper;

	@Override
	public List<SidoDto> getAllSido() throws Exception {
		return tripMapper.findSidoAll();
	}

	@Override
	public List<GugunDto> getAllGugun(int sidoCode) throws Exception {
		return tripMapper.findGugunBySido(sidoCode);
	}

	@Override
	public TripDto getTrip(int contentId) throws Exception {
		return tripMapper.findByContentId(contentId);
	}

	@Override
	public List<TripDto> getTripList(Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public void registerTripPlan(TripPlanDto tripPlanDto) throws Exception {
		tripMapper.insertTripPlan(tripPlanDto);
		Map<String, Object> map = new HashMap<>();
		map.put("planId", tripPlanDto.getPlanId()); // 여기가 안들어가
		map.put("contentIds", tripPlanDto.getTripList());
		tripMapper.insertPlanContentRelation(map);
	}

	@Override
	public List<TripPlanDto> getTripPlan(String userId) throws Exception {
		return null;
	}

	@Override
	public TripPlanDto getTripPlanDetail(int planId) throws Exception {
		return null;
	}

	@Override
	public void removeTripPlan(int planId) throws Exception {
		
	}
	
	

}
