/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.ExamSession;
import com.nus.iss.ems.common.AbstractFacade;
import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.entities.Student;
import java.sql.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nisithsingh
 */
@Stateless
public class ExamSessionFacade extends AbstractFacade<ExamSession>{
    
    @PersistenceContext(unitName = "emsPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    public ExamSessionFacade(){
        super(ExamSession.class);
    }
    
        public ExamSession createExamSession(Student student, ExamSession esession,ExamPaper ePaper) {


            esession.setStudent(student);
            esession.setDate(new Date(System.currentTimeMillis()));
            esession.setExamPaper(ePaper);
            
//        question.setCreatedBy(lecturer);
//        question.setCreatedOn(new Date(System.currentTimeMillis()));
//            question.setMark(mark);
//            question.setQuestionText(questionText);
//            question.setQuestionType(questionType);
//            question.setVersion(0);
//            question.setModule(module);
//            question.setSubjectTags(subjectTags);
        em.persist(esession);

        //save options
//        for (QuestionOption qo : question.getQuestionOptions()) {
//
//            qo.setQuestion(question);
//
//            em.persist(qo);
//
//        }

        em.flush();
        return esession;
       // }

        // return null;
    }
}
