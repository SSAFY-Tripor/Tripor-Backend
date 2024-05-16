package com.tripor.article.model.dto;

import java.util.List;

import com.tripor.util.PageNavigation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "BoardListDto : 게시글 목록 & 페이지 정보", description = "게시글 목록과 현재 페이지와 전체 페이지 정보를 나타낸다.")
public class ArticleListDto {

	@Schema(description = "글목록")
	private List<ArticleDto> articles;
	@Schema(description = "글목록 meta 정보")
	private PageNavigation pageNavigation;

}
