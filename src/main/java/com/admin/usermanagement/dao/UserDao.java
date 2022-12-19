package com.admin.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.admin.usermanagement.bean.UserModel;

public class UserDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	private String jdbcDriver = "com.mysql.jdbc.Driver";

	private static final String INSERT_USER_SQL = "INSERT INTO users" + "  (name, email, country) VALUE  "
			+ " (?, ?, ?);";
	private static final String SELECT_USER_BY_ID = "SELECT id, name, email, country FROM users WHERE id=?;";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id=?;";
	private static final String UPDATE_USER_SQL = "UPDATE users set name=?, email=?, country=? where id=?;";
	private static final String LIST_USER_SQL = "SELECT * FROM users;";

	public UserDao() {

	}

	protected Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}

		return connection;
	}

	// select all user

	public List<UserModel> listAllUser() {
		UserModel user = null;
		List<UserModel> listUsers = new ArrayList<>();
		
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(LIST_USER_SQL)){
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				int id = rs.getInt("id");
				user = new UserModel(id, name, email, country);
				listUsers.add(user);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
//		for (UserModel userModel : listUsers) {
//			System.out.println(userModel.getName());	
//			System.out.println(userModel.getCountry());
//			System.out.println(userModel.getEmail());
//			System.out.println(userModel.getId());
//		}
		
		

		return listUsers;

	}

	// insert user
	public void insertUser(UserModel user) {
		System.out.println(INSERT_USER_SQL);

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// select user by id

	public UserModel selectUser(Integer id) {
		UserModel user = null;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
			statement.setInt(1, id);
			System.out.println(statement);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new UserModel(id, name, email, country);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return user;
	}

	// update user

	public boolean updateUser(UserModel user) {
		boolean isRowUpdated;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {

			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());

			isRowUpdated = statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			isRowUpdated = false;
		}

		return isRowUpdated;

	}

	// delete user

	public boolean deleteUser(Integer id) {
		boolean isRowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {

			statement.setInt(1, id);

			isRowDeleted = statement.executeUpdate() > 0;

		} catch (SQLException e) {

			e.printStackTrace();
			isRowDeleted = false;

		}
		return isRowDeleted;
	}

}
