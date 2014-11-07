/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Lecturer;
import com.nus.iss.ems.entities.Module;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 *
 * @author abhinav
 */
@Stateless
public class LecturerModuleFacade {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "jdbc/ems")
    DataSource datasource;

    public List<Module> retrieveLecturerModules(Lecturer lecturer) {
        lecturer = em.find(Lecturer.class, lecturer.getId());
        return lecturer.getModules();
    }

    public void updateStudentModules(Lecturer lecturer, List<Module> modules) {
        lecturer = em.find(Lecturer.class, lecturer.getId());
        lecturer.setModules(modules);
        em.persist(lecturer);
    }
    
    
}
