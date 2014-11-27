/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.common.Constants;
import com.nus.iss.ems.entities.Lecturer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

/**
 *
 * @author abhinav
 */
@Stateless
public class LecturerFacade1 {

    @Resource(name = "jdbc/ems")
    DataSource datasource;

    @PersistenceContext
    private EntityManager em;

    public List<Lecturer> findAll() {
        TypedQuery<Lecturer> query = em.createNamedQuery("Lecturer.findAll", Lecturer.class);
        List<Lecturer> lecturers = query.getResultList();
        if (lecturers.size() > 0) {
            return lecturers;
        } else {
            return null;
        }

    }

    //find user id in student,lecturer table and set password in it
    public String registerUser(String id, String name) {
        String msg = saveToUsersTable(id, name);
        if (msg.equals(Constants.SUCCESS)) {
            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerId(id);
            lecturer.setLecturerName(name);
            em.persist(lecturer);
            return "SUCCESS";
        } else {
            return msg;
        }

    }

    public String saveToUsersTable(String id, String name) {
        String msg = "User ID not found or you are Already Registered";
        Connection connection = null;
        try {
            Properties prop = new Properties();
            prop.put("user", "root");
            prop.put("password", "root");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", prop);

            PreparedStatement stmt = connection.prepareStatement("insert into Users (userid, password) VALUES (?,?)");
            stmt.setString(1, id);
            stmt.setString(2, "password");
            int result = stmt.executeUpdate();
            
            stmt = connection.prepareStatement("insert into users_groups (groupid, userid) VALUES (?,?)");
            stmt.setString(1, "lecturer");
            stmt.setString(2, id);
            result = stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            return e.getMessage();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentFacade1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "SUCCESS";
    }
    public Lecturer findLecturer(Long id) {
        return em.find(Lecturer.class, id);
    }
}
