package com.tripor.member.controller;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripor.member.model.dto.MemberDto;
import com.tripor.member.model.dto.MemberLoginDto;
import com.tripor.member.model.service.MemberService;
import com.tripor.util.JWTUtil;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
@CrossOrigin("*")
@Tag(name = "회원관리")
public class MemberController {
	@Autowired
	MemberService memberService;
	@Autowired
	JWTUtil jwtUtil;

	// ============================== REST API =================================
	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<?> login(@org.springframework.web.bind.annotation.RequestBody MemberLoginDto memberLoginDto) {
		String memberId = memberLoginDto.getMemberId();
		String memberPw = memberLoginDto.getMemberPw();
		String saveid = memberLoginDto.getSaveid();
		log.debug("login Form memberId : {}, memberPw : {}", memberId, memberPw);

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			Map<String, String> map = new HashMap<>();
			map.put("memberId", memberId);
			map.put("memberPw", memberPw);

			MemberDto memberDto = memberService.loginMember(map);
			log.info("memberDto - {}", memberDto);
			if (memberDto != null) {
				String accessToken = jwtUtil.createAccessToken(memberId);
				String refreshToken = jwtUtil.createRefreshToken(memberId);
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);

				// 발급받은 refresh token 을 DB에 저장.
				memberService.saveRefreshToken(memberId, refreshToken);

				resultMap.put("result", "ok");
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);

				status = HttpStatus.CREATED;
			} else {
				resultMap.put("result", "err");
				resultMap.put("message", "아이디 또는 패스워드를 확인해 주세요.");
				status = HttpStatus.UNAUTHORIZED;
				return exceptionHandling("아이디 또는 패스워드를 확인해 주세요.", status);
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
//		return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
//		return ResponseEntity.status(HttpStatus.CREATED).body(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원인증", description = "회원 정보를 담은 Token 을 반환한다.")
	@GetMapping("/info/{memberId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("memberId") @Parameter(description = "인증할 회원의 아이디.", required = true) String memberId,
			HttpServletRequest request) {
		log.debug("memberId : {} ", memberId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println(request.getHeader("Authorization"));
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.getMember(memberId);
				resultMap.put("member", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "Access Token 재발급", description = "만료된 access token 을 재발급 받는다.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@org.springframework.web.bind.annotation.RequestBody MemberDto memberDto,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, memberDto);
		System.out.println(memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getMemberId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getMemberId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 access token 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("refresh token 도 사용 불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "로그아웃", description = "회원 정보를 담은 Token 을 제거한다.")
	@GetMapping("/logout/{memberId}")
	@Hidden
	public ResponseEntity<?> removeToken(
			@PathVariable("memberId") @Parameter(description = "로그아웃 할 회원의 아이디.", required = true) String memberId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(memberId);
			resultMap.put("result", "ok");
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "회원가입")
	@PostMapping(value = "")
	public ResponseEntity<?> join(
			@RequestBody(required = true, content = @Content(schema = @Schema(implementation = MemberDto.class))) @org.springframework.web.bind.annotation.RequestBody MemberDto memberDto) {
		log.debug("userRegister memberDto : {}", memberDto);

		try {
			System.out.println(memberDto);
			memberService.joinMember(memberDto);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(memberDto);
//			return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
//			return ResponseEntity.status(HttpStatus.CREATED).body(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원정보 수정")
	@PutMapping(value = "")
	public ResponseEntity<?> modify(@org.springframework.web.bind.annotation.RequestBody MemberDto memberDto) {
		log.debug("userModify memberDto : {}", memberDto);
		try {
			// form에서 hidden 필드 memberId도 전송 추가 해야함
			if (memberDto.getMemberId() == null)
				throw new Exception("ID 없음");
			memberService.updateMember(memberDto);

			MemberDto modifiedUser = memberService.getMember(memberDto.getMemberId());
			Map<String, Object> map = new HashMap<>();
			map.put("result", "ok");
			map.put("modifiedUser", modifiedUser);

//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(map);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원탈퇴")
	@DeleteMapping(value = "/{memberid}")
	public ResponseEntity<?> delete(@PathVariable("memberid") String memberId) {
		log.debug("delete memberid : {}", memberId);
		try {
			memberService.deleteMember(memberId);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(Collections.singletonMap("resultCode", "ok"));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원정보 조회")
	@GetMapping(value = "/{memberid}")
	public ResponseEntity<?> detail(@PathVariable("memberid") String memberId) {
		log.debug("detail memberid : {}", memberId);
		try {
			MemberDto memberDto = memberService.getMember(memberId);

			Map<String, Object> map = new HashMap<>();
			map.put("result", "ok");
			map.put("member", memberDto);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(map);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원 존재여부 확인")
	@GetMapping(value = "/exist/{memberid}")
	public ResponseEntity<?> checkExistence(@PathVariable("memberid") String memberId) {
		log.debug("exist memberid : {}", memberId);
		try {
			int count = memberService.idCheck(memberId);

			Map<String, Object> map = new HashMap<>();
			map.put("result", "ok");
			map.put("cnt", count);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(map);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<Map<String, Object>> exceptionHandling(String msg, HttpStatus status) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("result", "err");
		returnMap.put("msg", msg);
		return ResponseEntity.status(status).body(returnMap);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
