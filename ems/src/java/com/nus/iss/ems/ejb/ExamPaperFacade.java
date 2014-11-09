package com.nus.iss.ems.ejb;

import com.nus.iss.ems.common.Constants;
import static com.nus.iss.ems.entities.Admin_.password;
import com.nus.iss.ems.entities.ExamPaper;
import com.nus.iss.ems.entities.ExamSection;
import com.nus.iss.ems.entities.Question;
import com.nus.iss.ems.entities.QuestionOption;
import com.nus.iss.ems.entities.Student;
import com.nus.iss.ems.temp.AbstractFacade;
import java.sql.Date;
import java.util.ArrayList;
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
public class ExamPaperFacade extends AbstractFacade<ExamPaper> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamPaperFacade() {
        super(ExamPaper.class);
    }

    public List<ExamPaper> getCurrentPaper() {
        TypedQuery<ExamPaper> ExamQuery = em.createNamedQuery("ExamPaper.getProgressedPaper", ExamPaper.class);

        List<ExamPaper> examPapers = ExamQuery.getResultList();
        if (examPapers.size() > 0) {
            return examPapers;
        } else {
            return null;
        }
    }

    public ExamPaper createExamPaper(ExamPaper examPaper) {
       
        em.persist(examPaper);
       // examPaper= em.find(ExamPaper.class, examPaper.getId());

        for (ExamSection es : examPaper.getSections()) {
            es.setExamPaper(examPaper);
            em.persist(es);
        }
        
        
        return examPaper;

    }

    public ExamPaper retrieveSectionsAndQuestions(ExamPaper current) {
       current= em.find(ExamPaper.class, current.getId());
       List<ExamSection> sections=current.getSections();
       for (ExamSection es : sections) {
            es.getQuestions();
        }
       return current;
       
    }
    
    public boolean deleteExamPaper(ExamPaper ep)
    {
        ep=em.find(ExamPaper.class, ep.getId());
        em.remove(ep);
        return true;
    }
    
    public ExamPaper modifyExamPaper(ExamPaper ep) {
        
       em.merge(ep);
      //  em.persist(ep);
        for (ExamSection es : ep.getSections()) {
            es.setExamPaper(ep);
            em.merge(es);
        }
        ep=em.find(ExamPaper.class, ep.getId());
        
        return ep;
    }
}
