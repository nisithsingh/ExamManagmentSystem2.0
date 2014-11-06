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

    public Question createQuestion(String lecturerId, Module module, List<SubjectTag> subjectTags, QuestionType questionType, String questionText, List<String> options, Integer mark) {

        TypedQuery<Lecturer> query = em.createNamedQuery("Lecturer.findByLecturerId", Lecturer.class);
        query.setParameter("lecturerId", lecturerId);
        List<Lecturer> lecturers = query.getResultList();
        Question question = new Question();
        if (lecturers != null && lecturers.size() > 0) {
            question.setCreatedBy(lecturers.get(0));
            question.setCreatedOn(new Date(System.currentTimeMillis()));
            question.setMark(mark);
            question.setQuestionText(questionText);
            question.setQuestionType(questionType);
            question.setVersion(0);
            question.setModule(module);
            em.persist(question);

            //save options
            for (String o : options) {
                QuestionOption option = new QuestionOption();
                option.setQuestion(question);
                option.setValue(o);
                em.persist(option);
                
                
            }

            return question;
        }

        return null;
    }

    public List<Question> retrieveQuestions(Module module) {
       
        em.flush();
        return module.getQuestions();
    }
}
