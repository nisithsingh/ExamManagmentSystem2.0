/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Milan
 */
@Named
@SessionScoped
public class MenuBacking implements Serializable {
    private String selection;
    private boolean register;
    private boolean login;

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
   

    public MenuBacking() {
       register = false;
       login = true; // You can define the default page that will be show
      
    }

    // getters & setters

    public void active() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
       setRegister(selection.equals("register"));
       setLogin(selection.equals("login"));   
    }
}
