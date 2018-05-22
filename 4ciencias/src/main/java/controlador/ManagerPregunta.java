/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Categoria;
import modelo.HibernateUtil;
import modelo.Pregunta;
import modelo.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author palmerin
 */
public class ManagerPregunta {
       
    /**
     * Método para obtener las preguntas que están en la base de datos.
     * Obtiene todas las preguntas que están hasta ese momento.
     * @return lista de preguntas.
     */
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
    
    /**
     * Obtiene todas las preguntas que tengan el id dado.
     * Dado que el id es único para cada pregunta podemos asegurar
     * que la lista devuelta tendrá un elemento (o será vacia si no hay 
     * una pregunta con tal id).
     * @param id
     * @return lista de preguntas con el id dado.
     */
    public List<Pregunta> getPreguntas(Serializable id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null; 
        List<Pregunta> resultado = new LinkedList();        
        try {
            tr = sesion.beginTransaction();
            Pregunta pregunta = (Pregunta) sesion.get((new Pregunta()).getClass(), id);            
            tr.commit();
            resultado.add(pregunta);
        } catch(HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        return resultado;
    }
    
    /**
     * Obtiene las preguntas que tienen como contenido la cadena dada.   
     * @param contenido
     * @return lista de preguntas con tal contenido.
     */
    public List<Pregunta> getPreguntas(String contenido) {
        String cl = contenido.toLowerCase();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        List<Pregunta> resultado = new LinkedList<>();    
        String[] palabras = contenido.split("\\s+");
        if (palabras.length == 0)
            return resultado;
        try {
            tr = sesion.beginTransaction();
            Criteria cr = sesion.createCriteria(Pregunta.class);
            for (String s : palabras)
                cr.add(Restrictions.or(Restrictions.like("titulo", s, MatchMode.ANYWHERE).ignoreCase(),
                                       Restrictions.like("contenido", s, MatchMode.ANYWHERE).ignoreCase()));
            resultado = cr.list();
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
    
    /**
     * Método para agregar una pregunta a la base de datos.
     * Se encarga de conectarse a la base de datos y de crear una instancia 
     * Pregunta con los parámetros recibidos (sin id pues lo asigna la BD).     
     * @param categoria
     * @param usuario
     * @param titulo
     * @param contenido
     * @param fecha
     * @return id asignado de la pregunta que se insertó.
     */
    public Serializable addPregunta(Categoria categoria, Usuario usuario, String titulo,
                                String contenido, Date fecha) {        
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null; 
        Serializable serial = null;
        try {
            tr = sesion.beginTransaction();            
            Pregunta pregunta = new Pregunta(categoria, usuario, titulo, contenido, fecha);
            serial = sesion.save(pregunta);            
            tr.commit();
        }
        catch(HibernateException he) {
            if (tr != null)
                tr.rollback();
        }
        finally {
            sesion.close();
        }
        return serial;
    }        
    
    /**
     * Método que realiza el query para eliminar una pregunta con un id dado.
     * Si encuentra uan pregunta con tal id, entonces eliminará tal pregunta y
     * devolverá True.
     * En caso de no encontrar ninguna pregunta no hará nada y devolverá True.
     * @param id
     * @return booleano indicando si se eliminó la pregunta.
     *          Si devuvelve False será porque ocurrió un problema en la BD.
     */
    public boolean eliminarPregunta(int id) {
        
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        List<Pregunta> resultado = getPreguntas(id);
        boolean exito = false;
        try {
            tr = sesion.beginTransaction();            
            for (Pregunta pregunta : resultado)
                sesion.delete(pregunta);
            tr.commit();
            exito = true;
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
       
    /**
     * Obtiene la instancia Usuario dado un id de Usuario.     
     * @param userId
     * @return Usuario correspondiente al id.
     */
    public Usuario getUsuario(int userId) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();        
        Object o = sesion.get(Usuario.class, userId);
       
        return (Usuario) o;
    }
    
}

