/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.ejb.ModuleFacade;
import com.nus.iss.ems.ejb.StudentFacade;
import com.nus.iss.ems.ejb.StudentFacade1;
import com.nus.iss.ems.ejb.StudentModuleFacade;
import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.Student;
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
public class StudentModuleController implements Serializable {

    @PostConstruct
    public void init() {
        students = studentfacade1.findAll();
        module = moduleFacade.retrieveAll();
        student=students.get(0);
        selectedModule=student.getModules();
    }
    private Student student;
    private List<Student> students;
    private List<Module> module;
    private List<Module> selectedModule;
    @EJB
    ModuleFacade moduleFacade;

    @EJB
    StudentFacade studentFacade;
    @EJB
    StudentFacade1 studentfacade1;
    @EJB
    StudentModuleFacade studentmodulefacade;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        return students;
    }
    public Student getStudent(java.lang.Long id) {
        return studentfacade1.findStudent(id);
    }
    public void setStudents(List<Student> students) {
        this.students = students;
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
        selectedModule = studentmodulefacade.retrieveStudentModules(student);
    }

    public String assign() {
        studentmodulefacade.updateStudentModules(student, selectedModule);
        return "upload";
    }

}
