package com.tripor.article.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.article.model.dto.FileInfoDto;

@Mapper
public interface ArticleMapper {
	void insert(ArticleDto articleDto);

	List<ArticleDto> findAll(Map<String, Object> map);

	int getArticleCount(Map<String, Object> map);

	ArticleDto findById(int articleId);

	void increaseHit(int articleId);

	void update(ArticleDto articleDto);
	
	List<Integer> getRelationImageIdsByArticleId(int articleId);
	
	void deleteImageByImageId(int imageId);
	
	void deleteRelationByImageId(int imageId);
	
	void deleteRelationByArticleId(int articleId);

	void delete(int articleId);
	
	void registerFile(ArticleDto articleDto) throws Exception;
	
	void registerImage(FileInfoDto image) throws Exception;
	
	int lastKey() throws Exception;

	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
}
