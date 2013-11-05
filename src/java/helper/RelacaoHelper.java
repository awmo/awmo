package helper;

import hibernate.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import models.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class RelacaoHelper {
    
    /**
     * Get all RelacaoClasse from the database that belongs to a specified diagram. 
     * It is possible to use restrictions
     * like Where and Order on the options parameter
     * @param options HBL query restricions like order and limit
     * @return List list of RelacaoClasse models
     */
    public static List getRelacoesClasseByDiagramaId(Long diagramaId, String options) 
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<RelacaoClasse> relacaoList = new ArrayList<RelacaoClasse>();
        
        try {
            Transaction tx = session.beginTransaction();
            String query = "from RelacaoClasse";
            if (!options.equals("")) {
                 query = query + " " + options;
            }
            Query q = session.createQuery(query);
            relacaoList = (List<RelacaoClasse>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relacaoList;
    }
    
    public static RelacaoClasse save(RelacaoClasse relacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(relacao);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return relacao;
    }
    
    public static boolean deleteAllRelationsFromDiagram(Diagrama dia) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            String query = "delete from RelacaoClasse where source_classe_id in ( select c.id from Classe c where diagrama_id = :diaId )";
            Query q = session.createQuery(query);
            q.setParameter("diaId", dia.getId());
            q.executeUpdate();
            tx.commit();            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return false;
    }
}
