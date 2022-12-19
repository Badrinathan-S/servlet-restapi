package com.admin.usermanagement.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.admin.usermanagement.bean.UserModel;
import com.admin.usermanagement.dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UserServlet
 */
//@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
		System.out.println("init statrted");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/list":
			listUser(request, response);
			break;
		case "/insert":
			insertUser(request, response);
			break;
		case "/delete":
			deleteUser(request, response);
			break;
		case "/update":
			break;
		case "/edit":
			break;
		default:
			break;

		}

	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {

		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
		String name = json.getString("name");
		String email = json.getString("email");
		String country = json.getString("country");
		UserModel newUser = new UserModel(name, email, country);
		userDao.insertUser(newUser);

	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) {

		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		UserModel newUser = new UserModel(name, email, country);
		userDao.updateUser(newUser);
		System.out.println(newUser);

	}

	private void listUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

//		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
//
//		String name = json.getString("name");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		List<UserModel> listUsers = new ArrayList<>();
		
		listUsers = userDao.listAllUser();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(listUsers);
		
		out.write(jsonString);
		
//		for (UserModel userModel : listUsers) {
//			System.out.println(userModel.getName());	
//			System.out.println(userModel.getCountry());
//			System.out.println(userModel.getEmail());
//			System.out.println(userModel.getId());
//		}

//		System.out.println(name);

//		StringBuilder sb = new StringBuilder();
//	    BufferedReader reader = request.getReader();
//	    try {
//	        String line;
//	        while ((line = reader.readLine()) != null) {
//	            sb.append(line);
//	        }
//	    } finally {
//	        reader.close();
//	    }
//	    String jsonObject = sb.toString();
//	    JSONObject jsonData = new JSONObject(jsonObject);
//	    
//	    String name = jsonData.getString("name");
//	    
//	    System.out.println(name);

	}

}
