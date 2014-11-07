/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.ejb.LecturerFacade;
import com.nus.iss.ems.ejb.LecturerFacade1;
import com.nus.iss.ems.ejb.LecturerModuleFacade;
import com.nus.iss.ems.ejb.ModuleFacade;
import com.nus.iss.ems.entities.Lecturer;
import com.nus.iss.ems.entities.Module;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

/**
 *
 * @author abhinav
 */
@Named
@SessionScoped
public class LecturerModuleController implements Serializable {


    private Lecturer lecturer;
    private List<Lecturer> lecturers;
    private List<Module> module;
    private List<Module> selectedModule;
    @EJB
    ModuleFacade moduleFacade;

    @EJB
    LecturerFacade lecturerFacade;
    @EJB
    LecturerFacade1 lecturerfacade1;
    @EJB
    LecturerModuleFacade lecturermodulefacade;
    @PostConstruct
    public void init() {
        lecturers = lecturerfacade1.findAll();
        lecturer = lecturers.get(0);
        module = moduleFacade.retrieveAll();
        selectedModule = lecturer.getModules();
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Module> getModule() {
        return module;
    }

    public void setModule(List<Module> module) {
        this.module = module;
    }

    public List<Module> getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(List<Module> selectedModule) {
        this.selectedModule = selectedModule;
    }

    public void selectmodule() {
        selectedModule = lecturermodulefacade.retrieveLecturerModules(lecturer);
    }

    public String assign() {
        lecturermodulefacade.updateStudentModules(lecturer, selectedModule);
        return "upload";
    }
}
