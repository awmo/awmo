package helper;

import hibernate.HibernateUtil;
import java.io.Serializable;
import models.Parametro;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class ParametroHelper implements Serializable {
    
    public static Parametro save(Parametro parametro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(parametro);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return parametro;
    }

}
