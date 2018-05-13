/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Categoria;
import modelo.HibernateUtil;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author palmerin
 */
public class ManagerCategoria {
    
    public Categoria getCategoria(int id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "FROM Categoria C WHERE C.id = " + id;
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (HibernateException he) {
            if (tr != null)
                tr.rollback();
        } finally {
           sesion.close();
        }
       
        if (resultado != null)
            return (Categoria) resultado.get(0);
        
        return null;
    }
    public Categoria getCategoria(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "FROM Categoria C WHERE C.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (HibernateException he) {
            if (tr != null)
                tr.rollback();
        } finally {
           sesion.close();
        }
       
        if (resultado != null) {
            System.out.println("No es null el resultado.");
            return (Categoria) resultado.get(0);
        }
            
        System.out.println("Si es nulo");
        return null;
    }
    
    
    public List<Categoria> getCategorias() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;     
        String hql = "FROM Categoria";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        }        
        catch (HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        
        return resultado;
    }
    
    public boolean addCategoria(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        boolean exito = false;
        try {
            tr = sesion.beginTransaction();
            Categoria categoria = new Categoria(nombre);
            sesion.save(categoria);
            exito = true;
            tr.commit();
        }
        catch (HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        
        return exito;
    }
    
    public boolean exists(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select C.nombre FROM Categoria where C.nombre = '" 
                        + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        }
        catch (HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        return resultado != null && !resultado.isEmpty();
    }
}
