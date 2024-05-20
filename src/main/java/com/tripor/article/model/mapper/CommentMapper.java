package com.tripor.article.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tripor.article.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
	// 댓글 추가
    void insertComment(CommentDto commentDto);

    // 특정 게시글의 모든 댓글 가져오기
    List<CommentDto> findByArticleId(int articleId);

    // 댓글 수정
    void updateComment(CommentDto commentDto);

    // 댓글 삭제
    void deleteComment(int commentId);
    
    void deleteChildComment(int parentCommentId);
}
