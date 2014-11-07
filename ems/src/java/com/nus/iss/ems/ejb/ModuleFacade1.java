/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;


import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Milan
 */
@Stateless
public class ModuleFacade1 {

    @PersistenceContext
    private EntityManager em;

    public List<Module> retireveAllModules(String studentid) {
        TypedQuery<Student> query = em.createNamedQuery("Student.findByStudentId", Student.class);
        query.setParameter("studentId", studentid);
        List<Student> students = query.getResultList();
        if (students != null && students.size() > 0) {
            return students.get(0).getModules();
        } else {
            return new ArrayList<Module>();
        }
    }

}
