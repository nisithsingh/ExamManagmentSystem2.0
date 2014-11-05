package com.nus.iss.ems.controller;

import com.nus.iss.ems.common.Constants;
import com.nus.iss.ems.enums.QuestionType;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Milan
 */
@RequestScoped
@Named
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        //new user
        if(this.password.equalsIgnoreCase(Constants.DEFAULT_PASSWORD))
        {
            return "/register?faces-redirect=true";
        }
        
        try {
            if (request.getRemoteUser() == null) {
                request.login(this.username, this.password);
            }

        } catch (Exception e) {

            context.addMessage(null, new FacesMessage(e.getMessage()));
            return "login";
        }
        if (request.isUserInRole("student")) {
            return "/student/index?faces-redirect=true";
        } else if (request.isUserInRole("lecturer")) {
            return "lecturer/index";
        } else if (request.isUserInRole("admin")) {
            return "admin/index";
        } else {
            return "login";
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (Exception e) {

            context.addMessage(null, new FacesMessage("Logout failed."));
            return null;
        }
        return "/login";
    }
    
   

    
}
