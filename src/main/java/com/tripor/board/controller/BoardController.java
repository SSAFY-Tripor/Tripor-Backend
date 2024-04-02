package com.tripor.board.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tripor.board.model.dto.BoardDto;
import com.tripor.board.model.service.BoardService;
import com.tripor.board.model.service.BoardServiceImpl;
import com.tripor.member.model.dto.MemberDto;
import com.tripor.util.PageNavigation;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = BoardServiceImpl.getInstance();

	private int pgno;
	private String key;
	private String word;
	private String queryString;

	public BoardController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		// 숫자로만 이루어진 문자열인지 확인 (아니라면 1)
		pgno = Optional.ofNullable(request.getParameter("pgno")).filter(str -> str.matches("\\d+"))
				.map(Integer::parseInt).orElse(1);
		key = Optional.ofNullable(request.getParameter("key")).orElse("");
		word = Optional.ofNullable(request.getParameter("word")).orElse("");
		queryString = "pgno=" + pgno + "&key=" + key + "&word=" + word;

		String path = "";
		String root = request.getContextPath();
		try {
			if ("list".equals(action)) {
				HttpSession session = request.getSession();
				MemberDto memberDto = (MemberDto) session.getAttribute("member");
				if (memberDto != null) {
					Map<String, Object> map = new HashMap<>();
					map.put("pgno", pgno);
					map.put("key", key);
					map.put("word", word);

					// 글 목록
					List<BoardDto> list = boardService.listBoard(map);
					request.setAttribute("boards", list);

					// 페이징처리
					PageNavigation pageNavigation = boardService.makePageNavigation(map);
					request.setAttribute("navigation", pageNavigation);

					path = "/trip/board.jsp";
					forward(path, request, response);
				} else {
					path = "/member?action=mvLogin";
					redirect(path, root, response);
				}
			} else if ("mvWrite".equals(action)) {
				path = "/trip/write.jsp";
				forward(path, request, response);
			} else if ("write".equals(action)) {
				HttpSession session = request.getSession();
				MemberDto memberDto = (MemberDto) session.getAttribute("member");
				BoardDto boardDto = null;
				if (memberDto != null) {
					String subject = request.getParameter("subject");
					String content = request.getParameter("content");
					boardDto = new BoardDto(memberDto.getUserId(), subject, content);
					for (int i = 0; i < 30; i++) {
						boardDto.setSubject(boardDto.getSubject() + "" + i);
						boardService.writeBoard(boardDto);
					}
					path = "/board?action=list?" + queryString;
					redirect(path, root, response);
				} else {
					path = "/member?action=mvLogin";
					redirect(path, root, response);
				}
			} else if ("detail".equals(action)) {
				int boardNo = Integer.parseInt(request.getParameter("boardno"));
				boardService.updateHit(boardNo);
				BoardDto boardDto = boardService.getBoard(boardNo);
				if (boardDto == null) {
					path = "/board?action=list";
					redirect(path, root, response);
					return;
				}
				System.out.println(boardDto);
				request.setAttribute("board", boardDto);
				path = "/trip/detail.jsp";
				forward(path, request, response);
			} else if ("mvModify".equals(action)) {
				int boardNo = Integer.parseInt(request.getParameter("boardno"));
				BoardDto boardDto = boardService.getBoard(boardNo);
				request.setAttribute("board", boardDto);
				path = "/trip/modify.jsp";
				forward(path, request, response);
			} else if ("modify".equals(action)) {
				int boardNo = Integer.parseInt(request.getParameter("boardno"));
				BoardDto boardDto = boardService.getBoard(boardNo);
				if (boardDto == null) {
					path = "/board?action=list";
					redirect(path, root, response);
					return;
				}
				String subject = request.getParameter("subject");
				String content = request.getParameter("content");
				boardDto.setSubject(subject);
				boardDto.setContent(content);
				boardService.modifyBoard(boardDto);
				path = "/board?action=detail&boardno=" + boardNo;
				redirect(path, root, response);
			} else if ("delete".equals(action)) {
				int boardNo = Integer.parseInt(request.getParameter("boardno"));
				boardService.deleteBoard(boardNo);
				path = "/board?action=list";
				redirect(path, root, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			path = "/error.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void redirect(String path, String root, HttpServletResponse response) throws IOException {
		response.sendRedirect(root + path);
	}

	protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

}
