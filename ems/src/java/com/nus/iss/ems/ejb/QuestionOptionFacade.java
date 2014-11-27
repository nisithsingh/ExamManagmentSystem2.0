/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.QuestionOption;
import com.nus.iss.ems.entities.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nisithsingh
 */
@Stateless
public class QuestionOptionFacade extends AbstractFacade<QuestionOption>{
    
    @PersistenceContext
    private EntityManager em;
    
    public QuestionOptionFacade()
    {
     super(QuestionOption.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){      
        return em;
    }
    
    public QuestionOption findQuestionOption(Long id) {
         return em.find(QuestionOption.class, id);
    }
    
    //public List<QuestionOption> findQuestionOptions
    
}
