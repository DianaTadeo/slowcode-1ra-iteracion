/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.HibernateUtil;
import modelo.UsuarioSinValidar;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;

/**
 *
 * @author emmanuel
 */
public class ManageUsuarioSinValidar {
    
    public int agregaUSV(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer usv_id = null;
        
        try{
            tx = session.beginTransaction();
            UsuarioSinValidar usv = new UsuarioSinValidar();
            usv_id = (Integer) session.save(usv);
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null) tx.rollback();
        }
        finally{
            session.close();
        }
        
        return usv_id;
    }
}
