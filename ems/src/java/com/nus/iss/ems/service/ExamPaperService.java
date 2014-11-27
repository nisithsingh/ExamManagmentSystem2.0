/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.service;

import com.nus.iss.ems.entities.ExamPaper;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Milan
 */

@RequestScoped
@Named
public class ExamPaperService {

    public Map<String, String> validateExamPaper(ExamPaper examPaper) {
        Map<String, String> errors = null;
        errors = new HashMap<String, String>();
        if (examPaper.getName() == null || examPaper.getName().equals("")) {
            errors.put("examPaper", "Exam Paper cannot be empty");
        }
        if (examPaper.getDuration() == null || examPaper.getDuration() < 30) {
            errors.put("duration", "duration cannot be less than 30 mt");
        }
//        if (examPaper.getStartDate() == null || examPaper.getStartDate().before(new Date(System.currentTimeMillis()))) {
//            errors.put("startDate", "Start Date cannot be less or equal to today");
//        }
        if (examPaper.getModule() == null) {
            errors.put("module", "Module is required");
        }

        return errors;
    }

}
