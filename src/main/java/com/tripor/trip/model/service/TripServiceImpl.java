package com.tripor.trip.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.tripor.trip.model.dao.TripDao;
import com.tripor.trip.model.dao.TripDaoImpl;
import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;

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

public class TripServiceImpl implements TripService {
	TripDao tripDao = TripDaoImpl.getInstance();
	private static TripService instance = new TripServiceImpl();

	private TripServiceImpl() {
	}

	public static TripService getInstance() {
		return instance;
	}

	@Override
	public String getTripList(String keyword) throws Exception {
		List<TripDto> list = tripDao.searchAll(keyword);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}

	@Override
	public String getTripList(TripSearchDto param) throws Exception {
		List<TripDto> list = tripDao.searchAll(param);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}

	@Override
	public String getTrip(int contentId) throws Exception {
		TripDto tripDto = tripDao.searchByContentId(contentId);
		Gson gson = new Gson();
		String json = gson.toJson(tripDto);
		return json;
	}

	@Override
	public String getTripList(int planId) throws Exception {
		List<TripDto> list = tripDao.searchPlanByPlanId(planId).getTripList();
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}

	@Override
	public String getTripList(int planId, String mode) throws Exception {
		List<TripDto> list = tripDao.searchPlanByPlanId(planId).getTripList();
		System.out.println("왔음 mode까지" + list);
		Gson gson = new Gson();
		String json = gson.toJson(shortestPathByGreedy(list, 0));
		System.out.println(json);
		return json;
	}

	private List<TripDto> shortestPathByGreedy(List<TripDto> list, int start) {
//		double[][] Graph = new double[list.size()][list.size()];
		ArrayList<Node>[] tripGraph = new ArrayList[list.size()];
		int N = tripGraph.length;
		for (int i = 0; i < tripGraph.length; i++) {
			tripGraph[i] = new ArrayList<Node>();
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j)
					continue;
				TripDto A = list.get(i);
				TripDto B = list.get(j);
				double weight = latDiff(Double.parseDouble(A.getLatitude()), Double.parseDouble(B.getLatitude()))
						+ lngDiff(Double.parseDouble(A.getLongitude()), Double.parseDouble(B.getLongitude()));
				tripGraph[i].add(new Node(j, weight));
				tripGraph[j].add(new Node(i, weight));
			}
		}

		boolean[] visit = new boolean[N];
		int cur = start;
		List<TripDto> returnList = new ArrayList<>();
		returnList.add(list.get(start));
		visit[cur] = true;
		System.out.println(returnList.size() + " " +list.size());
		while (returnList.size() < list.size()) {
			System.out.println(returnList.size() + " " +list.size());
			double min = Double.MAX_VALUE;
			int minIdx = 0;
			for (Node next : tripGraph[cur]) {
				if (visit[next.next])
					continue;
				if(min > next.weight) {
					min = next.weight;
					minIdx = next.next;
				}
			}
			cur = minIdx;
			visit[cur] = true;
			returnList.add(list.get(cur));
		}
		return returnList;
	}

	private double latDiff(double latA, double latB) {
		return Math.abs(latA - latB);
	}

	private double lngDiff(double lngA, double lngB) {
		return Math.abs(lngA - lngB);
	}

	/*
	 * private int findSet(int x, int[] parents) { if (parents[x] == x) return x;
	 * return parents[x] = findSet(parents[x], parents); }
	 * 
	 * private boolean unionSet(int p, int x, int[] parents) { if (p == x) return
	 * false; parents[x] = p; return true; }
	 * 
	 * private void makeSet(int[] p) { for (int i = 0; i < p.length; i++) { p[i] =
	 * i; } }
	 */

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

	@Override
	public void registerTripPlan(String tripJson, String userId, String planName) throws Exception {
		tripDao.insertTripPlan(tripJson, userId, planName);
	}

	@Override
	public List<TripPlanDto> getTripPlan(String userId) throws Exception {
		List<TripPlanDto> tripPlanDto = tripDao.searchPlansByUserId(userId);
		return tripPlanDto;
	}

	@Override
	public TripPlanDto getTripPlanDetail(int planId) throws Exception {
		return tripDao.searchPlanByPlanId(planId);
	}

	@Override
	public void removeTripPlan(int planId) throws Exception {
		tripDao.deleteTripPlan(planId);
	}

}
