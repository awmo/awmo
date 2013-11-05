package helper;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import models.Classe;
import models.Operacao;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fgrillo
 */
public class OperacaoHelper implements Serializable {
    
    public static Operacao save(Operacao operacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = session.beginTransaction();
            session.save(operacao);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return operacao;
    }
    
    public static boolean deleteAllOperationsFromClass(Classe classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Operacao> ops = classe.getOperacaos();
        Iterator it = ops.iterator(); 
        try {
            Transaction tx = session.beginTransaction();
            while(it.hasNext()) {
                Operacao o = (Operacao)it.next(); 
                session.delete(o);
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
