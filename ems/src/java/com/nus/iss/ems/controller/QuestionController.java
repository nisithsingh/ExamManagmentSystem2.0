/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.ejb.ModuleFacade;
import com.nus.iss.ems.ejb.QuestionFacade;
import com.nus.iss.ems.ejb.SubjectTagFacade;
import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.Question;
import com.nus.iss.ems.entities.QuestionOption;
import com.nus.iss.ems.entities.SubjectTag;
import com.nus.iss.ems.enums.QuestionType;
import com.nus.iss.ems.service.QuestionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Named;

/**
 *
 * @author Milan
 */
@ViewScoped
@Named
public class QuestionController implements Serializable {

    @EJB
    private ModuleFacade moduleFacade;

    @EJB
    private QuestionFacade questionFacade;

    @EJB
    private SubjectTagFacade subjectTagFacade;

    private List<Module> modules;

    private boolean isInCreateMode = true;

    private String dailogTitle = "Create Question";

    //private Module moduleSelected;
    // private List<SubjectTag> subjectTags;
    // private List<SubjectTag> subjectTagsSelected = new ArrayList<SubjectTag>();
    //private String questionText;
    //private List<String> options = new ArrayList<String>();
    private String option;

    private Question question;

    //private int mark;
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    private List<Question> questions;

    //private Question questionSelected;
//    public Question getQuestionSelected() {
//        return questionSelected;
//    }
//
//    public void setQuestionSelected(Question questionSelected) {
//        this.questionSelected = questionSelected;
//    }
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

//    public int getMark() {
//        return mark;
//    }
//
//    public void setMark(int mark) {
//        this.mark = mark;
//    }
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

//    public List<String> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<String> options) {
//        this.options = options;
//    }
//
//    public String getQuestionText() {
//        return questionText;
//    }
//
//    public void setQuestionText(String questionText) {
//        this.questionText = questionText;
//    }
    //private QuestionType questionType = QuestionType.MCQ_OneCorrect;
//    public QuestionType getQuestionType() {
//        return questionType;
//    }
//
//    public void setQuestionType(QuestionType questionType) {
//        this.questionType = questionType;
//    }
//    public List<SubjectTag> getSubjectTags() {
//        return subjectTags;
//    }
//
//    public void setSubjectTags(List<SubjectTag> subjectTags) {
//        this.subjectTags = subjectTags;
//    }
//
//    public List<SubjectTag> getSubjectTagsSelected() {
//        return subjectTagsSelected;
//    }
//
//    public void setSubjectTagsSelected(List<SubjectTag> subjectTagsSelected) {
//        this.subjectTagsSelected = subjectTagsSelected;
//    }
//    public Module getModuleSelected() {
//        return moduleSelected;
//    }
//
//    public void setModuleSelected(Module moduleSelected) {
//        this.moduleSelected = moduleSelected;
//    }
    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String getDailogTitle() {
        return dailogTitle;
    }

    public void setDailogTitle(String dailogTitle) {
        this.dailogTitle = dailogTitle;
    }

    public boolean isIsInCreateMode() {

        return isInCreateMode;
    }

    public void setIsInCreateMode(boolean isInCreateMode) {
        if (isInCreateMode) {
            setDailogTitle("Create Question");
        } else {
            setDailogTitle("Modify Question");
        }
        this.isInCreateMode = isInCreateMode;
    }

    @PostConstruct
    public void init() {

        question = new Question();

        retrieveModules();
        if (question.getModule() != null) {
            retrieveQuestions();
        }
        // questionSelected = new Question();

    }

    public List<Module> retrieveModules() {
        String lecturerID = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();

        modules = moduleFacade.retireveAllModules(lecturerID);
        if (modules != null && modules.size() > 0) {
            // moduleSelected = modules.get(0);
            question.setModule(modules.get(0));
            // retrieveQuestions();

        }
        return modules;
    }

    public List<SubjectTag> retrieveSubjectTags() {
        List<SubjectTag> subjectTags = subjectTagFacade.retireveAllSubjectTags();
        if (subjectTags != null && subjectTags.size() > 0) {
            //subjectTagsSelected.add(subjectTags.get(0));
            question.getSubjectTags().add(subjectTags.get(0));
        }
        return subjectTags;
    }

    public QuestionType[] getQuestionTypes() {
        return QuestionType.values();
    }

    public void addOption(String option) {
        QuestionOption qo = new QuestionOption();
        qo.setQuestion(question);
        qo.setValue(option);
        question.getQuestionOptions().add(qo);
        //options.add(option);
        this.option = "";
    }

    public void removeOption(QuestionOption option) {
        question.getQuestionOptions().remove(option);

    }

    public void createorModifyQuestion() {
//        System.out.println("module :" + moduleSelected);
//        System.out.println("subjectTags size: " + subjectTags.size());
//        System.out.println("Question Type :" + questionType.getLabel());
//        System.out.println("Question Text:" + questionText);
//        System.out.println("Options Size :" + options.size());
//        
//        question.setMark(mark);
//        question.setQuestionText(questionText);
//        question.setQuestionType(questionType);
//        question.setSubjectTags(subjectTags);
//        question.setModule(moduleSelected);

        Map<String, String> errors = new QuestionService().validateQuestion(question);
        FacesContext context = FacesContext.getCurrentInstance();
        if (!errors.isEmpty()) {

            for (String key : errors.keySet()) {
                FacesMessage error = new FacesMessage(errors.get(key));
                context.addMessage(key, error);
            }
        } else {
            if (isInCreateMode) {
                String lecturerID = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
                Question newQuestion = questionFacade.createQuestion(lecturerID, question);
                if (newQuestion == null) {
                    FacesMessage error = new FacesMessage("Error Occured while saving Question");
                    context.addMessage(null, error);
                } else {
                    questions.add(newQuestion);
                    FacesMessage error = new FacesMessage("Question Created Successfully");
                    context.addMessage(null, error);
                    reset();
                }
            } else {
                int index=questions.indexOf(question);
                Question updatedQuestion = questionFacade.modifyQuestion(question);
                if (updatedQuestion == null) {
                    FacesMessage error = new FacesMessage("Error Occured while saving Question");
                    context.addMessage(null, error);
                } else {
                    questions.remove(index);
                    questions.add(index, updatedQuestion);
                    FacesMessage error = new FacesMessage("Question Modified Successfully");
                    context.addMessage(null, error);
                    reset();
                }
            }

        }

    }

    public void reset() {
        question = new Question();
//        moduleSelected = null;
//        subjectTags = null;
//        questionType = questionType.MCQ_OneCorrect;
//        questionText = "";
//        options = new ArrayList<String>();
//        mark = 0;
//        questionSelected = new Question();
    }

    public List<Question> retrieveQuestions() {
        questions = questionFacade.retrieveQuestions(question.getModule());
        return questions;
    }

    public void modifyQuestion(Question question) {

    }

    public SubjectTag getSubject(java.lang.Long id) {
        return subjectTagFacade.findSubjectTag(id);
    }

    public Module getModule(java.lang.Long id) {
        return moduleFacade.findModule(id);
    }

    public void depreciateQuestion(Question question) {

        boolean result = questionFacade.depreciateQuestion(question);
        if (result) {
            questions.remove(question);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(question.getQuestionText() + " Depreciated Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error occured while depreciating Question"));

        }
    }

    public void setQuestionToModify(Question question) {
        this.question = question;
        setIsInCreateMode(false);
    }

    public void setQuestionToCreate() {
        reset();
        setIsInCreateMode(true);
    }

//     public void onQuestionTypeChanged()
//     {
//         if(questionType.ordinal()==1)
//         {
//             
//         }
//     }
}
