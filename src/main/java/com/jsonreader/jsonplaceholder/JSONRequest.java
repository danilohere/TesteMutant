package com.jsonreader.jsonplaceholder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jsonreader.logging.Logging;

public class JSONRequest {
	
	private final String apiURL = "https://jsonplaceholder.typicode.com/users";


	private StringBuffer getJSONPlaceholder(){
		try {
			Logging.saveLog("Realizando o método GET para a API de JSON Placeholder");
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuffer content = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				content.append(line);
			}
			
			Logging.saveLog("JSON retornado com sucesso");
			
			in.close();
			con.disconnect();
			
			return content;
			
		} catch (MalformedURLException e) {
			Logging.saveLog("Erro ao conectar com a API - MalformedURLException");
			Logging.saveLog(e.getMessage());
			return null;
		} catch (IOException e) {
			Logging.saveLog("Erro ao recuperar JSON da API - IOException");
			Logging.saveLog(e.getMessage());
			return null;
		}
		
	}
	
	public String getAPIResult() {
		Logging.saveLog("Resultado da API obtido com sucesso");
		return getJSONPlaceholder().toString();
	}
	

}
