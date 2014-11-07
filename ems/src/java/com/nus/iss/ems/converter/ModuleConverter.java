/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ems.converter;

import com.nus.iss.ems.controller.QuestionController;
import com.nus.iss.ems.entities.Module;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Milan
 */
@FacesConverter("moduleConverter")
public class ModuleConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         if (value == null || value.length() == 0) {
                return null;
            }
            QuestionController controller = (QuestionController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "questionController");
            return controller.getModule(getKey(value));
    }
    
     java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

    
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
         if (object == null) {
                return null;
            }
            if (object instanceof Module) {
                Module o = (Module) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Module.class.getName()});
                return null;
            }
    }
    
}
