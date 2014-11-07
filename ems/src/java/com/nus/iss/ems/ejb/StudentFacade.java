/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Student;
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
public class StudentFacade {

    @PersistenceContext
    private EntityManager em;

    public Student findStudent(String studentId) {
        TypedQuery<Student> query = em.createNamedQuery("Student.findByStudentId", Student.class);
        query.setParameter("studentId", studentId);
        List<Student> students = query.getResultList();
        if (students.size() > 0) {
            return students.get(0);
        } else {
            return null;
        }

    }
}
