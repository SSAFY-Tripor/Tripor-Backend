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
		String mode = (String) map.get("mode");
		if ("planId".equals(mode)) {
			int planId = (int) map.get("planId");
			return tripMapper.findByPlanId(planId);
		} else if ("option".equals(mode)) {
			TripSearchDto tripSearchDto = (TripSearchDto) map.get("param");
			return tripMapper.findByOption(tripSearchDto);
		} else if ("search".equals(mode)) {
			String keyword = (String) map.get("keyword");
			return tripMapper.findAll(keyword);
		}else if("shortest".equals(mode)) {
			int planId = (int) map.get("planId");
			List<TripDto> list = tripMapper.findByPlanId(planId);
			return shortestPathByTSP(list, 0);
		}

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
	public List<TripPlanDto> getTripPlan(String memberId) throws Exception {
		System.out.println(memberId);
		return tripMapper.findPlanByMemberId(memberId);
	}

	@Override
	public TripPlanDto getTripPlanDetail(int planId) throws Exception {
		return tripMapper.findTripPlanByPlanId(planId);
	}

	@Override
	public void removeTripPlan(int planId) throws Exception {
		tripMapper.deletePlanRelation(planId);
		tripMapper.deletePlan(planId);
	}

	private List<TripDto> shortestPathByTSP(List<TripDto> list, int start) {
//		return tspImplPermutaion(list, start);
		return tspImplDP(list, start);
	}

	private List<TripDto> tspImplDP(List<TripDto> list, int start) {
		int N = list.size();
		if (N > 18) {
			// 많은 범위가 들어온다면, 수행하지 않음. 안전 장치!
			return list;
		}
		double[][] dp, Graph;
		int[][] path;
		Graph = new double[N][N];
		dp = new double[N][(1 << N)];
		path = new int[N][(1 << N)];
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

		for (int i = 0; i < N; i++)
			Arrays.fill(dp[i], -1);
		dfs(start, (1 << start), N, Graph, dp, path, start);

		// 외판원 순회 끝
		// path를 활용하여, 최적 경로 반환
		List<TripDto> returnList = new ArrayList<>();
		int flag = (1 << start);
		returnList.add(list.get(start));
		int last = 0;
		while (flag != (1 << N) - 1) {
			int next = path[last][flag];
			returnList.add(list.get(next));
			flag = (flag | (1 << next));
			last = next;
		}
		return returnList;
	}

	private List<TripDto> tspImplPermutaion(List<TripDto> list, int start) {
		int N = list.size();
		if (N > 12) {
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

	private List<TripDto> shortestPathByGreedy(List<TripDto> list, int start) {
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

	private double dfs(int cur, int visitFlag, int N, double[][] Graph, double[][] dp, int[][] path, int start) {
		// 모든 도시를 지난 경우
		if (visitFlag == (1 << N) - 1) {
			dp[cur][visitFlag] = Graph[cur][start];
			path[cur][visitFlag] = -3;
			return Graph[cur][start]; // 시작 경로로 반환(외판원 돌아가는 부분이라 지금은 필요 없지만, 후에 기차역 또는 공항, 호텔과 연동 가능할 듯
		}

		if (dp[cur][visitFlag] != -1)
			return dp[cur][visitFlag];
		dp[cur][visitFlag] = Double.POSITIVE_INFINITY;

		for (int i = 0; i < N; i++) {
			// now -> 아직 방문하지 않는 i번 도시 가는 경로가 있는 경우
			if ((visitFlag & (1 << i)) == 0 && Graph[cur][i] != 0) {
				// d[i][j] = 현재 있는 도시가 i이고 이미 방문한 도시들의 집합이 j일때,
				// 방문하지 않은 나머지 도시들을 모두 방문한 뒤 출발 도시로 돌아올 때 드는 최소 비용.
				// 즉, 방문해야하는 도시(여기에 시작지점으로 돌아오는 것 포함) 들까지 가는 최소 비용
				double pre = dp[cur][visitFlag];
				dp[cur][visitFlag] = Math.min(dfs(i, visitFlag | (1 << i), N, Graph, dp, path, start) + Graph[cur][i],
						dp[cur][visitFlag]); // 최소비용
				if (pre > dp[cur][visitFlag]) {
					path[cur][visitFlag] = i;
				}
				// 갱신
				// dfs(다음 도시, 다음도시 방문했다고 가정) + 여기서 다음 도시까지의 거리 와 최소거리 비교
			}
		}
		return dp[cur][visitFlag];
	}

	private void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	private double diffDistance(double latA, double latB, double lngA, double lngB) {
		return Math.sqrt(Math.pow(latDiff(latA, latB), 2) + Math.pow(lngDiff(lngA, lngB), 2));
	}

	private double latDiff(double latA, double latB) {
		return Math.abs(latA - latB);
	}

	private double lngDiff(double lngA, double lngB) {
		return Math.abs(lngA - lngB);
	}
}
