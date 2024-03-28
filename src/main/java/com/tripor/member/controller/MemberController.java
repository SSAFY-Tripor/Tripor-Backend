package com.tripor.member.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.tripor.member.model.dto.MemberDto;
import com.tripor.member.model.service.MemberService;
import com.tripor.member.model.service.MemberServiceImpl;

@WebServlet("/member")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = MemberServiceImpl.getInstance();
       
    public MemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String path = "";
		String root = request.getContextPath();
		
		try {
			if("mvLogin".equals(action)) {
				path = "/user/login.jsp";
				forward(path, request, response);
			}else if("login".equals(action)) {
				String userId = request.getParameter("userid");
				String userPw = request.getParameter("userpwd");
				String saveId = request.getParameter("saveid");
				MemberDto member = memberService.login(userId, userPw);
				if(member == null) {
					path = "/user/login.jsp";
					request.setAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
					forward(path, request, response);
					return;
				}
				path = "";
				// 세션 설정
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				// 쿠키 설정
				if ("on".equals(saveId)) {
					// 쿠키 생성
					Cookie cookie = new Cookie("saveid", userId);
					cookie.setPath(root);
					cookie.setMaxAge(60 * 60 * 24 * 365);
					response.addCookie(cookie);
				} else {
					// 쿠키 삭제
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if ("saveid".equals(cookie.getName())) {
								cookie.setMaxAge(0);
								response.addCookie(cookie);
								break;
							}
						}
					}
				}
				redirect(path, root, response);
			}else if("mvJoin".equals(action)) {
				path = "/user/join.jsp";
				forward(path, request, response);
			}else if("join".equals(action)) {
				String userName = request.getParameter("username");
			    String userId = request.getParameter("userid");
			    String userPw = request.getParameter("userpwd");
			    String userPwCk = request.getParameter("userpwdcheck");
			    if(userPw == null || !userPw.equals(userPwCk)) {
			    	request.setAttribute("pwck", "비밀번호가 틀렸습니다.");
			    	path = "/user/join.jsp";
			    	forward(path, request, response);
			    	return;
			    }
			    String emailId = request.getParameter("emailid");
			    String emailDomain = request.getParameter("emaildomain");
//			    int sido = Integer.parseInt(request.getParameter("sido"));
//			    int gugun = Integer.parseInt(request.getParameter("gugun"));
			    int sido = 5; // 광주
			    int gugun = 1; // 광산구
				MemberDto joinMember = new MemberDto(userId, userPw, userName, emailId, emailDomain, sido, gugun);
				if(memberService.join(joinMember) == -1) {
					request.setAttribute("msg", "이미 존재하는 회원입니다.");
					path = "/user/join.jsp";
					forward(path, request, response);
					return;
				}
				path = "/member?action=mvLogin";
				redirect(path, root, response);
			}else if("logout".equals(action)) {
				HttpSession session = request.getSession();
				session.removeAttribute("member");
				path = "";
				redirect(path, root, response);
			}else {
				path = "";
				redirect(path, root, response);
			}
		}catch(Exception e) {
			path = "/error.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
		
	}
	
	protected void redirect(String path, String root, HttpServletResponse response) throws IOException{
		response.sendRedirect(root + path);
	}
	
	protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
