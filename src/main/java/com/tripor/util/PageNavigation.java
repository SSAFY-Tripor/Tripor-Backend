package com.tripor.util;

public class PageNavigation {

	private int totalCount; // 총 게시글 개수
//	private int newCount; // 새글 개수
	private int totalPageCount; // 총 페이지 개수
	private int currentPage; // 현재 페이지 번호
	private int naviSize; // 네비게이션 사이즈

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

//	public int getNewCount() {
//		return newCount;
//	}
//
//	public void setNewCount(int newCount) {
//		this.newCount = newCount;
//	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNaviSize() {
		return naviSize;
	}

	public void setNaviSize(int naviSize) {
		this.naviSize = naviSize;
	}

	@Override
	public String toString() {
		return "PageNavigation [totalCount=" + totalCount + ", totalPageCount=" + totalPageCount + ", currentPage="
				+ currentPage + ", naviSize=" + naviSize + "]";
	}

}
