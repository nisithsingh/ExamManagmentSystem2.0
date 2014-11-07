/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Lecturer;
import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.Question;
import com.nus.iss.ems.entities.QuestionOption;
import com.nus.iss.ems.entities.SubjectTag;
import com.nus.iss.ems.enums.QuestionType;
import java.sql.Date;

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
public class QuestionFacade {

    @PersistenceContext
    private EntityManager em;

    

    public Question createQuestion(Lecturer lecturer, Question question) {

//        TypedQuery<Lecturer> query = em.createNamedQuery("Lecturer.findByLecturerId", Lecturer.class);
//        query.setParameter("lecturerId", lecturer);
//        List<Lecturer> lecturers = query.getResultList();
//        Question question = new Question();
        //if (lecturers != null && lecturers.size() > 0) {
        question.setCreatedBy(lecturer);
        question.setCreatedOn(new Date(System.currentTimeMillis()));
//            question.setMark(mark);
//            question.setQuestionText(questionText);
//            question.setQuestionType(questionType);
//            question.setVersion(0);
//            question.setModule(module);
//            question.setSubjectTags(subjectTags);
        em.persist(question);

        //save options
        for (QuestionOption qo : question.getQuestionOptions()) {

            qo.setQuestion(question);

            em.persist(qo);

        }

        em.flush();
        return question;
       // }

        // return null;
    }

    public Question modifyQuestion(Question question) {
        Question previousQuestion = em.find(Question.class, question.getId());
        previousQuestion.setDepreciated(1);
        em.persist(previousQuestion);

        question.setVersion(question.getVersion() + 1);
        question.setCreatedOn(new Date(System.currentTimeMillis()));
        question.setPreviousQuestion(previousQuestion);
        em.persist(question);
        for (QuestionOption qo : question.getQuestionOptions()) {

            qo.setQuestion(question);

            em.persist(qo);

        }

        em.flush();
        question = em.find(Question.class, question.getId());
        return question;
    }

    public List<Question> retrieveQuestions(Module module) {

        module = em.find(Module.class, module.getId());
        TypedQuery<Question> query = em.createNamedQuery("Question.findByDepreciatedAndModule", Question.class);
        query.setParameter("depreciated", 0);
        query.setParameter("module", module);
        return query.getResultList();
    }

    public boolean depreciateQuestion(Question question) {
        question = em.find(Question.class, question.getId());
        question.setDepreciated(1);
        em.persist(question);
        em.flush();
        return true;
    }

}
