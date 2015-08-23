/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.ejb;


import com.nus.iss.ems.controller.ExamPaperView;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import org.primefaces.context.RequestContext;
import javax.ejb.EJBException;


/**
 *
 * @author nisithsingh
 */
@Stateless
public class ExamTimerBean {
    
    @Resource TimerService timerService;
    RequestContext reqCtx;
    
    public void createExamPaperDuration(int duration,RequestContext reqCtx){
        
        this.reqCtx=reqCtx;
        timerService.createTimer(duration*1000, null);
        
    }
    
    @Timeout
    public void examTimeExpired(){// throws IOException{
        
        try{
        System.out.println("Time expires now");
        
        }
        catch(EJBException e){
            System.out.println(e.getMessage());
        }
    }
}
