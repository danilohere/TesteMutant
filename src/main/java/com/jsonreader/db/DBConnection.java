package com.jsonreader.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.jsonreader.logging.Logging;

public class DBConnection {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String DB_NAME = "TesteMutant";
	private static final String TIME_ZONE = "?useTimezone=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	public static Connection getConnection() {
		try {
			Logging.saveLog("Estabelecendo conexão com banco de dados");
			Connection con = DriverManager.getConnection(DB_URL + DB_NAME + TIME_ZONE, USER, PASSWORD);
			Logging.saveLog("Conexão estabelecida com sucesso");
			return con;
		} catch (Exception e) {
			Logging.saveLog("Erro ao estabelecer conexão com banco de dados");
			Logging.saveLog(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		
		
	}
}
