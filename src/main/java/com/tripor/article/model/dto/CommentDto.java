package com.tripor.article.model.dto;

import java.util.ArrayList;
import java.util.List;

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
public class CommentDto {
	private int commentId;
	private int articleId;
	private String memberId;
	private String commentContent;
	private String commentRegisterDate;
	private Integer parentCommentId;
	List<CommentDto> childComments = new ArrayList<>();

	public CommentDto(int commentId, int articleId, String memberId, String commentContent, String commentRegisterDate,
			Integer parentCommentId) {
		super();
		this.commentId = commentId;
		this.articleId = articleId;
		this.memberId = memberId;
		this.commentContent = commentContent;
		this.commentRegisterDate = commentRegisterDate;
		this.parentCommentId = parentCommentId;
	}
}
