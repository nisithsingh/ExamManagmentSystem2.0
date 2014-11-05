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
import com.nus.iss.ems.entities.SubjectTag;
import com.nus.iss.ems.enums.QuestionType;
import com.nus.iss.ems.service.QuestionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private Module moduleSelected;

    private List<SubjectTag> subjectTags;
    private List<SubjectTag> subjectTagsSelected = new ArrayList<SubjectTag>();

    private String questionText;

    private List<String> options = new ArrayList<String>();

    private String option;

    int mark;
    
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    private QuestionType questionType = QuestionType.MCQ_OneCorrect;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<SubjectTag> getSubjectTags() {
        return subjectTags;
    }

    public void setSubjectTags(List<SubjectTag> subjectTags) {
        this.subjectTags = subjectTags;
    }

    public List<SubjectTag> getSubjectTagsSelected() {
        return subjectTagsSelected;
    }

    public void setSubjectTagsSelected(List<SubjectTag> subjectTagsSelected) {
        this.subjectTagsSelected = subjectTagsSelected;
    }

    public Module getModuleSelected() {
        return moduleSelected;
    }

    public void setModuleSelected(Module moduleSelected) {
        this.moduleSelected = moduleSelected;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Module> retrieveModules() {
        String lecturerID = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();

        modules = moduleFacade.retireveAllModules(lecturerID);
        if (modules != null && modules.size() > 0) {
            moduleSelected = modules.get(0);
        }
        return modules;
    }

    public List<SubjectTag> retrieveSubjectTags() {
        subjectTags = subjectTagFacade.retireveAllSubjectTags();
        if (subjectTags != null && subjectTags.size() > 0) {
            subjectTagsSelected.add(subjectTags.get(0));
        }
        return subjectTags;
    }

    public QuestionType[] getQuestionTypes() {
        return QuestionType.values();
    }

    public void addOption(String option) {
        options.add(option);
        this.option = "";
    }

    public void removeOption(String option) {
        options.remove(option);
    }

    public void createQuestion() {
        System.out.println("module :" + moduleSelected);
        System.out.println("subjectTags size: " + subjectTags.size());
        System.out.println("Question Type :" + questionType.getLabel());
        System.out.println("Question Text:" + questionText);
        System.out.println("Options Size :" + options.size());

        Map<String, String> errors = new QuestionService().validateQuestion(moduleSelected, subjectTags, questionType, questionText, options,mark);
        FacesContext context = FacesContext.getCurrentInstance();
        if (!errors.isEmpty()) {

            for (String key : errors.keySet()) {
                FacesMessage error = new FacesMessage(errors.get(key));
                context.addMessage(key, error);
            }
        } else {
            String lecturerID = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
            Question question = questionFacade.createQuestion(lecturerID, moduleSelected, subjectTags, questionType, questionText, options, mark);
            if (question == null) {
                FacesMessage error = new FacesMessage("Error Occured while saving Question");
                context.addMessage(null, error);
            } else {
                FacesMessage error = new FacesMessage("Question Created Successfully");
                context.addMessage(null, error);
            }

//            String msg = registerFacade.registerUser(userID, password);
//
//            if (!msg.equals(Constants.SUCCESS)) {
//                FacesMessage error = new FacesMessage(msg);
//                context.addMessage(null, error);
//                
//            } else {
//                
//                
//                 FacesMessage error = new FacesMessage("Registered Successfully");
//                context.addMessage("registerSuccess", error);
//            }
        }

    }

//     public void onQuestionTypeChanged()
//     {
//         if(questionType.ordinal()==1)
//         {
//             
//         }
//     }
}
