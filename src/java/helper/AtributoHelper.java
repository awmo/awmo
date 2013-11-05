package helper;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import models.Atributo;
import models.Classe;
import models.Tipo;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class AtributoHelper implements Serializable {
    
    public static Atributo save(Atributo atributo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(atributo);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return atributo;
    }
    
    public static boolean deleteAllAttributesFromClass(Classe classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Atributo> attrs = classe.getAtributos();
        Iterator it = attrs.iterator(); 
        try {
            Transaction tx = session.beginTransaction();
            while(it.hasNext()) {
                Atributo a = (Atributo)it.next(); 
                session.delete(a);
            }
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
