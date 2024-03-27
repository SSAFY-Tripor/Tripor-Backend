package com.tripor.member.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
				
			}else if("login".equals(action)) {
				
			}else if("mvJoin".equals(action)) {
				
			}else if("join".equals(action)) {
				
			}else if("logout".equals(action)) {
				
			}else {
				
			}
		}catch(Exception e) {
			
		}
		
	}
	
	protected void redirect(String path, HttpServletResponse response) throws IOException{
		response.sendRedirect(path);
	}
	
	protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
