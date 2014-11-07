/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.service;

import com.nus.iss.ems.ejb.ModuleFacade;
import com.nus.iss.ems.entities.Module;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author abhinav
 */
@ManagedBean(name = "moduleService", eager = true)
@ApplicationScoped
public class ModuleService {

    private List<Module> modules;
    @EJB
    ModuleFacade module;

    @PostConstruct
    public void init() {
        modules = module.retrieveAll();
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Module> getModules() {
        return modules;
    }
}
