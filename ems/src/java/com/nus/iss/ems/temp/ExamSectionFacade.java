/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.temp;

import com.nus.iss.ems.entities.ExamSection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Milan
 */
@Stateless
public class ExamSectionFacade extends AbstractFacade<ExamSection> {
    @PersistenceContext(unitName = "emsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamSectionFacade() {
        super(ExamSection.class);
    }
    
}
