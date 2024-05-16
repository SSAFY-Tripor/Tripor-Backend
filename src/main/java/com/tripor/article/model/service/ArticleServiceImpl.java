package com.tripor.article.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.article.model.dto.ArticleListDto;
import com.tripor.article.model.mapper.ArticleMapper;
import com.tripor.util.PageNavigation;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleMapper articleMapper;

	@Override
	public void writeArticle(ArticleDto articleDto) throws Exception {
		articleMapper.insert(articleDto);
		List<com.tripor.article.model.dto.FileInfoDto> fileInfos = articleDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			articleMapper.registerFile(articleDto);
		}
	}

	@Override
	public ArticleListDto listArticle(Map<String, String> map) throws Exception {
		int pgno = 1;
		String pg = (String) map.get("pgno");
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));

		Map<String, Object> param = new HashMap<>();
		// 페이지 Navigation 관련
		int start = currentPage * sizePerPage - sizePerPage;
		
		param.put("pgno", Integer.parseInt(map.get("pgno")));
		param.put("spp", Integer.parseInt(map.get("spp")));
		param.put("nav", Integer.parseInt(map.get("nav")));
		
		param.put("start", start);
		param.put("listSize", sizePerPage);

		// 검색 관련
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
//		if ("member_id".equals(key))
//			param.put("key", key == null ? "" : "article.member_id");

		List<ArticleDto> list = articleMapper.findAll(param);

//		if (map.containsKey("pgno") && pg != null && pg.length() > 0)
//			pgno = Integer.parseInt(pg);
//		System.out.println(pgno);
//		int listSize = BoardSize.LIST.getBoardSize();
//		int start = (pgno - 1) * listSize;
//		map.put("start", start);
//		map.put("listSize", listSize);
//		System.out.println(map);

		ArticleListDto articleListDto = new ArticleListDto();
		PageNavigation pageNavigation = makePageNavigation(param);
		System.out.println(pageNavigation);
		
		articleListDto.setArticles(list);
		articleListDto.setPageNavigation(pageNavigation);

		return articleListDto;
	}

	@Override
	public ArticleDto getArticle(int articleId) throws Exception {
		return articleMapper.findById(articleId);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, Object> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		int currentPage = (int) map.get("pgno");
		int naviSize = (int) map.get("nav");
		int listSize = (int) map.get("spp");
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = articleMapper.getArticleCount(map);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / listSize + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		return pageNavigation;
	}

	@Override
	public void updateHit(int articleId) throws Exception {
		articleMapper.increaseHit(articleId);
	}

	@Override
	public void modifyArticle(ArticleDto articleDto) throws Exception {
		articleMapper.update(articleDto);
	}

	@Override
	public void deleteArticle(int articleId) throws Exception {
		articleMapper.delete(articleId);
	}

}
