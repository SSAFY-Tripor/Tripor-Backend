package com.tripor.board.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.tripor.board.model.dto.BoardDto;
import com.tripor.board.model.service.BoardService;
import com.tripor.board.model.service.BoardServiceImpl;
import com.tripor.member.model.dto.MemberDto;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = BoardServiceImpl.getInstance();

	public BoardController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String path = "";
		String root = request.getContextPath();
		System.out.println(action);
		try {
			if ("list".equals(action)) {
				HttpSession session = request.getSession();
				MemberDto memberDto = (MemberDto) session.getAttribute("member");
				if (memberDto != null) {
					List<BoardDto> list = boardService.listBoard();
					request.setAttribute("boards", list);
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
					boardService.writeBoard(boardDto);
					path = "/board?action=list";
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
			}else if("mvPlan".equals(action)) {
				// 로그인 블락 추가하기
				path = "/trip/plan.jsp";
				forward(path, request, response);
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
