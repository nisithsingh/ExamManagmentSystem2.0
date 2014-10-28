/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.common.Constants;
import com.nus.iss.ems.entities.Lecturer;
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
public class RegisterFacade {
    
    @PersistenceContext
    private EntityManager em;
    
    
    //find user id in student,lecturer table and set password in it
    public String registerUser(String userID,String password)
    {
        
        String msg="User ID not found";
        TypedQuery<Student> studentQuery=em.createNamedQuery("Student.findByStudentId", Student.class);
        studentQuery.setParameter("studentId", userID);
        List<Student>students=studentQuery.getResultList();
        if(students.size()>0)
        {
            Student s=students.get(0);
            if(s.getPassword()!=null && s.getPassword().equalsIgnoreCase(Constants.DEFAULT_PASSWORD))
            {
                s.setPassword(password);
                msg=Constants.SUCCESS;
                em.persist(s);
            }
            else
            {
                msg="ALREADY_REGISTERED";
            }
            
            return msg;
        }
        
        
        
        TypedQuery<Lecturer> lecturerQuery=em.createNamedQuery("Lecturer.findByLecturerId", Lecturer.class);
        lecturerQuery.setParameter("lecturerId", userID);
        List<Lecturer> lecturers=lecturerQuery.getResultList();
        if(lecturers.size()>0)
        {
            Lecturer l=lecturers.get(0);
            if(l.getPassword()!=null && l.getPassword().equalsIgnoreCase(Constants.DEFAULT_PASSWORD))
            {
                l.setPassword(password);
                msg="SUCCESS";
                em.persist(l);
            }
            else
            {
                msg="ALREADY_REGISTERED";
            }
            
            return msg;
        }      
        
        return msg;
    }
}
