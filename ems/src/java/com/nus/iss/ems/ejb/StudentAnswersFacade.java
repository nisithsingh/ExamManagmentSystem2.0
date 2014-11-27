/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.common.AbstractFacade;
import com.nus.iss.ems.entities.StudentAnswer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
/**
 *
 * @author nisithsingh
 */
@Stateless
public class StudentAnswersFacade extends AbstractFacade<StudentAnswer>{
   
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    public StudentAnswersFacade(){
        super(StudentAnswer.class);
    }
    
    public StudentAnswer createStudentAnswer(StudentAnswer sans){
        
     
        
        em.persist(sans);
      
        return sans;
    }
}
