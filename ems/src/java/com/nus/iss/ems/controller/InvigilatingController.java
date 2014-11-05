package com.nus.iss.ems.controller;

import com.nus.iss.ems.ejb.ExamPaperFacade;
import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.entities.ExamSection;
import com.nus.iss.ems.entities.InvigilatingView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author like
 */
@SessionScoped
@Named("invigilatingController")
public class InvigilatingController implements Serializable {

    private static long uip = 1L;
    @EJB
    ExamPaperFacade epf;
    private List<ExamPaper> examPapers;
    private ExamPaper detailExamPaper;
    private int numberOfQuestion;
    private List<InvigilatingView> invigilationSet  = new ArrayList<InvigilatingView>();
    private InvigilatingView invigilatingView;
    public List<InvigilatingView> getInvigilationSet() {
        return invigilationSet;
    }

    public void setInvigilationSet(List<InvigilatingView> invigateSet) {
        this.invigilationSet = invigateSet;
    }
    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public ExamPaper getDetailExamPaper() {
        return detailExamPaper;
    }

    public void setDetailExamPaper(ExamPaper detailPaper) {
        this.detailExamPaper = detailPaper;
    }
    public List<ExamPaper> getExamPapers() {
        return examPapers;
    }

    public void setExamPapers(List<ExamPaper> examPapers) {
        this.examPapers = examPapers;
    }

    public void loadAllCurrentExamPapers() {
        invigilationSet.clear();
        int index = 0;
        examPapers = epf.getCurrentPaper();
        for (ExamPaper e : examPapers) {
            int stuN = e.getModule().getStudents().size();
            int sessionN = e.getSections().size();
            PieChartModel pieModel1 = new PieChartModel();
            createPieModel(pieModel1,stuN,sessionN);
            InvigilatingView inV = new InvigilatingView();
            inV.setModule(e.getModule());
            inV.setPaper(e);
            inV.setPie(pieModel1);
            inV.setStudents(e.getModule().getStudents());
            inV.setIndex(index);
            invigilationSet.add(inV);
            index = index +1;
        }
        System.out.println("loadedPages");
    }

    private void createPieModel(PieChartModel pieModel1,int numbserStudent,int numberOfSession) {
        int nonSigned = numbserStudent - numberOfSession;
        pieModel1.set("signed in: "+numberOfSession+" person", numberOfSession);
        pieModel1.set(" no signed in: "+nonSigned+ " person", nonSigned);
        pieModel1.setLegendPosition("w");
    }
    
    public InvigilatingView getInvigilatingView() {
        return invigilatingView;
    }

    public void setInvigilatingView(InvigilatingView inv) {
        this.invigilatingView = inv;
    }
    public String detail(int i) {
        invigilatingView = invigilationSet.get(i);
        detailExamPaper = invigilatingView.getPaper();
        List<ExamSection> secs = detailExamPaper.getSections();
        numberOfQuestion = 0;
        for (ExamSection sec : secs) {
            numberOfQuestion = numberOfQuestion + sec.getQuestions().size();
        }
        System.out.println("togoDetail");
        return "details";
    }
}
