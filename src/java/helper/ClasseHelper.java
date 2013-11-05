package helper;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import models.Classe;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class ClasseHelper implements Serializable {

    
     /**
     * Get all classes from the database that belongs to a specified diagram. 
     * @param diagramaId Id of the diagram that the classes belong to
     * @return List list of class models
     */
    public static List getClassesByDiagramaId(Long diagramaId) {
        return getClassesByDiagramaId(diagramaId, "");
    }
    
    /**
     * Get all classes from the database that belongs to a specified diagram. 
     * It is possible to use restrictions
     * like Where and Order on the options parameter
     * @param options HBL query restricions like order and limit
     * @return List list of class models
     */
    public static List getClassesByDiagramaId(Long diagramaId, String options) 
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Classe> classeList = null;
        
        try {
            Transaction tx = session.beginTransaction();
            String query = "from Classe where diagrama_id = :diaId";
            if (!options.equals("")) {
                 query = query + " " + options;
            }
            Query q = session.createQuery(query);
            q.setParameter("diaId", diagramaId);
            classeList = (List<Classe>) q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return classeList;
    }
    
    /**
     * Get an instance of a classes from the Database by it's Id
     * @param id Id of the class to retrieve
     */
    public static Classe getClasseById(Long id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Classe result = null;
        try {
            Transaction tx = session.beginTransaction();
            result = (Classe)session.get(Classe.class, id);
            tx.commit();
            //Query query = session.createQuery("from Classe where id = :classId");
            //query.setParameter("classId", id);
            //result = (Classe)query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return result;
    }
    
    /**
     * Delete the provided Classe element
     * @param Classe classe
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteClasse(Classe classe)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean result = false;
        
        try {
            Transaction tx = session.beginTransaction();
            //session.delete(classe);
            String hql = "delete from Classe where id= :classId";
            session.createQuery(hql).setParameter("classId", classe.getId()).executeUpdate();
            tx.commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return result;
    }
    
    public static Classe save(Classe classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(classe);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return classe;
    }
    
    public static Classe update(Classe classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.update(classe);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return classe;
    }
}
