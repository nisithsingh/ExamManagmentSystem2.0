package com.nus.iss.ems.ejb;

import com.nus.iss.ems.common.Constants;
import static com.nus.iss.ems.entities.Admin_.password;
import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author like
 */
@Stateless
public class ExamPaperFacade {

    @PersistenceContext
    private EntityManager em;

    public List<ExamPaper> getCurrentPaper() {
        TypedQuery<ExamPaper> ExamQuery = em.createNamedQuery("ExamPaper.getProgressedPaper", ExamPaper.class);

        List<ExamPaper> examPapers = ExamQuery.getResultList();
        if (examPapers.size() > 0) {
            return examPapers;
        } else {
            return null;
        }
    }
}