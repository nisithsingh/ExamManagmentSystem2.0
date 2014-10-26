/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Milan
 */


public class RegisterService {
    
    Map<String, String> errors=null;
    
    public Map<String,String> validate(String userID,String password)
    {
        errors=new HashMap<String,String>();
        if(userID==null || userID.equals(""))
        {
            errors.put("userID", "User ID cannot be empty");
        }
        if(password==null || password.equals(""))
        {
            errors.put("password", "Password cannot be empty");
        }
        return errors;
    }
}
