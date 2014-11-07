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
 * @author abhinav
 */
public class UploadService {
    Map<String, String> errors=null;
    
    public Map<String,String> validate(String id,String name)
    {
        errors=new HashMap<String,String>();
        if(id==null || id.equals(""))
        {
            errors.put("STUDENTID", "Student id cannot be empty");
        }
        if(name==null || name.equals(""))
        {
            errors.put("STUDENTNAME", "Student name cannot be empty");
        }
        return errors;
    }
}
