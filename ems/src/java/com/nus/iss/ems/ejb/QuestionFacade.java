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
import com.nus.iss.ems.temp.AbstractFacade;
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Milan
 */
@Stateless
public class QuestionFacade extends AbstractFacade<Question>{

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     public QuestionFacade() {
        super(Question.class);
    }
    
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

    public List<Question> retrieveQuestions(Module module, List<Question> exceptQuestions, List<SubjectTag> subjectTags, int totalMarks) {
        if (exceptQuestions == null) {
            exceptQuestions = new ArrayList<>();
        }

        if (subjectTags == null) {
            subjectTags = new ArrayList<>();
        }

        module = em.find(Module.class, module.getId());
        TypedQuery<Question> query = em.createNamedQuery("Question.findByDepreciatedAndModuleAndSubjectTags", Question.class);
        query.setParameter("depreciated", 0);
        query.setParameter("module", module);
        query.setParameter("subjectTags", subjectTags);
        //query.setParameter("questions", exceptQuestions);
        return query.getResultList();

    }
    
     

    public int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private List<List<Question>> solve(List<Question> questions, int totalMarks) {
        ArrayList<Question> questionsSelected = new ArrayList<Question>();
        int tmp = 0;
        List<List<Question>> res = new ArrayList<List<Question>>();
        helper(res, questions, totalMarks, tmp, questionsSelected, 0);
        return res;
    }

    private void helper(List<List<Question>> res, List<Question> questions, int totalMarks,
            int tmp, List<Question> questionsSelected, int currPosition) {
        while (currPosition < questions.size()) {
            if (tmp == totalMarks) {
                res.add(questionsSelected);
                int lastIndex = questionsSelected.size() - 1;
                Question question = questionsSelected.remove(lastIndex);
                helper(res, questions, totalMarks, tmp - question.getMark(), questionsSelected, currPosition + 1);

            } else {
                if ((questions.get(currPosition).getMark() <= totalMarks) && (questions.get(currPosition).getMark() + tmp <= totalMarks)) {
                    questionsSelected.add((questions.get(currPosition)));
                    helper(res, questions, totalMarks, tmp + questions.get(currPosition).getMark(), questionsSelected, currPosition + 1);
                }
                if ((questions.get(currPosition).getMark() <= totalMarks) && (questions.get(currPosition).getMark() + tmp > totalMarks)) {
                    helper(res, questions, totalMarks, tmp, questionsSelected, currPosition + 1);
                    if (questionsSelected.size() >= 1) {
                        int lastIndex = questionsSelected.size() - 1;
                        Question question = questionsSelected.remove(lastIndex);
                        helper(res, questions, totalMarks, tmp - question.getMark(), questionsSelected, currPosition + 1);
                    }
                }
            }
        }
    }

}
