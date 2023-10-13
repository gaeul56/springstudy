package com.gdu.app02.xml02;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyJdbcConnection {
	private String driver;
	private String url;
	private String user;
	private String password;
	
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			System.out.println(user + "접속 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
