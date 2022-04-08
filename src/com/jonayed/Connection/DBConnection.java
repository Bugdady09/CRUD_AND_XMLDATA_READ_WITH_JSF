package com.jonayed.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private String url = "jdbc:mysql://localhost:3306/employee";
	private String userName = "root";
	private String password = "1234";
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,userName,password);  
			return con;  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Connection error");
			return null;
		}
		
	}

}
