
package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Milan
 */
@Entity
public class StudentAnswer extends AbstractEntity implements Serializable{
    
    private String answer;
    
    @ManyToOne
    private Question question;
    
    @ManyToMany
    private List<QuestionOption> optionsSelected;
    
    @ManyToOne 
    private ExamSession examSession;
    
    public StudentAnswer(Question q,QuestionOption qp){
        
        this.question=q;
        optionsSelected= new ArrayList<QuestionOption>();
        if(optionsSelected!= null)
            this.optionsSelected.add(qp);
        else
            System.out.println("List reference variable is null");
        
    }
    
    public StudentAnswer(Question q,List<QuestionOption> qp){
        
        this.question=q;
        optionsSelected= new ArrayList<QuestionOption>();
        if(optionsSelected!= null)
            this.optionsSelected=qp;
        else
            System.out.println("List reference variable is null");
        
    }
    
    public StudentAnswer(Question q, String essayAns){
        
        this.question=q;
        this.answer= essayAns;
        
    }
    public StudentAnswer(){
        
    }
    
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<QuestionOption> getOptionsSelected() {
        return optionsSelected;
    }

    public void setOptionsSelected(List<QuestionOption> optionsSelected) {
        this.optionsSelected = optionsSelected;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAnswer)) {
            return false;
        }
        StudentAnswer other = (StudentAnswer) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nus.iss.ems.entities.StudentAnswer[ id=" + getId() + " ]";
    }
    
}
