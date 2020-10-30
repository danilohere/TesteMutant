package com.jsonreader.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jsonreader.db.CRUDDatabase;
import com.jsonreader.entity.Company;
import com.jsonreader.entity.User;
import com.jsonreader.jsonplaceholder.JSONRequest;
import com.jsonreader.logging.Logging;

public class UserController {

	private JSONRequest request;
	
	public UserController() {
		request = new JSONRequest();
	}
	
	public void downloadData() {
		String apiResult = request.getAPIResult();
		
		JsonArray jsonObjectAlt = JsonParser.parseString(apiResult).getAsJsonArray();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String Json = gson.toJson(jsonObjectAlt);
		System.out.println("Baixar Dados: \n"+Json + "\n");
	}
	
	public List<User> JSONToUserList() {
		
		Logging.saveLog("Preparando para transformar o JSON em um Java Object");
		
		JSONArray arr = new JSONArray(request.getAPIResult());
		
		List<User> userList = new ArrayList<User>();
		
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			
			User user = new User();
			Company company = new Company();
			
			user.setId(obj.getInt("id"));
			user.setName(obj.getString("name"));
			user.setUsername(obj.getString("username"));
			user.setEmail(obj.getString("email"));
			user.setStreet(obj.getJSONObject("address").getString("street"));
			user.setSuite(obj.getJSONObject("address").getString("suite"));
			user.setCity(obj.getJSONObject("address").getString("city"));
			user.setZipcode(obj.getJSONObject("address").getString("zipcode"));
			user.setLat(obj.getJSONObject("address").getJSONObject("geo").getString("lat"));
			user.setLng(obj.getJSONObject("address").getJSONObject("geo").getString("lng"));
			user.setPhone(obj.getString("phone"));
			user.setWebsite(obj.getString("website"));
			
			company.setName(obj.getJSONObject("company").getString("name"));
			company.setCatchPhrase(obj.getJSONObject("company").getString("catchPhrase"));
			company.setBs(obj.getJSONObject("company").getString("bs"));
			
			user.setCompany(company);
			
			userList.add(user);
	
		}
		
		Logging.saveLog("Objetos criados com sucesso");
		
		return userList;
	}
	
	public List<User> FilterByAddress() {
		Logging.saveLog("Filtrando lista de usuarios");
		List<User> userList = JSONToUserList();
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getSuite().contains("Apt.")) {
				userList.remove(i);
				i--;
			}
		}
		
		Logging.saveLog("Lista Filtrada");
		return sort(userList);
	}
	
	private List<User> sort(List<User> list){
		Logging.saveLog("Preparando para ordenar lista de usuarios");
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			names.add(list.get(i).getName());
		}
		
		Collections.sort(names);
		
		List<User> orderedList = new ArrayList<User>();
		
		for (int i = 0; i < names.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).getName().equals(names.get(i)))
					orderedList.add(list.get(j));
			}
		}
		
		Logging.saveLog("Lista em ordem alfabetica");
		
		return orderedList;
	}
	
	public void saveData() {
		System.out.println("Salvar Dados:");
		List<User> userList = FilterByAddress();
		
		CRUDDatabase crud = new CRUDDatabase();
		
		for (int i = 0; i < userList.size(); i++) {
			crud.insertUser(userList.get(i));
		}
		
		for (int i = 0; i < userList.size(); i++) {
			crud.updateUsersCompany(userList.get(i).getId(), userList.get(i).getCompany().getName());
		}
		
		crud.closeConnection();
	}

}
