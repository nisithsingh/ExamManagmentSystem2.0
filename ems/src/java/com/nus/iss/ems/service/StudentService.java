/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.service;

import com.nus.iss.ems.ejb.StudentFacade1;
import com.nus.iss.ems.entities.Student;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author abhinav
 */
@ManagedBean(name = "studentService", eager = true)
@ApplicationScoped
public class StudentService {

    private List<Student> students;
    @EJB
    StudentFacade1 student;

    @PostConstruct
    public void init() {
        students = student.findAll();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
