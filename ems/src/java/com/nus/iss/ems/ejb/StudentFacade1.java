/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Student;
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
public class StudentFacade1 {
    
    @Resource(name = "jdbc/ems")
    DataSource datasource;
    @PersistenceContext
    private EntityManager em;
    
    public List<Student> findAll() {
        TypedQuery<Student> query = em.createNamedQuery("Student.findAll", Student.class);
        List<Student> students = query.getResultList();
        if (students.size() > 0) {
            return students;
        } else {
            return null;
        }
        
    }

    //find user id in student,lecturer table and set password in it
    public String registerUser(String id, String name) {
        String msg = "User ID not found or you are Already Registered";
        Connection connection = null;
        try {
            Properties prop = new Properties();
            prop.put("user", "root");
            prop.put("password", "root");
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", prop);

            //not working table not found exception
            //  connection = datasource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into Users (userid, password) VALUES (?,?)");
            stmt.setString(1, id);
            stmt.setString(2, "password");
            int result = stmt.executeUpdate();
            stmt.close();
//            if (result == 0) {
//                return msg;
//            } else {
//                msg = "SUCCESS";
//                return msg;
//            }

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
        
        Student student = new Student();
        student.setStudentId(id);
        student.setStudentName(name);
        em.persist(student);
        return "SUCCESS";

//        TypedQuery<Student> studentQuery = em.createNamedQuery("Student.findByStudentId", Student.class);
//        studentQuery.setParameter("studentId", userID);
//        List<Student> students = studentQuery.getResultList();
//        if (students.size() > 0) {
//            Student s = students.get(0);
//            if (s.getPassword() != null && s.getPassword().equalsIgnoreCase(Constants.DEFAULT_PASSWORD)) {
//                s.setPassword(password);
//                msg = Constants.SUCCESS;
//                em.persist(s);
//            } else {
//                msg = "ALREADY_REGISTERED";
//            }
//
//            return msg;
//        }
//
//        TypedQuery<Lecturer> lecturerQuery = em.createNamedQuery("Lecturer.findByLecturerId", Lecturer.class);
//        lecturerQuery.setParameter("lecturerId", userID);
//        List<Lecturer> lecturers = lecturerQuery.getResultList();
//        if (lecturers.size() > 0) {
//            Lecturer l = lecturers.get(0);
//            if (l.getPassword() != null && l.getPassword().equalsIgnoreCase(Constants.DEFAULT_PASSWORD)) {
//                l.setPassword(password);
//                msg = "SUCCESS";
//                em.persist(l);
//            } else {
//                msg = "ALREADY_REGISTERED";
//            }
//
//            return msg;
//        }
//
//        return msg;
    }

    public Student findStudent(Long id) {
        return em.find(Student.class, id);
    }
}
