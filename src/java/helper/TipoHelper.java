package helper;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import models.Tipo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class TipoHelper implements Serializable {
    
    public static List getTiposByDiagramId(Long diagramaId, String options) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Tipo> tipoList = null;
        
        try {
            Transaction tx = session.beginTransaction();
            String query = "from Tipo where classe_id in ( select c.id from Classe c where diagrama_id = :diaId ) or kind = 1";
            if (!options.equals("")) {
                 query = query + " " + options;
            }
            Query q = session.createQuery(query);
            q.setParameter("diaId", diagramaId);
            tipoList = (List<Tipo>) q.list();
            tx.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return tipoList;
    }
    
    public static Tipo save(Tipo tipo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(tipo);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return tipo;
    }
    
}
