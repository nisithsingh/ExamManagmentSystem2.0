/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.common.Constants;
import com.nus.iss.ems.ejb.LecturerFacade;
import com.nus.iss.ems.ejb.LecturerFacade1;
import com.nus.iss.ems.service.UploadService1;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author abhinav
 */
@Named
@SessionScoped
public class UploadController1 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    
    @EJB
    LecturerFacade1 uploadFacade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String goToUploadStudentPage() {

        return "uploadstudent";
    }
    public String goToUploadLecturerPage() {

        return "uploadlecturer";
    }
    public String goTostudentmodulePage() {

        return "studentmodule";
    }
    public String goTolecturermodulePage() {
        return "lecturermodule";
    }
    
    public String add() {
        Map<String, String> errors = new UploadService1().validate(id, name);
        FacesContext context = FacesContext.getCurrentInstance();
        if (!errors.isEmpty()) {

            for (String key : errors.keySet()) {
                FacesMessage error = new FacesMessage(errors.get(key));
                context.addMessage(key, error);
            }

            return "upload";
        } else {
            String msg;
            msg = uploadFacade.registerUser(id, name);

            if (!msg.equals(Constants.SUCCESS)) {
                FacesMessage error = new FacesMessage(msg);
                context.addMessage(null, error);
                return "upload";
            } else {
                 FacesMessage error = new FacesMessage("Registered Successfully");
                context.addMessage("registerSuccess", error);
            }
        }
        return "upload";
    }
}
