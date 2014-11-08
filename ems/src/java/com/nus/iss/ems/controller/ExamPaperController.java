package com.nus.iss.ems.controller;

import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.ejb.ExamPaperFacade;
import com.nus.iss.ems.service.ExamPaperService;
import com.nus.iss.ems.temp.util.JsfUtil;
import com.nus.iss.ems.temp.util.PaginationHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named
@ViewScoped
public class ExamPaperController implements Serializable {

    @Inject
    ExamPaperService examPaperService;
    
    private Date startDate;
    
    private ExamPaper current;
    private DataModel items = null;
    @EJB
    private ExamPaperFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ExamPaperController() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date toSQLDate(Date date)
    {
        return new java.sql.Date(date.getTime());
    }
    
    public ExamPaper getSelected() {
        if (current == null) {
            current = new ExamPaper();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ExamPaperFacade getFacade() {
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

    public String prepareView() {
        current = (ExamPaper) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ExamPaper();
        selectedItemIndex = -1;
        return "Create";
    }

    public void create() {
        try {
            current.setStartDate(toSQLDate(startDate));
        
            Map<String, String> errors = examPaperService.validateExamPaper(current);
            FacesContext context = FacesContext.getCurrentInstance();
            if (!errors.isEmpty()) {

                for (String key : errors.keySet()) {
                    FacesMessage error = new FacesMessage(errors.get(key));
                    context.addMessage(key, error);
                }
                return;
            }
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/examPaper").getString("ExamPaperCreated"));
            prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/examPaper").getString("PersistenceErrorOccured"));
            return;
        }
    }

    public String prepareEdit() {
        current = (ExamPaper) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/examPaper").getString("ExamPaperUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/examPaper").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ExamPaper) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/examPaper").getString("ExamPaperDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/examPaper").getString("PersistenceErrorOccured"));
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

    public ExamPaper getExamPaper(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ExamPaper.class)
    public static class ExamPaperControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExamPaperController controller = (ExamPaperController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "examPaperController");
            return controller.getExamPaper(getKey(value));
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
            if (object instanceof ExamPaper) {
                ExamPaper o = (ExamPaper) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ExamPaper.class.getName());
            }
        }

    }
    
    
}
