/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.HibernateUtil;
import modelo.Pregunta;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rrios
 */
public class ManagerP {
    
    public List<Pregunta> todas() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List <Pregunta> resultado = null;
        Transaction tr = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createCriteria(Pregunta.class).list();
            tr.commit();
        }
        catch(HibernateException he) {
            if (tr != null) 
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        return resultado;
    }
    
}
