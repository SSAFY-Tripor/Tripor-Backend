package com.tripor.trip.model.service;

import java.util.ArrayList;
import java.util.Arrays;
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
//		String json = gson.toJson(shortestPathByGreedy(list, 0));
		String json = gson.toJson(shortestPathByTSP(list, 0));
		System.out.println(json);
		return json;
	}
	/*
	 * private List<TripDto> shortestPathByTSP(List<TripDto> list, int start) { int
	 * N = list.size(); double[][] Graph = new double[N][N];
	 * 
	 * for (int i = 0; i < N; i++) { for (int j = 0; j < N; j++) { if (i == j)
	 * continue; TripDto A = list.get(i); TripDto B = list.get(j); double weight =
	 * latDiff(Double.parseDouble(A.getLatitude()),
	 * Double.parseDouble(B.getLatitude())) +
	 * lngDiff(Double.parseDouble(A.getLongitude()),
	 * Double.parseDouble(B.getLongitude())); Graph[i][j] = weight; } } int[] perm =
	 * new int[N]; double ans = Double.POSITIVE_INFINITY; int[] answer = new int[N];
	 * for (int i = 0; i < N; i++) { perm[i] = i; } do { int sum = 0; boolean flag =
	 * true; for (int i = 0; i < N; i++) { double edge = Graph[perm[i]][perm[(i + 1)
	 * % N]]; sum += edge; } if(ans > sum){ ans = sum; for (int i = 0; i < N; i++) {
	 * answer[i] = perm[i]; } } } while (np(perm, N));
	 * 
	 * System.out.println(ans); for (int i = 0; i < N; i++) {
	 * System.out.println(answer[i]); } return null; } private boolean np(int[] p,
	 * int N) { int i = N - 1; while (i > 0 && p[i - 1] > p[i]) { i--; } if (i == 0)
	 * return false; int j = N - 1; while (p[i - 1] > p[j]) { j--; } swap(p, i - 1,
	 * j); int k = N - 1; while (i < k) { swap(p, i++, k--); } return true; }
	 * 
	 * private void swap(int[] arr, int i, int j) { int tmp = arr[i]; arr[i] =
	 * arr[j]; arr[j] = tmp; }
	 */

	private List<TripDto> shortestPathByTSP(List<TripDto> list, int start) {
		return tspImplPermutaion(list, start);
	}

	private List<TripDto> tspImplPermutaion(List<TripDto> list, int start) {	
		int N = list.size();
		if(N > 10) {
			// 많은 범위가 들어온다면, 수행하지 않음. 안전 장치!
			return list;
		}
		int[] perm = new int[N - 1];
		double[][] Graph = new double[N][N];

		for (int i = 0, idx = 0; i < N; i++) {
			if (i == start)
				continue;
			perm[idx++] = i;
		}
		double answer = Double.POSITIVE_INFINITY;
		List<TripDto> maxList = null;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j)
					continue;
				TripDto A = list.get(i);
				TripDto B = list.get(j);
				double weight = latDiff(Double.parseDouble(A.getLatitude()), Double.parseDouble(B.getLatitude()))
						+ lngDiff(Double.parseDouble(A.getLongitude()), Double.parseDouble(B.getLongitude()));
				Graph[i][j] = weight;
			}
		}
		System.out.println(Arrays.toString(perm));
		do {
			double sum = Graph[start][perm[0]];
			for (int i = 0; i < perm.length - 1; i++) {
				double edge = Graph[perm[i]][perm[i + 1]];
				sum += edge;
			}
			if (answer > sum) {
				answer = sum;
				maxList = new ArrayList<>();
				maxList.add(list.get(start));
				for (int i = 0; i < N - 1; i++) {
					maxList.add(list.get(perm[i]));
				}
			}
		} while (np(perm, perm.length));
		return maxList;
	}

	private boolean np(int[] p, int N) {
		int i = N - 1;
		while (i > 0 && p[i - 1] > p[i]) {
			i--;
		}
		if (i == 0)
			return false;
		int j = N - 1;
		while (p[i - 1] > p[j]) {
			j--;
		}
		swap(p, i - 1, j);
		int k = N - 1;
		while (i < k) {
			swap(p, i++, k--);
		}
		return true;
	}

	private void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

//	private List<TripDto> shortestPathByTSP(List<TripDto> list, int start) {
//		int N = list.size();
//		int END = (1 << N) - 1;
//		double[][] Graph = new double[N][N];
//		double[][] dp = new double[N][1 << N]; // ex) 1 << 5 = 100000(2) = 32 -> 우리의 최대값 : 11111(2) 이므로 1 빼기
//		List<Integer> shortestPath = new ArrayList<>();
//
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				if (i == j)
//					continue;
//				TripDto A = list.get(i);
//				TripDto B = list.get(j);
//				double weight = latDiff(Double.parseDouble(A.getLatitude()), Double.parseDouble(B.getLatitude()))
//						+ lngDiff(Double.parseDouble(A.getLongitude()), Double.parseDouble(B.getLongitude()));
//				Graph[i][j] = weight;
//			}
//		}
//
//		double shortestPathLength = tsp(0, 1, N, dp, Graph, shortestPath);
//		List<TripDto> returnList = new ArrayList<>();
//		System.out.println(shortestPath);
//		for (int i = 0; i < N; i++) {
//			returnList.add(list.get(shortestPath.get(i)));
//		}
//		return returnList;
//	}

	// 동적 계획법으로 최단 경로 구하기
	static double tsp(int node, int visited, int N, double[][] dp, double[][] graph, List<Integer> shortestPath) {
		if (visited == (1 << N) - 1) { // 모든 노드를 방문한 경우
			shortestPath = new ArrayList<>();
			shortestPath.add(node); // 출발 노드 추가
			shortestPath.add(0); // 출발 노드로 돌아가는 노드 추가
			return graph[node][0]; // 출발 노드로 돌아가는 비용 반환
		}

		if (dp[node][visited] != 0) { // 이미 계산한 값이 있는 경우
			return dp[node][visited];
		}

		double minCost = Double.POSITIVE_INFINITY;
		int nextNode = -1;

		for (int i = 0; i < N; i++) {
			if ((visited & (1 << i)) == 0 && graph[node][i] != 0) {
				double cost = graph[node][i] + tsp(i, visited | (1 << i), N, dp, graph, shortestPath);
				if (cost < minCost) {
					minCost = cost;
					nextNode = i;
				}
			}
		}

		if (nextNode != -1) {
			dp[node][visited] = minCost;
			shortestPath = new ArrayList<>();
			shortestPath.add(node); // 현재 노드 추가
			shortestPath.addAll(shortestPath); // 이전 경로 추가
			return minCost;
		} else {
			return dp[node][visited] = Double.POSITIVE_INFINITY;
		}
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
		System.out.println(returnList.size() + " " + list.size());
		while (returnList.size() < list.size()) {
			System.out.println(returnList.size() + " " + list.size());
			double min = Double.MAX_VALUE;
			int minIdx = 0;
			for (Node next : tripGraph[cur]) {
				if (visit[next.next])
					continue;
				if (min > next.weight) {
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
