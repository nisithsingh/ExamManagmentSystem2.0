package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import com.nus.iss.ems.enums.QuestionType;
import java.util.List;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Milan
 */
@Entity
public class Question extends AbstractEntity implements Serializable  {
  
    
    
    private Integer version;
    private Date createdOn;
    @ManyToOne
    private Lecturer createdBy;
    private Integer mark;
    private String questionText;
    
    @ManyToOne
    private Module module;
    
    @Enumerated(EnumType.ORDINAL)
    private QuestionType questionType;
    
    @ManyToMany(mappedBy = "questions")
    private List<SubjectTag> subjectTags;
    
    @ManyToMany
    private List<ExamSection> examSections;
    
    @OneToMany(mappedBy = "question")
    private List<QuestionOption> questionOptions;
    
    @OneToMany(mappedBy = "question")
    private List<StudentAnswer> studentAnswers;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    
    
    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    
    
    public List<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }

    
    
    public List<ExamSection> getExamSections() {
        return examSections;
    }

    public void setExamSections(List<ExamSection> examSections) {
        this.examSections = examSections;
    }
    
    public List<SubjectTag> getSubjectTags() {
        return subjectTags;
    }

    public void setSubjectTags(List<SubjectTag> subjectTags) {
        this.subjectTags = subjectTags;
    }
    
    

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Lecturer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Lecturer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nus.iss.ems.entities.Question[ id=" + getId() + " ]";
    }
    
}
