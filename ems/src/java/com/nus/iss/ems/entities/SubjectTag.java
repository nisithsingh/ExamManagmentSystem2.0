
package com.nus.iss.ems.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Milan
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SubjectTag.findAll", query = "SELECT s FROM SubjectTag s"),
    @NamedQuery(name = "SubjectTag.findById", query = "SELECT s FROM SubjectTag s WHERE s.id = :id"),
    @NamedQuery(name = "SubjectTag.findByTagName", query = "SELECT s FROM SubjectTag s WHERE s.tagName = :tagName")})
public class SubjectTag extends AbstractEntity implements Serializable {
   
    private String tagName;

    @ManyToMany
    private List<Question> questions;
    
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
        if (!(object instanceof SubjectTag)) {
            return false;
        }
        SubjectTag other = (SubjectTag) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTagName();
    }
    
}
