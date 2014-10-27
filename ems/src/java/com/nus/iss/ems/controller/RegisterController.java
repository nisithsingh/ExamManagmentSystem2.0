package com.nus.iss.ems.controller;

import com.nus.iss.ems.common.Constants;
import com.nus.iss.ems.ejb.RegisterFacade;
import com.nus.iss.ems.service.RegisterService;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Milan
 */
@RequestScoped
@Named("registerController")
public class RegisterController {

    private String userID;
    private String password;

    @EJB
    RegisterFacade registerFacade;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String goToRegisterPage() {

        return "register";
    }

    public String registerUser() {
        Map<String, String> errors = new RegisterService().validate(userID, password);
        FacesContext context = FacesContext.getCurrentInstance();
        if (!errors.isEmpty()) {

            for (String key : errors.keySet()) {
                FacesMessage error = new FacesMessage(errors.get(key));
                context.addMessage(key, error);
            }

            return "register";
        } else {
            String msg = registerFacade.registerUser(userID, password);

            if (!msg.equals(Constants.SUCCESS)) {
                FacesMessage error = new FacesMessage(msg);
                context.addMessage(null, error);
                return "register";
            } else {
                 FacesMessage error = new FacesMessage("Registered Successfully");
                context.addMessage("registerSuccess", error);
            }
        }
        return "index";
    }

}
