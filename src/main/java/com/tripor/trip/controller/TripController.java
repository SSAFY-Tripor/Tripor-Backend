package com.tripor.trip.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.tripor.trip.model.dto.TripSearchDto;
import com.tripor.trip.model.service.TripService;
import com.tripor.trip.model.service.TripServiceImpl;

@WebServlet("/trip")
public class TripController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TripService tripService = TripServiceImpl.getInstance();
    public TripController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String path = "";
		String root = request.getContextPath();
		try {
			if("sido".equals(action)) {
				String json = tripService.getAllSido();
				System.out.println(json);
				returnJson(json, response);
			}else if("gugun".equals(action)) {
				int sidoCode = Integer.parseInt(request.getParameter("sido"));
				String json = tripService.getAllGugun(sidoCode);
				returnJson(json, response);
			}else if("mapping".equals(action)) {
				String sido= request.getParameter("sido");
				String gugun= request.getParameter("gugun");
				String type = request.getParameter("type");
				
				TripSearchDto param = new TripSearchDto(sido, gugun, type);
				String json = tripService.getTripList(param);
				returnJson(json, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	protected void redirect(String path, String root, HttpServletResponse response) throws IOException{
		response.sendRedirect(root + path);
	}
	
	protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	protected void returnJson(String json, HttpServletResponse response) throws IOException{
		// HTTP 응답 설정
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// JSON 응답 출력
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
