
package com.nus.iss.ems.ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;


/**
 *
 * @author Milan
 */
@Stateless
public class RegisterFacade {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "jdbc/ems")
    DataSource datasource;

    //find user id in student,lecturer table and set password in it
    public String registerUser(String userID, String password)  {
        String msg = "User ID not found or you are Already Registered";
        Connection connection=null;
        try {
            Properties prop=new Properties();
            prop.put("user", "root");
            prop.put("password", "root");
                    
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", prop);
            
            //not working table not found exception
           //  connection = datasource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("update USERS set PASSWORD=? where USERID=? and PASSWORD='password'");
            stmt.setString(1, password);
            stmt.setString(2, userID);
            int result = stmt.executeUpdate();
            stmt.close();
            if (result == 0) {
                return msg;
            } else {
                msg = "SUCCESS";
                return msg;
            }
            

        } catch (SQLException e) {
            return e.getMessage();
        }
        finally{
           if(connection!=null) try {
               connection.close();
           } catch (SQLException ex) {
               Logger.getLogger(RegisterFacade.class.getName()).log(Level.SEVERE, null, ex);
           }
        }

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
}
