package com.jsonreader.JSONReader;

import java.util.List;

import com.jsonreader.controller.UserController;
import com.jsonreader.db.DBConnection;
import com.jsonreader.entity.User;

public class App 
{
    public static void main( String[] args )
    {
    	
    	UserController userController = new UserController();
    	
    	//Req 1
    	userController.downloadData();
    	
    	userController.saveData();
    }
}
