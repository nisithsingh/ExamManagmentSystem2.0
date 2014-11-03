/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.controller;

import com.nus.iss.ems.entities.Module;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Milan
 */
@RequestScoped
@Named
public class QuestionController {
    
    private List<Module> modules;
    
    private Module moduleSelected;

    public Module getModuleSelected() {
        return moduleSelected;
    }

    public void setModuleSelected(Module moduleSelected) {
        this.moduleSelected = moduleSelected;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    
    
    
}
