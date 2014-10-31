
package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Milan
 */
@Entity
public class ExamSession extends AbstractEntity implements Serializable {
    
    @Column(unique = true)
    private String sessionId;
    
    @ManyToOne
    private ExamPaper examPaper;
    
    @ManyToOne
    private Student student;
    
    private Date startDate;
    
    @OneToMany(mappedBy = "examSession")
    private List<StudentAnswer> studentAnswers;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ExamPaper getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(ExamPaper examPaper) {
        this.examPaper = examPaper;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getDate() {
        return startDate;
    }

    public void setDate(Date date) {
        this.startDate = date;
    }

    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
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
        if (!(object instanceof ExamSession)) {
            return false;
        }
        ExamSession other = (ExamSession) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nus.iss.ems.entities.ExamSession[ id=" + getId() + " ]";
    }
    
}
