package com.jsonreader.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jsonreader.entity.Company;
import com.jsonreader.entity.User;
import com.jsonreader.logging.Logging;

public class CRUDDatabase {
	private Connection con;
	
	public CRUDDatabase() {
		con = DBConnection.getConnection();
	}
	
	public void insertUser(User user) {
		Logging.saveLog("Inserindo usuário no banco");
		String query = "INSERT INTO PERSON "
				+ "(ID, NAME, EMAIL, USERNAME, STREET, SUITE, CITY, ZIPCODE, LATITUDE, LONGITUDE, PHONE, WEBSITE) VALUES"
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stnt = con.prepareStatement(query);
			stnt.setInt(1, user.getId());
			stnt.setString(2, user.getName());
			stnt.setString(3, user.getEmail());
			stnt.setString(4, user.getUsername());
			stnt.setString(5, user.getStreet());
			stnt.setString(6, user.getSuite());
			stnt.setString(7, user.getCity());
			stnt.setString(8, user.getZipcode());
			stnt.setString(9, user.getLat());
			stnt.setString(10, user.getLng());
			stnt.setString(11, user.getPhone());
			stnt.setString(12, user.getWebsite());
			
			stnt.execute();
			stnt.close();
			
			Logging.saveLog("User Name: " + user.getName() + " registrado no banco");
			
			insertCompany(user.getCompany());

			
		} catch (SQLException e) {
			Logging.saveLog("Erro ao inserir usuário no banco - SQLException");
			Logging.saveLog(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void insertCompany(Company company) {
		String query = "INSERT INTO COMPANY "
				+ "(NAME, CATCH_PHRASE, BS) VALUES"
				+ "(?, ?, ?)";
		try {
			PreparedStatement stnt = con.prepareStatement(query);
			stnt.setString(1, company.getName());
			stnt.setString(2, company.getCatchPhrase());
			stnt.setString(3, company.getBs());
		
			stnt.execute();
			stnt.close();
			
			Logging.saveLog("Company Name: " + company.getName() + " registrado no banco");

			
		} catch (SQLException e) {
			Logging.saveLog("Erro ao inserir compania no banco - SQLException");
			Logging.saveLog(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void updateUsersCompany(int userId, String companyName) {
		String querySelect = "SELECT ID FROM COMPANY WHERE NAME = ?";
		String queryUpdate = "UPDATE PERSON SET COMPANY_ID = ? WHERE ID = ?";
		
		try {
			PreparedStatement select = con.prepareStatement(querySelect);
			select.setString(1, companyName);
			ResultSet rs = select.executeQuery();
			PreparedStatement update = con.prepareStatement(queryUpdate);
			int companyId;
			if(rs != null && rs.next()) {
				 companyId = rs.getInt("ID");
				 update.setInt(1, companyId);
				 update.setInt(2, userId);
				 update.execute();
			}
			rs.close();
		} catch (SQLException e) {
			Logging.saveLog("Erro ao atualizar o id da compania no usuário no banco - SQLException");
			Logging.saveLog(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			con.close();
			Logging.saveLog("Conexão encerrada com sucesso");
		} catch (SQLException e) {
			Logging.saveLog("Erro ao encerrar conexão com banco - SQLException");
			Logging.saveLog(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
