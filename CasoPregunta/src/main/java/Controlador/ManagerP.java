/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Categoria;
import Modelo.HibernateUtil;
import Modelo.Pregunta;
import Modelo.Usuario;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author palmerin
 */
public class ManagerP {
    
    public ManagerP() {
        
    }   
    
    public List getPreguntas() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "FROM Pregunta";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch(HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        return resultado;
    }
    
    public boolean addPregunta(Categoria categoria, Usuario usuario, String titulo,
                                String contenido, Date fecha) {        
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        boolean exito = false;
        try {
            tr = sesion.beginTransaction();
            Pregunta pregunta = new Pregunta(categoria, usuario, titulo, contenido, fecha);
            sesion.save(pregunta);
            exito = true;
            tr.commit();
        }
        catch(HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        return exito;
    }               
    
}

