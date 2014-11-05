
package com.nus.iss.ems.entities;

import java.util.List;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author like
 */

public class InvigilatingView {
    private List<Student> students;
    private PieChartModel pie;
    private ExamPaper paper;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public PieChartModel getPie() {
        return pie;
    }

    public void setPie(PieChartModel pie) {
        this.pie = pie;
    }

    public ExamPaper getPaper() {
        return paper;
    }

    public void setPaper(ExamPaper paper) {
        this.paper = paper;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    private Module module;
    
}
