package com.admin.usermanagement.web;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setHeader("Access-Control-Max-Age", "86400");
//	    //Tell the browser what requests we allow.
//		response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
//		response.setContentType("application/json");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		// TODO Auto-generated method stub
		doGet(request, response);
		doOptions(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		// Tell the browser what requests we allow.
		response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
		response.setContentType("application/json");

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
			updateUser(request, response);
			break;
		case "/edit":
			editUser(request, response);
			break;
		default:
			break;

		}

	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {

		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
		String name = json.getString("name");
		String email = json.getString("email");
		String country = json.getString("country");
		UserModel newUser = new UserModel(name, email, country);
		userDao.insertUser(newUser);

	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {
		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
		System.out.println(json.toString());
		Integer id = Integer.parseInt(json.getString("id"));
		userDao.deleteUser(id);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {

		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
		System.out.println(json.toString());
		Integer id = Integer.parseInt(json.getString("id"));
		String name = json.getString("name");
		String email = json.getString("email");
		String country = json.getString("country");
		UserModel newUser = new UserModel(id, name, email, country);
		userDao.updateUser(newUser);
//		System.out.println(newUser);

	}

	private void editUser(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {

//		System.out.println("edit function");
//		response.setHeader("Access-Control-Allow-Origin", "*");
////		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//		response.setHeader("Access-Control-Allow-Headers",
//				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Expose-Headers", "Authorization");
//		response.addHeader("Access-Control-Expose-Headers", "responseType");
//		response.addHeader("Access-Control-Expose-Headers", "observe");
//		response.addHeader("Accept", "*/*");
//		System.out.println(request.getPathInfo());
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setHeader("Access-Control-Max-Age", "86400");
//		// Tell the browser what requests we allow.
//		response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
//		response.setContentType("application/json");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
		System.out.println(json.toString());
		Integer id = Integer.parseInt(json.getString("id"));
//		Integer id = Integer.parseInt(request.getParameter("id"));
		UserModel user = userDao.selectUser(id);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(user);

		out.write(jsonString);

	}

	private void listUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

//		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
//
//		String name = json.getString("name");
		PrintWriter out = response.getWriter();

		List<UserModel> listUsers = new ArrayList<>();

		listUsers = userDao.listAllUser();

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(listUsers);

		out.write(jsonString);

	}

	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
		response.setContentType("application/json");
	}

}
