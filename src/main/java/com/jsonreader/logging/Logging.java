package com.jsonreader.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {
	
	private static final String PATH = "log/";
	
	public static void saveLog(String message) {
		
		String logfileName = getFileName();
		BufferedWriter bw;
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
		String date = "[" + dtf.format(now) + "] - ";
		
		try {
			if(setFileName(logfileName)) {
				bw = new BufferedWriter(new FileWriter(logfileName, true));
			} else {
				bw = new BufferedWriter(new FileWriter(logfileName));
			}
			
			bw.write(date + message);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private static String getFileName() {
		LocalDateTime day = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		String logfileName = PATH + dtf.format(day) + "log.txt";
		
		return logfileName;
	}

	private static boolean setFileName(String logfileName) {
		File f = new File(logfileName);
		
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
		
	}

}
