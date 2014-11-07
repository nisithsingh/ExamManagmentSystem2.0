/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Lecturer;
import com.nus.iss.ems.entities.Module;
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
public class ModuleFacade {

    @PersistenceContext
    private EntityManager em;

    public List<Module> retireveAllModules(String lecturerId) {
        TypedQuery<Lecturer> query = em.createNamedQuery("Lecturer.findByLecturerId", Lecturer.class);
        query.setParameter("lecturerId", lecturerId);
        List<Lecturer> lecturers = query.getResultList();
        if (lecturers != null && lecturers.size() > 0) {
            return lecturers.get(0).getModules();
        } else {
            return new ArrayList<Module>();
        }
    }

    public Module findModule(Long id) {
         return em.find(Module.class, id);
    }
    
    public List<Module> retrieveAll(){
        TypedQuery<Module> query = em.createNamedQuery("Module.findAll", Module.class);
        List<Module> modules = query.getResultList();
        return modules;
    }
}
