package com.tripor.article.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.article.model.dto.ArticleListDto;
import com.tripor.article.model.dto.ArticlePostDto;
import com.tripor.article.model.dto.CommentDto;
import com.tripor.article.model.dto.FileInfoDto;
import com.tripor.util.PageNavigation;

public interface ArticleService {
	int writeArticle(ArticlePostDto articlePostDto) throws Exception;

	ArticleListDto listArticle(Map<String, String> map) throws Exception;

	ArticleDto getArticle(int articleId) throws Exception;

	PageNavigation makePageNavigation(Map<String, Object> map) throws Exception;

	void updateHit(int articleId) throws Exception;

	void modifyArticle(ArticleDto articleDto) throws Exception;
	
	void deleteImage(int imageId) throws Exception;
	
	FileInfoDto registerImage(FileInfoDto image) throws Exception;

	void deleteArticle(int articleId) throws Exception;
	
    public void addComment(CommentDto commentDto)  throws Exception;

    public List<CommentDto> getCommentsByArticleId(int articleId)  throws Exception;

    public void updateComment(CommentDto commentDto)  throws Exception;

    public void deleteComment(int commentId) throws Exception;
}
