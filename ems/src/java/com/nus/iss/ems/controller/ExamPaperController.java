package com.nus.iss.ems.controller;

import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.ejb.ExamPaperFacade;
import com.nus.iss.ems.ejb.ModuleFacade;
import com.nus.iss.ems.ejb.QuestionFacade;
import com.nus.iss.ems.ejb.SubjectTagFacade;
import com.nus.iss.ems.entities.ExamSection;
import static com.nus.iss.ems.entities.Lecturer_.modules;
import com.nus.iss.ems.entities.Module;
import com.nus.iss.ems.entities.Question;
import static com.nus.iss.ems.entities.StudentAnswer_.question;
import com.nus.iss.ems.entities.SubjectTag;
import com.nus.iss.ems.enums.SectionType;
import com.nus.iss.ems.service.ExamPaperService;
import com.nus.iss.ems.temp.util.JsfUtil;
import com.nus.iss.ems.temp.util.PaginationHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
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

    @EJB
    ModuleFacade moduleFacade;

    @EJB
    QuestionFacade questionFacade;

    @Inject
    UserBean userBean;

    List<Module> modules;

    @EJB
    SubjectTagFacade subjectTagFacade;

    @EJB
    ExamPaperFacade examPaperFacade;

    private List<Question> questions = new ArrayList<Question>();

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @PostConstruct
    public void init() {
        resetExamPaper();
    }

    private ExamSection examSection = new ExamSection();

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public ExamSection getExamSection() {
        return examSection;
    }

    public void setExamSection(ExamSection examSection) {
        this.examSection = examSection;
    }

    private Date startDate;

    private ExamPaper current;
    private DataModel items = null;
    @EJB
    private ExamPaperFacade ejbFacade;
    private PaginationHelper pagination;

    private boolean isInCreateMode = true;

    private String dailogTitle = "Create Question";

    private int selectedItemIndex;

    public String getDailogTitle() {
        return dailogTitle;
    }

    public void setDailogTitle(String dailogTitle) {
        this.dailogTitle = dailogTitle;
    }

    public boolean isIsInCreateMode() {

        return isInCreateMode;
    }

    public void setIsInCreateMode(boolean isInCreateMode) {
        if (isInCreateMode) {
            setDailogTitle("Create Exam Paper");
        } else {
            setDailogTitle("Modify Exam Paper");
        }
        this.isInCreateMode = isInCreateMode;
    }

    public ExamPaperController() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date toSQLDate(Date date) {
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

//    public String prepareList() {
//        recreateModel();
//        return "List";
//    }
//    public String prepareView() {
//        current = (ExamPaper) getItems().getRowData();
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        return "View";
//    }
//    public void prepareCreate() {
//        current = new ExamPaper();
//        selectedItemIndex = -1;
//    }
    public void create() {
        try {

            if (isInCreateMode) {
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

                current = examPaperFacade.createExamPaper(current);
                recreateModel();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/examPaper").getString("ExamPaperCreated"));
                current = new ExamPaper();
            } else {
                Map<String, String> errors = examPaperService.validateExamPaper(current);
                FacesContext context = FacesContext.getCurrentInstance();
                if (!errors.isEmpty()) {

                    for (String key : errors.keySet()) {
                        FacesMessage error = new FacesMessage(errors.get(key));
                        context.addMessage(key, error);
                    }
                    return;
                }

                current = examPaperFacade.modifyExamPaper(current);
                recreateModel();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/examPaper").getString("ExamPaperCreated"));
                current = new ExamPaper();
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/examPaper").getString("PersistenceErrorOccured"));
            return;
        }
    }

    public void prepareEdit() {
        current = (ExamPaper) getItems().getRowData();
        current = examPaperFacade.retrieveSectionsAndQuestions(current);
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        setIsInCreateMode(false);

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

    public void destroy() {
        current = (ExamPaper) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();

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
            getFacade().deleteExamPaper(current);
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

    public void setExamPaperToCreate() {
        current = new ExamPaper();

        setIsInCreateMode(true);
    }

    public void addSection() {
        if (examSection.getSectionType() == SectionType.Automatic) {
            List<Question> questions = questionFacade.retrieveQuestions(current.getModule(), examSection.getQuestions(), examSection.getSubjectTags(), examSection.getTotalMarks());
            examSection.setQuestions(questions);
            

        } else {

        }
        getSelected().getSections().add(examSection);
        resetExamSection();
        questions = retrieveQuestions();
        for (ExamSection es : current.getSections()) {
            questions.removeAll(es.getQuestions());
        }

    }

    public void addQuestion(ExamSection examSection, List<Question> questionsSelected) {
        
        
        for (Question q : questionsSelected) {
            if( examSection.getQuestions().contains(q))
            questions.remove(q);
            else
            {
                 questions.remove(q);
                examSection.getQuestions().add(q);
            }
        }
        
        
    }

    public List<Question> retrieveQuestions() {

        if (current.getModule() == null) {
            resetExamPaper();
        }
        questions = questionFacade.retrieveQuestions(current.getModule(), examSection.getQuestions(), examSection.getSubjectTags(), examSection.getTotalMarks());

        for (ExamSection es : current.getSections()) {
            questions.removeAll(es.getQuestions());
        }
        return questions;
    }

    public SectionType[] getSectionTypes() {
        return SectionType.values();
    }

    public void removeQuestionFromSection(ExamSection examSection, Question question) {
        questions.add(question);
        examSection.getQuestions().remove(question);
    }

    public void resetExamPaper() {
        current = new ExamPaper();
        resetExamSection();
        current.getSections().add(examSection);
        questions = retrieveQuestions();
        for (ExamSection es : current.getSections()) {
            questions.removeAll(es.getQuestions());
        }

    }

    public void resetExamSection() {
        examSection = new ExamSection();
        retrieveModules();
        if (modules != null && modules.size() > 0) {
            // moduleSelected = modules.get(0);
            current.setModule(modules.get(0));

        }
        retrieveSubjectTags();
        //questions=retrieveQuestions();
    }

    public void retrieveModules() {
        String lecturerID = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();

        modules = moduleFacade.retireveAllModules(lecturerID);
    }

    public List<SubjectTag> retrieveSubjectTags() {
        List<SubjectTag> subjectTags = subjectTagFacade.retireveAllSubjectTags();
        if (subjectTags != null && subjectTags.size() > 0) {
            //subjectTagsSelected.add(subjectTags.get(0));
            examSection.getSubjectTags().add(subjectTags.get(0));
        }
        return subjectTags;
    }

}
