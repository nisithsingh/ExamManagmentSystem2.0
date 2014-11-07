/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.common.AbstractFacade;
import com.nus.iss.ems.entities.Admin;
import com.nus.iss.ems.entities.Lecturer;
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
public class AdminFacade extends AbstractFacade<Admin> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }
    
    public Admin findAdmin(String name) {
        TypedQuery<Admin> query = em.createNamedQuery("Admin.findByName", Admin.class);
        query.setParameter("name", name);
        List<Admin> admins = query.getResultList();
        if (admins.size() > 0) {
            return admins.get(0);
        } else {
            return null;
        }

    }
}
