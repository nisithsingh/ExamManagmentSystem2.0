package com.nus.iss.ems.entities;

import com.nus.iss.ems.controller.ExamPaperView;
import com.nus.iss.ems.controller.UserBean;
import com.nus.iss.ems.ejb.ExamSessionFacade;
import com.nus.iss.ems.entities.util.JsfUtil;
import com.nus.iss.ems.entities.util.PaginationHelper;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("examSessionController")
@SessionScoped
public class ExamSessionController implements Serializable {

    @Inject private UserBean ub;
    
    private ExamSession current;
    private DataModel items = null;
    @EJB
    private com.nus.iss.ems.ejb.ExamSessionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    @Inject ExamPaperView ePaperView;

    public ExamSessionController() {
    }

    public ExamSession getSelected() {
        if (current == null) {
            current = new ExamSession();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ExamSessionFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareExam(ExamPaper ePaper){
        //current = (ExamSession) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        //System.out.println(ePaper.getDuration());
        ePaperView.setePaper(ePaper);
       
        //System.out.println("Big Daddy:"+ePaper.getSections().get(0).getSectionName());
        ePaperView.init();
        createExamSession(ePaper);
        return "ExamStarted?faces-redirect=true";
        
    }
    public String prepareView() {
        current = (ExamSession) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View?faces-redirect=true";
    }

    public String prepareCreate() {
        current = new ExamSession();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamSessionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void createExamSession(ExamPaper epaper){
        System.out.println(ub.getStudent().getStudentName());
        current= new ExamSession();
        ePaperView.setEsession(current); //Will be used later
        ExamSession esession= ejbFacade.createExamSession(ub.getStudent(), current,epaper);
        System.out.println("Session created");
    }
    
    public String prepareEdit() {
        current = (ExamSession) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamSessionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ExamSession) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ExamSessionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ExamSession getExamSession(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ExamSession.class)
    public static class ExamSessionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExamSessionController controller = (ExamSessionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "examSessionController");
            return controller.getExamSession(getKey(value));
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
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ExamSession) {
                ExamSession o = (ExamSession) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ExamSession.class.getName());
            }
        }

    }

}
