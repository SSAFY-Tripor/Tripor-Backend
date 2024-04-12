package com.tripor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	private static DBUtil instance = new DBUtil();

	
	/**
	 * 커넥션 풀 사용 시 변경
	 */
//	private DBUtil() {}
	
	
	/**
	 * 초기 세팅용 커넥션 풀 X
	 */
	private DBUtil() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DBUtil getInstance() {
		return instance;
	}
	
	/**
	 * 커넥션 풀 사용시 사용
	 * @return
	 * @throws SQLException
	 */
//	public Connection getConnection() throws SQLException {
//		try {
//			Context context = new InitialContext(); // 메모리안에 윈도우 탐색기에 들어감
//			Context rootContext = (Context) context.lookup("java:comp/env");
//			DataSource dataSource = (DataSource) rootContext.lookup("jdbc/ssafy");
//			// 톰캣이 미리 만들어둔 커넥션에서 얻어오는 방식으로 변경됐음. (META-INF에 있음)
//			return dataSource.getConnection();
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 초기 데이터 세팅용 커넥션 풀 X
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://127.0.0.1:3306/tripor?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
		String user = "ssafy";
		String pwd = "ssafy";
		return DriverManager.getConnection(url, user, pwd);
	}
	
	
	public void close(AutoCloseable... autoCloseables) {
		for(AutoCloseable ac : autoCloseables) {
			if(ac != null) {
				try {
					ac.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
