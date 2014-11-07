/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author abhinav
 */
@Stateless
public class StudentModuleFacade {

    @PersistenceContext
    private EntityManager em;

    public List<Module> retrieveStudentModules(Student student) {
        student = em.find(Student.class, student.getId());
        return student.getModules();
    }

    public void updateStudentModules(Student student, List<Module> modules) {
        
        student = em.find(Student.class, student.getId());
        student.setModules(modules);
        em.persist(student);
    }
    
    
}
