package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/zumba_gym_db";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "caltech123";
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}
		
	

}
