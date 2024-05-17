package com.tripor.trip.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(title="TripPlanDto (여행 일정 정보)")
public class TripPlanDto {
	@Schema(description="일정 ID")
	int planId;
	@Schema(description="일정 이름")
	String planName;
	@Schema(description="여행 계획 리스트")
	List<Integer> tripList;
	@Schema(description="사용자 ID")
	String memberId;
	@Schema(description="일정 등록 날짜")
	String planRegisterDate;
	@Schema(description = "여행 계획 메모")
	String memo;
}
