
package com.nus.iss.ems.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import com.nus.iss.ems.enums.SectionType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 *
 * @author Milan
 */
@Entity
public class ExamSection extends AbstractEntity implements Serializable  {
    
    private String sectionName;
    private Integer totalMarks=0;
    
    @Transient
    private List<Question> questionSelected;
    
    @Enumerated(EnumType.ORDINAL)
    private SectionType sectionType;
    
    @ManyToMany
    private List<SubjectTag> subjectTags=new ArrayList<SubjectTag>();
    
    @ManyToOne
    private ExamPaper examPaper;
    
    @ManyToMany
    private List<Question> questions=new ArrayList<Question>();

    public List<Question> getQuestionSelected() {
        return questionSelected;
    }

    public void setQuestionSelected(List<Question> questionSelected) {
        this.questionSelected = questionSelected;
    }

    
    
    public List<SubjectTag> getSubjectTags() {
        return subjectTags;
    }

    public void setSubjectTags(List<SubjectTag> subjectTags) {
        this.subjectTags = subjectTags;
    }

    
    
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public ExamPaper getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(ExamPaper examPaper) {
        this.examPaper = examPaper;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
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
        if (!(object instanceof ExamSection)) {
            return false;
        }
        ExamSection other = (ExamSection) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getSectionName()+ ", Total Marks:" + getTotalMarks() ;
    }
    
}
