package com.tripor.trip.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;
import com.tripor.trip.model.service.TripService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	TripService tripService;

	@GetMapping("/sido")
	public ResponseEntity<?> sidoAll() {
		log.debug("sidoAll method");
		try {
			// pgno, key, word
			List<SidoDto> list = tripService.getAllSido();

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", list);
			returnMap.put("count", list.size());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/{sidoCode}/gugun")
	public ResponseEntity<?> gugunAll(@PathVariable int sidoCode) {
		log.debug("gugunAll sidoCode : {}", sidoCode);
		try {
			List<GugunDto> list = tripService.getAllGugun(sidoCode);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", list);
			returnMap.put("count", list.size());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getAttraction(@PathVariable int contentId) {
		log.debug("getAttraction contentId : {}", contentId);
		try {
			TripDto tripDto = tripService.getTrip(contentId);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("item", tripDto);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/plan")
	public ResponseEntity<?> addTripPlan(@org.springframework.web.bind.annotation.RequestBody TripPlanDto tripPlanDto) {
		log.debug("addTripPlan tripPlanDto : {}", tripPlanDto);
		try {
			tripService.registerTripPlan(tripPlanDto);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("result", "ok");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/plan/{planId}/trip")
	public ResponseEntity<?> getAttractionByPlanId(@PathVariable("planId") int planId) {
		log.debug("getAttractionByPlanId planId : {}", planId);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("mode", "planId");
			map.put("planId", planId);

			List<TripDto> tripDto = tripService.getTripList(map);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", tripDto);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/option")
	public ResponseEntity<?> getAttractionByOption(
			@org.springframework.web.bind.annotation.RequestBody TripSearchDto tripSearchDto) {
		log.debug("getAttractionByOption tripSearchDto : {}", tripSearchDto);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("mode", "option");
			map.put("param", tripSearchDto);

			List<TripDto> list = tripService.getTripList(map);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", list);
			returnMap.put("count", list.size());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getAttractionList(@RequestParam(name = "keyword", required = false) String keyword) {
		log.debug("getAttractionList keyword : {}", keyword);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("mode", "search");
			map.put("keyword", keyword);

			List<TripDto> list = tripService.getTripList(map);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", list);
			returnMap.put("count", list.size());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/plan/{planId}")
	public ResponseEntity<?> getPlanTripList(@PathVariable("planId") int planId) {
		log.debug("getPlanTripList planId : {}", planId);
		try {
			TripPlanDto tripPlanDto = tripService.getTripPlanDetail(planId);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", tripPlanDto);
			System.out.println(tripPlanDto);
			System.out.println(returnMap);

			List<TripDto> tripDtos = new ArrayList<>();
			for (int contentId : tripPlanDto.getTripList()) {
				tripDtos.add(tripService.getTrip(contentId));
			}

			returnMap.put("tripList", tripDtos);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// URL 수정 필요
	@GetMapping("/plan/m/{memberId}")
	public ResponseEntity<?> getPlanTripList(@PathVariable("memberId") String memberId) {
		log.debug("getPlanTripList memberId : {}", memberId);
		try {
			List<TripPlanDto> list = tripService.getTripPlan(memberId);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", list);

			List<List> tripList = new ArrayList<>();
			for (TripPlanDto tripPlanDto : list) {
				List<TripDto> tripDtos = new ArrayList<>();
				for (int contentId : tripPlanDto.getTripList()) {
					tripDtos.add(tripService.getTrip(contentId));
				}
				tripList.add(tripDtos);
			}
			returnMap.put("tripList", tripList);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
