package helper;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import models.Diagrama;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class DiagramaHelper implements Serializable
{
    
    /**
     * Get all diagrams from the database
     * without any restriction
     * @return List all diagrams on the database
     */
    public static List getDiagramas() 
    {
        return getDiagramas("");
    }
    
    /**
     * Get all diagrams from the database. It is possible to use restrictions
     * like Where and Order on the options parameter
     * @param options HBL query restricions like where and order
     * @return List list of diagram models
     */
    public static List getDiagramas(String options) 
    {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        List<Diagrama> diagramaList = null;
        
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            String query = "from Diagrama";
            if (!options.equals("")) {
                 query = query + " " + options;
            }
            Query q = session.createQuery(query);
            diagramaList = (List<Diagrama>) q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return diagramaList;
    }
    
    /**
     * Retrieve a specific diagram from the database
     * @param diagramId The id of the diagram to retrieve
     * @return Diagrama hibernate object of the diagram
     */
    public static Diagrama getDiagramaById(Long diagramId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Diagrama diagram = null;

        try {
            Transaction tx = session.beginTransaction();
            diagram = (Diagrama)session.get(Diagrama.class, diagramId);
            tx.commit();
            //Query q = session.createQuery("from Diagrama as dia where dia.id = :diaId");
            //q.setParameter("diaId", diagramId);
            //diagram = (Diagrama) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }

        return diagram;
    }
    
    public static Diagrama save(Diagrama diagrama) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(diagrama);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return diagrama;
    }
    
    /**
     * Delete the provided Diagram element
     * @param Diagrama diagram
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteDiagram(Long id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean result = false;
        
        try {
            Transaction tx = session.beginTransaction();
            //session.delete(classe);
            String hql = "delete from Diagrama where id= :diaId";
            session.createQuery(hql).setParameter("diaId", id).executeUpdate();
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
    
    
}
