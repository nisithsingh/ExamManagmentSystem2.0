
package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
    
    private Date date;
   
    
    

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
