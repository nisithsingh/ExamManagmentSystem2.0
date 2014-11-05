/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.service;

import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.SubjectTag;
import com.nus.iss.ems.enums.QuestionType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Milan
 */
public class QuestionService {

    Map<String, String> errors = null;

    public Map<String, String> validateQuestion(Module module, List<SubjectTag> subjectTags, QuestionType questionType, String questionText, List<String> options,int mark) {
        errors = new HashMap<String, String>();
        
        if (mark == 0 ) {
            errors.put("markInputText", "Mark should be greater than 0");
        }
        
        if (module == null ) {
            errors.put("moduleDropDown", "Module Code is Required");
        }

        if (subjectTags == null || subjectTags.size() == 0) {
            errors.put("subjectTagDropDown", "Subject Tag is Required");
        }

        if (questionType == null) {

            errors.put("questionTypeDropDown", "Question Type is Required");
        }
        
        if(questionText==null || questionText.equals(""))
        {
            errors.put("questionText", "Question Text is Required");
        }
        
        if(options==null || options.size()==0)
        {
            if(questionType!=null && questionType!=questionType.ESSAY)
            errors.put("questionOptionDataTable", "Question Option is Required");
        }
        return errors;
    }
}
