package com.tripor.article.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripor.article.model.dto.ArticleDto;

@Mapper
public interface ArticleMapper {
	void insert(ArticleDto articleDto);

	List<ArticleDto> findAll(Map<String, Object> map);

	int getArticleCount(Map<String, Object> map);

	ArticleDto findById(int articleId);

	void increaseHit(int articleId);

	void update(ArticleDto articleDto);

	void delete(int articleId);
}
