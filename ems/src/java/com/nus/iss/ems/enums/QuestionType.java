/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.enums;

/**
 *
 * @author a0120041j
 */
public enum QuestionType {
    MCQ_OneCorrect("MCQ One Correct"),MCQ_MultipleCorrect("MCQ Multiple Correct"),ESSAY("Essay"),MULTI_PART("Multi Part");
    
     private String label;

    private QuestionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    public String toString()
    {
        return label;
    }
}
