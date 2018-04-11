/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.HibernateUtil;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author emmanuel
 */
public class ManagerUsuario {

    public int agregaUsuario(String nombre, String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer usuario_id = -1;

        try {
            tx = session.beginTransaction();
            //Esto está mal
            Usuario usuario = new Usuario(nombre, email, password,
                    new Date(System.currentTimeMillis()), true);
            usuario_id = (Integer) session.save(usuario);
            tx.commit();
            
        } catch (HibernateException he) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
        
        return usuario_id;
    }
    
    public int existeUsuario(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List resultado = null;
        int id = 0;

        try {
            tx = session.beginTransaction();
            String query = "from Usuario U where "
                    + "U.email = '" + email + "'";
            resultado = session.createQuery(query).list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        if(resultado == null || resultado.isEmpty()) return -1;
        
        id = ((Usuario) resultado.get(0)).getId();
        return id;
    }
}
