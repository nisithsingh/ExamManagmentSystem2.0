
package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Milan
 */

@NamedQueries({
    @NamedQuery(name = "ExamPaper.getProgressedPaper", query = "SELECT e FROM ExamPaper e where e.startDate = CURRENT_DATE ")
    })
@Entity
public class ExamPaper extends AbstractEntity implements Serializable  {
    
    private String name;
    private Date createDate=new Date(System.currentTimeMillis());
    private Date startDate;
    private Integer duration;
    
    @ManyToOne
    private Module module;

    @OneToMany(mappedBy = "examPaper",cascade = CascadeType.REMOVE)
    List<ExamSection> sections=new ArrayList<ExamSection>();
    
    @OneToMany(mappedBy = "examPaper" , cascade = CascadeType.ALL)
    List<ExamSession> examSession;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<ExamSection> getSections() {
        return sections;
    }

    public void setSections(List<ExamSection> sections) {
        this.sections = sections;
    }

    public List<ExamSession> getExamSession() {
        return examSession;
    }

    public void setExamSession(List<ExamSession> examSession) {
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
        if (!(object instanceof ExamPaper)) {
            return false;
        }
        ExamPaper other = (ExamPaper) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nus.iss.ems.entities.ExamPaper[ id=" + getId() + " ]";
    }
    
}
