package com.tripor.article.model.dto;

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
}
