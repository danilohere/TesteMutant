package com.jsonreader.test;

import org.junit.Test;

import com.jsonreader.controller.UserController;

import org.junit.Assert;

public class JSONReaderTest {
	
	UserController controller = new UserController();

	@Test
	public void runTests(){
		downloadDataTest();
		saveDataTest();
	}
	
	//@Test
	public void downloadDataTest() {
		System.out.println("1. downloadDataTest - BEGIN");
		boolean noException = true;
		
		try {
			controller.downloadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(true, noException);
		System.out.println("1. downloadDataTest - END");
	}
	
	//@Test
	public void saveDataTest() {
		System.out.println("2. saveDataTest - BEGIN");
		boolean noException = true;
		
		try {
			controller.saveData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(true, noException);
		System.out.println("2. saveDataTest - END");
	}
}
