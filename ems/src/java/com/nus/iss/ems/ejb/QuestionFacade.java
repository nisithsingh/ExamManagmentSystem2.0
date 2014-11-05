/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;

import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.SubjectTag;
import com.nus.iss.ems.enums.QuestionType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Milan
 */
@Stateless
public class QuestionFacade {
 
    @PersistenceContext
    private EntityManager em;
    
    public String createQuestion(String lecturerId,Module module, List<SubjectTag> subjectTags, QuestionType questionType, String questionText, List<String> options)
    {
        
        
      return "";   
    }
}
