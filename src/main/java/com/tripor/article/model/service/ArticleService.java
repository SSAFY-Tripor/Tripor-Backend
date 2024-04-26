package com.tripor.article.model.service;

import java.util.List;
import java.util.Map;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.util.PageNavigation;

public interface ArticleService {
	void writeArticle(ArticleDto articleDto) throws Exception;

	List<ArticleDto> listArticle(Map<String, Object> map) throws Exception;

	ArticleDto getArticle(int articleId) throws Exception;

	PageNavigation makePageNavigation(Map<String, Object> map) throws Exception;

	void updateHit(int articleId) throws Exception;

	void modifyArticle(ArticleDto articleDto) throws Exception;

	void deleteArticle(int articleId) throws Exception;
}
