/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.entities.ExamSection;
import com.nus.iss.ems.entities.ExamSession;
import com.nus.iss.ems.entities.Question;
import com.nus.iss.ems.entities.QuestionOption;
import com.nus.iss.ems.entities.StudentAnswer;
import com.nus.iss.ems.enums.QuestionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;


/**
 *
 * @author nisithsingh
 */

@SessionScoped
@Named
public class ExamPaperView implements Serializable{
    
    @Inject private UserBean ub;
    
    private int timeRemaining = 10;
    
    private String essayAns;

    public String getEssayAns() {
        return essayAns;
    }

    public void setEssayAns(String essayAns) {
        this.essayAns = essayAns;
    }
    private ExamPaper ePaper;
    
    private List<ExamSection> examSections;

    public List<ExamSection> getExamSections() {
        return examSections;
    }

    public void setExamSections(List<ExamSection> examSections) {
        this.examSections = examSections;
    }
    
    private ExamSession esession;
    
    public ExamSession getEsession() {
        return esession;
    }

    public void setEsession(ExamSession esession) {
        this.esession = esession;
    }
    
    private boolean prevVal =false;

    public boolean isPrevVal() {
        return prevVal;
    }

    public void setPrevVal(boolean prevVal) {
        this.prevVal = prevVal;
    }

    public void setePaper(ExamPaper ePaper) {
        this.ePaper = ePaper;
    }
    
    private ExamSection sectionA;
    private ExamSection sectionB;
    private ExamSection sectionC;
    private ExamSection sectionD;
    
    private Question currQsn;
    
    private Question currMCQ_OneCorrectQsn;
    private Question currMCQ_MultipleCorrectQsn;
    private Question curr_ESSAY;
    
    private List<StudentAnswer> studentAnswers;

    private ExamSection currentSection;
    
    private QuestionOption currAnsQt;

    public QuestionOption getCurrAnsQt() {
        return currAnsQt;
    }

    public void setCurrAnsQt(QuestionOption currAnsQt) {
        this.currAnsQt = currAnsQt;
    }

    public List<QuestionOption> getCurrAnsListQt() {
        return currAnsListQt;
    }

    public void setCurrAnsListQt(List<QuestionOption> currAnsListQt) {
        this.currAnsListQt = currAnsListQt;
    }

    public String getCurrAnsEssay() {
        return currAnsEssay;
    }

    public void setCurrAnsEssay(String currAnsEssay) {
        this.currAnsEssay = currAnsEssay;
    }
    private List<QuestionOption> currAnsListQt;
    private String currAnsEssay;
    
    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
    
    @PostConstruct
    public void initList(){
        
        studentAnswers = new ArrayList<StudentAnswer>();
        
    }
    
    public void init() {
        
        studentAnswers = new ArrayList<StudentAnswer>();
        
        System.out.println("ININT:" + ePaper.toString());
               

        for (ExamSection s : ePaper.getSections()) {
            System.out.println(s.getQuestions().size());
        }
        
        examSections=ePaper.getSections();

    }
    
    public void endExam(){
        
        RequestContext.getCurrentInstance().execute("testDialog2.show()");
        System.out.println("Exam end");
        //return "thanks?faces-redirect=true";
        
    } 
    
    public void submitAnswer(){
        
        if(currMCQ_OneCorrectQsn!=null){
           System.out.println("One Correct"+currAnsQt.getValue());
           
           for(int i=0; i< studentAnswers.size();i++){
               
               if(currMCQ_OneCorrectQsn.equals(studentAnswers.get(i).getQuestion())){
                   studentAnswers.get(i).getOptionsSelected().clear();
                   studentAnswers.get(i).getOptionsSelected().add(currAnsQt);
                          
                   return;
               }
           }
           
           this.studentAnswers.add(new StudentAnswer(this.currMCQ_OneCorrectQsn,this.currAnsQt));
        }
        else if(currMCQ_MultipleCorrectQsn!=null){
            
           System.out.println("Multiple Correct"+ currAnsListQt);
           for(int i=0; i< studentAnswers.size();i++){
               
               if(currMCQ_MultipleCorrectQsn.equals(studentAnswers.get(i).getQuestion())){
                   studentAnswers.get(i).getOptionsSelected().clear();
                   studentAnswers.get(i).setOptionsSelected(currAnsListQt);
                          
                   return;
               }
           }
           
           this.studentAnswers.add(new StudentAnswer(this.currMCQ_MultipleCorrectQsn,this.currAnsListQt));
        }
        else if(curr_ESSAY!=null){
            System.out.println("Essay");
            this.studentAnswers.add(new StudentAnswer(this.curr_ESSAY,this.currAnsEssay));
        }
        
    }
    
    public void printAnswers(){
        if(currMCQ_OneCorrectQsn!=null){
            for(StudentAnswer ans: studentAnswers){
                System.out.println(ans.getQuestion()+":"+ans.getOptionsSelected());
            }
        }
        else if(currMCQ_MultipleCorrectQsn!=null){
            for(StudentAnswer ans: studentAnswers){
                System.out.println(ans.getQuestion()+":");
                for(QuestionOption qo: ans.getOptionsSelected())
                    System.out.println(qo.getValue()+"\n");
            }
        }
    }
    
    public Question getCurrMCQ_OneCorrectQsn() {
        return currMCQ_OneCorrectQsn;
    }

    public void setCurrMCQ_OneCorrectQsn(Question currMCQ_OneCorrectQsn) {
        this.currMCQ_OneCorrectQsn = currMCQ_OneCorrectQsn;
    }

    public Question getCurrMCQ_MultipleCorrectQsn() {
        return currMCQ_MultipleCorrectQsn;
    }

    public void setCurrMCQ_MultipleCorrectQsn(Question currMCQ_MultipleCorrectQsn) {
        this.currMCQ_MultipleCorrectQsn = currMCQ_MultipleCorrectQsn;
    }

    public Question getCurr_ESSAY() {
        return curr_ESSAY;
    }

    public void setCurr_ESSAY(Question curr_ESSAY) {
        this.curr_ESSAY = curr_ESSAY;
    }
   
    
    
    private int currCursor;
    
    private String currSection;
    private boolean nextVal = true;

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public ExamSection getSectionA() {
        return sectionA;
    }

    public void setSectionA(ExamSection sectionA) {
        this.sectionA = sectionA;
    }

    public ExamSection getSectionB() {
        return sectionB;
    }

    public void setSectionB(ExamSection sectionB) {
        this.sectionB = sectionB;
    }

    public ExamSection getSectionC() {
        return sectionC;
    }

    public void setSectionC(ExamSection sectionC) {
        this.sectionC = sectionC;
    }

    public ExamSection getSectionD() {
        return sectionD;
    }

    public void setSectionD(ExamSection sectionD) {
        this.sectionD = sectionD;
    }

    public Question getCurrQsn() {
        return currQsn;
    }

    public void setCurrQsn(Question currQsn) {
        this.currQsn = currQsn;
    }

    public int getCurrCursor() {
        return currCursor;
    }

    public void setCurrCursor(int currCursor) {
        this.currCursor = currCursor;
    }

    public String getCurrSection() {
        return currSection;
    }

    public void setCurrSection(String currSection) {
        this.currSection = currSection;
    }

    public boolean isNextVal() {
        return nextVal;
    }

    public void setNextVal(boolean nextVal) {
        this.nextVal = nextVal;
    }
    
  
    
    
    public String getTypeOfQuestion(Question question) {

        if (question.getQuestionType()==QuestionType.ESSAY) {
            return "EssayQuestion";
        } else if (question.getQuestionType()==QuestionType.MCQ_OneCorrect) {
            return "MCQ_OneCorrect";
        } else if (question.getQuestionType()==QuestionType.MCQ_MultipleCorrect) {
            return "MCQ_MultipleCorrect";
        } else {
            return null;
        }

    }
    
    public void startSection(ExamSection o){
        
        currMCQ_MultipleCorrectQsn=null;
        currMCQ_OneCorrectQsn=null;
        curr_ESSAY=null;
        
        setNextVal(true);
        setPrevVal(false);
        
        currentSection = o;
        
         Question q= currentSection.getQuestions().get(0);
            this.setCurrQsn(q);
            this.currCursor=0;
        
        if (currentSection.getQuestions().size() > 0) {
            if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("EssayQuestion")) {
                
               this.setCurr_ESSAY(q);
                
            } else if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("MCQ_OneCorrect")) {
                
                this.setCurrMCQ_OneCorrectQsn(q);
                
            } else if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("MCQ_MultipleCorrect")) {
               
                this.setCurrMCQ_MultipleCorrectQsn(q);
            }
            
        }
        
        
        
    }
    
   
    
    public void decreaseCount() {
        timeRemaining = timeRemaining - 1;
        System.out.println(timeRemaining);

    }
    
    public void nextSectionQuestion() {
        
        if(currentSection!=null){
            
            currMCQ_MultipleCorrectQsn=null;
            currMCQ_OneCorrectQsn=null;
            curr_ESSAY=null;
        
            setPrevVal(true);
            currCursor += 1;
            if (currentSection.getQuestions().size() > currCursor) {

                nextVal = true;

                Question q= currentSection.getQuestions().get((currCursor));
                this.setCurrQsn(q);
                
            if (currentSection.getQuestions().size() > 0) {
            if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("EssayQuestion")) {
                
               this.setCurr_ESSAY(q);
                
            } else if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("MCQ_OneCorrect")) {
                
                this.setCurrMCQ_OneCorrectQsn(q);
                
            } else if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("MCQ_MultipleCorrect")) {
               
                this.setCurrMCQ_MultipleCorrectQsn(q);
            }
            }
                 if(currCursor==currentSection.getQuestions().size()-1){
                    setNextVal(false);
                    return;
                    }
                } else {
                System.out.println("false next");
                nextVal = false;
                //currSection = null;
                }
            }
        }
       
    
    public void prevSectionQuestion() {
    
     if(currentSection!=null){
         
        currMCQ_MultipleCorrectQsn=null;
        currMCQ_OneCorrectQsn=null;
        curr_ESSAY=null;
        
            setNextVal(true);
            currCursor -= 1;
            
        if (currentSection.getQuestions().size() > currCursor && currCursor>=0) {

                prevVal = true;

                Question q= currentSection.getQuestions().get((currCursor));
                this.setCurrQsn(q);
                
        if (currentSection.getQuestions().size() > 0) {
            if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("EssayQuestion")) {
                
               this.setCurr_ESSAY(q);
                
            } else if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("MCQ_OneCorrect")) {
                
                this.setCurrMCQ_OneCorrectQsn(q);
                
            } else if (getTypeOfQuestion(currentSection.getQuestions().get(0)).equals("MCQ_MultipleCorrect")) {
               
                this.setCurrMCQ_MultipleCorrectQsn(q);
            }
            
        }
                
                if(currCursor==0){
                    prevVal = false;
                    return;
                }
                    
            } else {
                System.out.println("false next");
                prevVal = false;
                //currSection = null;
            }
     }
        
        
    }
   
    
    }

