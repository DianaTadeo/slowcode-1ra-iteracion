/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.HibernateUtil;
import modelo.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rrios
 */
public class ManagerU {
     
    public Usuario dameUsuario(String email, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "FROM Usuario U where U.email = '" + email + 
                     "' AND U.password = '" + password + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        }
        catch(HibernateException he) {
            if (tr != null) 
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        if(resultado != null && !resultado.isEmpty()){
            return (Usuario) resultado.get(0);
        }
        else{
            return null;
        }
    }
}
