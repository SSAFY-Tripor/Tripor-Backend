package com.tripor.article.controller;

import java.nio.charset.StandardCharsets;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.article.model.dto.ArticleListDto;
import com.tripor.article.model.service.ArticleService;
import com.tripor.member.model.dto.MemberDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/article")
@Tag(name = "게시물")
public class ArticleController {
	@Autowired
	ArticleService articleService;

	@Operation(summary = "전체 게시물 목록 조회")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "None Page"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@GetMapping("")
	public ResponseEntity<?> listArticle(
			@RequestParam @Parameter(description = "게시글을 얻기위한 부가정보", required = true) Map<String, String> map) {
		log.debug("listArticle map : {}", map);
		System.out.println(map);
		try {
			// pgno, key, word
			ArticleListDto list = articleService.listArticle(map);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("items", list);
			returnMap.put("count", list.getArticles().size());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "게시물 조회")
	@GetMapping("/{articleId}")
	public ResponseEntity<?> getArticle(@PathVariable("articleId") int articleId) {
		log.debug("getArticle articleId : {}", articleId);
		try {
			// pgno, key, word
			ArticleDto articleDto = articleService.getArticle(articleId);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("item", articleDto);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "게시물 삭제")
	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteArticle(@PathVariable("articleId") int articleId) {
		log.debug("deleteArticle articleId : {}", articleId);
		try {
			// pgno, key, word
			articleService.deleteArticle(articleId);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("result", "ok");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "조회수 업데이트")
	@PutMapping("/hit/{articleId}")
	public ResponseEntity<?> updateHit(@PathVariable("articleId") int articleId) {
		log.debug("updateHit articleId : {}", articleId);
		try {
			// pgno, key, word
			articleService.updateHit(articleId);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("item", articleService.getArticle(articleId));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "게시물 수정")
	@PutMapping("")
	public ResponseEntity<?> modifyArticle(@org.springframework.web.bind.annotation.RequestBody ArticleDto articleDto) {
		log.debug("modifyArticle articleDto : {}", articleDto);
		try {
			// pgno, key, word
			articleService.modifyArticle(articleDto);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("item", articleService.getArticle(articleDto.getArticleId()));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(returnMap);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "게시물 작성")
	@PostMapping(value = "")
	public ResponseEntity<?> writeArticle(@org.springframework.web.bind.annotation.RequestBody ArticleDto articleDto) {
		log.debug("writeArticle articleDto : {}", articleDto);
		try {
//			String subject = articleDto.getSubject();
//			for(int i=0;i<200;i++) {
//				articleDto.setSubject(subject+""+i);
//				articleService.writeArticle(articleDto);
//			}
			articleService.writeArticle(articleDto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(articleDto);
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
