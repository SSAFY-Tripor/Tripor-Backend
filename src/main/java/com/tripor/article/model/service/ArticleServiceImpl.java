package com.tripor.article.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripor.article.model.dto.ArticleDto;
import com.tripor.article.model.mapper.ArticleMapper;
import com.tripor.util.BoardSize;
import com.tripor.util.PageNavigation;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleMapper articleMapper;

	@Override
	public void writeArticle(ArticleDto articleDto) throws Exception {
		articleMapper.insert(articleDto);
	}

	@Override
	public List<ArticleDto> listArticle(Map<String, Object> map) throws Exception {
		int pgno = 1;
		System.out.println(pgno);
		String pg = (String) map.get("pgno");
		if (map.containsKey("pgno") && pg != null && pg.length() > 0)
			pgno = Integer.parseInt(pg);
		System.out.println(pgno);
		int listSize = BoardSize.LIST.getBoardSize();
		int start = (pgno - 1) * listSize;
		map.put("start", start);
		map.put("listSize", listSize);
		System.out.println(map);
		return articleMapper.findAll(map);
	}

	@Override
	public ArticleDto getArticle(int articleId) throws Exception {
		return articleMapper.findById(articleId);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, Object> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		int currentPage = (int) map.get("pgno");
		int naviSize = BoardSize.NAVIGATION.getBoardSize();
		int listSize = BoardSize.LIST.getBoardSize();
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = articleMapper.getArticleCount(map);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / listSize + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = (currentPage <= naviSize);
		pageNavigation.setStartRange(startRange);
		boolean endRange = ((totalPageCount - 1) / naviSize * naviSize < currentPage);
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
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
