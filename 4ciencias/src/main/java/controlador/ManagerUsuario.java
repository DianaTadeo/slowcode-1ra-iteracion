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
                    new Date(System.currentTimeMillis()), false);
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
            String query = "from Usuario U where U.email = '" + email + "'";
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
    
    public boolean exists(String nombre, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.id FROM Usuario U where U.nombre = '" + nombre + 
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
        return (resultado != null && !resultado.isEmpty());
    }

    public boolean nombreExiste(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.id FROM Usuario U where U.nombre = '" + nombre + "'";
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
        return (resultado != null && !resultado.isEmpty());
    }

    public String editarNombre(String uid, String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "UPDATE Usuario U SET U.nombre = '"+ nombre +"' WHERE U.id = "+ uid ; 
        int resultado = 0;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).executeUpdate();
            tr.commit();
        }
        catch(Exception e) {
            if (tr != null) 
                tr.rollback();
             return e.toString();
        }
        finally {
            sesion.close();
        }
        return "SUCCESS";
    }
    
    public String editarPassword(String uid, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "UPDATE Usuario U SET U.password = '"+ password +"' WHERE U.id = "+ uid ;
        int resultado = 0;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).executeUpdate();
            tr.commit();
        }
        catch(Exception e) {
            if (tr != null) 
                tr.rollback();
            return e.toString();
        }
        finally {
            sesion.close();
        }
        return "SUCCESS";
    }
    
    public String editarEmail(String uid, String email) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "UPDATE Usuario U SET U.email = '"+ email +"' WHERE U.id = "+ uid;
        int resultado = 0;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).executeUpdate();
            tr.commit();
        }
        catch(Exception e) {
            if (tr != null) 
                tr.rollback();
            return e.toString();
        }
        finally {
            sesion.close();
        }
        return "SUCCESS";
    }
    
    public String getUserId(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.id FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        }
        catch(Exception e) {
            if (tr != null) 
                tr.rollback();
            return e.toString();
        }
        finally {
            sesion.close();
        }
        return (resultado.get(0).toString());
    }
    
    public int getUserPwdLength(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.password FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        }
        catch(Exception e) {
            if (tr != null) 
                tr.rollback();
            return -1;
        }
        finally {
            sesion.close();
        }
        return resultado.get(0).toString().length();
    }
    
    public String getUserEmail(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.email FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        }
        catch(Exception e) {
            if (tr != null) 
                tr.rollback();
            return e.toString();
        }
        finally {
            sesion.close();
        }
        return resultado.get(0).toString();
    }
}
