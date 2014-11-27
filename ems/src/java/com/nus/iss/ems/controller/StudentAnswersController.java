/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.ejb.StudentAnswersFacade;
import com.nus.iss.ems.entities.ExamSessionController;
import com.nus.iss.ems.entities.StudentAnswer;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author nisithsingh
 */
@Named
@RequestScoped
public class StudentAnswersController {
    
    @Inject ExamPaperView ePaperView;
    
    
    private StudentAnswer current;
    
    @EJB
    private StudentAnswersFacade ejbFacade;
    
    public String createStudentAnswers(){
        //System.out.println(ub.getStudent().getStudentName());
        //current= new StudentAnswers();
         for(StudentAnswer ans: ePaperView.getStudentAnswers()){
             ans.setExamSession(ePaperView.getEsession());
              StudentAnswer sAns= ejbFacade.createStudentAnswer(ans);
         }
       
        System.out.println("Session created");
        return "ListModule?faces-redirect=true";
    }
    
    
}
