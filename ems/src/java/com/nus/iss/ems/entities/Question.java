package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import com.nus.iss.ems.enums.QuestionType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Milan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByCreatedOn", query = "SELECT q FROM Question q WHERE q.createdOn = :createdOn"),
    @NamedQuery(name = "Question.findByMark", query = "SELECT q FROM Question q WHERE q.mark = :mark"),
    @NamedQuery(name = "Question.findByQuestionText", query = "SELECT q FROM Question q WHERE q.questionText = :questionText"),
    @NamedQuery(name = "Question.findByQuestiontype", query = "SELECT q FROM Question q WHERE q.questionType = :questionType"),
    @NamedQuery(name = "Question.findByVersion", query = "SELECT q FROM Question q WHERE q.version = :version"),
    @NamedQuery(name = "Question.findByDepreciated", query = "SELECT q FROM Question q WHERE q.depreciated = :depreciated"),
@NamedQuery(name = "Question.findByDepreciatedAndModule", query = "SELECT q FROM Question q WHERE q.depreciated = :depreciated and q.module=:module and q.version = ( select max(q1.version) FROM Question q1 where q1.id=q.id )")})
public class Question extends AbstractEntity implements Serializable  {
  
    
    
    private Integer version=0;
    private Date createdOn;
    @ManyToOne
    private Lecturer createdBy;
    private Integer mark;
    private String questionText;
    
    private Question previousQuestion;

    public Question getPreviousQuestion() {
        return previousQuestion;
    }

    public void setPreviousQuestion(Question previousQuestion) {
        this.previousQuestion = previousQuestion;
    }
    
    
    
    @ManyToOne
    private Module module;
    
    @Enumerated(EnumType.ORDINAL)
    private QuestionType questionType=QuestionType.MCQ_OneCorrect;
    
    @ManyToMany
    private List<SubjectTag> subjectTags=new ArrayList<SubjectTag>();
    
    @ManyToMany
    private List<ExamSection> examSections;
    
    @OneToMany(mappedBy = "question")
    private List<QuestionOption> questionOptions=new ArrayList<QuestionOption>();
    
    @OneToMany(mappedBy = "question")
    private List<StudentAnswer> studentAnswers;
    
    private Integer depreciated=0;

    public Integer getDepreciated() {
        return depreciated;
    }

    public void setDepreciated(Integer depreciated) {
        this.depreciated = depreciated;
    }

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
