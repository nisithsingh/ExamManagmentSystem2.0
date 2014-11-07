/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

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
public class LecturerFacade {

    @PersistenceContext
    private EntityManager em;

    public Lecturer findLecturer(String lecturerId) {
        TypedQuery<Lecturer> query = em.createNamedQuery("Lecturer.findByLecturerId", Lecturer.class);
        query.setParameter("lecturerId", lecturerId);
        List<Lecturer> lecturers = query.getResultList();
        if (lecturers.size() > 0) {
            return lecturers.get(0);
        } else {
            return null;
        }

    }
}
