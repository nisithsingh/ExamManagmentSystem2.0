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
    private ExamPaper detailPaper;
    private int numberOfQuestion;
    private List<PieChartModel> pieModel1;
    private List<InvigilatingView> invigateSet  = new ArrayList<InvigilatingView>();
    private InvigilatingView inv;
    public List<InvigilatingView> getInvigateSet() {
        return invigateSet;
    }

    public void setInvigateSet(List<InvigilatingView> invigateSet) {
        this.invigateSet = invigateSet;
    }
    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public ExamPaper getDetailPaper() {
        return detailPaper;
    }

    public void setDetailPaper(ExamPaper detailPaper) {
        this.detailPaper = detailPaper;
    }
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<ExamPaper> getExamPapers() {
        return examPapers;
    }

    public void setExamPapers(List<ExamPaper> examPapers) {
        this.examPapers = examPapers;
    }

    public void loadAllCurrentPapers() {
        invigateSet.clear();
        examPapers = epf.getCurrentPaper();
        for (ExamPaper e : examPapers) {
            int stuN = e.getModule().getStudents().size();
            int sessionN = e.getSections().size();
            PieChartModel pieModel1 = new PieChartModel();
            createPieModel1(pieModel1,stuN,sessionN);
            InvigilatingView inV = new InvigilatingView();
            inV.setModule(e.getModule());
            inV.setPaper(e);
            inV.setPie(pieModel1);
            inV.setStudents(e.getModule().getStudents());
            invigateSet.add(inV);
        }
        index = 1;
        System.out.println("loadedPages");
    }

    private void createPieModel1(PieChartModel pieModel1,int numbserStudent,int numberOfSession) {
        int nonSigned = numbserStudent - numberOfSession;
        pieModel1.set("signed "+numberOfSession, numberOfSession);
        pieModel1.set(" without "+nonSigned, nonSigned);
        pieModel1.setLegendPosition("w");
    }


    public InvigilatingView getInv() {
        return inv;
    }

    public void setInv(InvigilatingView inv) {
        this.inv = inv;
    }
    public String togoDetail() {
        numberOfQuestion = 0;
        inv = invigateSet.get(index);
        detailPaper = inv.getPaper();
        List<ExamSection> secs = detailPaper.getSections();
        for (ExamSection sec : secs) {
            numberOfQuestion = numberOfQuestion + sec.getQuestions().size();
        }
        System.out.println("togoDetail");
        return "details";
    }
}
