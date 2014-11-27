/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;

/**
 *
 * @author Milan
 */
@Entity
@NamedQueries(
        {
            //@NamedQuery(name="QuestionOptionList.findSelected",query="")
        }
)
public class QuestionOption extends AbstractEntity implements Serializable{
   
    private String optionValue;
    
    @ManyToOne
    private Question question;
    
    @ManyToMany(mappedBy = "optionsSelected")
    private List<StudentAnswer> studentAnswers;

    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
    
    
    
    public String getValue() {
        return optionValue;
    }

    public void setValue(String value) {
        this.optionValue = value;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
        if (!(object instanceof QuestionOption)) {
            return false;
        }
        QuestionOption other = (QuestionOption) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nus.iss.ems.entities.QuestionOption[ id=" + getId() + " ]";
    }
}
