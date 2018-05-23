package controlador;

import java.util.*;
import modelo.HibernateUtil;
import modelo.Usuario;
import modelo.Pregunta;
import modelo.Respuesta;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Clase que permite la interacción del objedo te la base de datos
 * Respuesta con el resto de las clases del controlador.
 *
 * @author diana
 * @version 1.0
 */
@ManagedBean(name ="managerR")
@ApplicationScoped
public class ManagerRespuesta {
    
    /**Se encarga de agregar una respuesta a la base de datos
     * @param r es una platilla de la repsuesta que será agregada
     * @param p es la pregunta a la que corresponde la repsuesta agregada
     * @return exito es el valor de éxito al ser agregada la repsuesta
     * true en caso de éxito y false en caso de fallo
     */
   public boolean addRespuesta(String contenido, int idPregunta, Date fecha) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        boolean exito = false;
        try {
            tr = sesion.beginTransaction();
            //String userID= util.getUserId();
            Usuario user= (Usuario)sesion.get(Usuario.class, 
                                              UtilidadHTTP.obtenerIdUsuario());
            Pregunta preg= (Pregunta)sesion.get(Pregunta.class,idPregunta);
            
            Respuesta respuesta = new Respuesta();
            respuesta.setContenido(contenido);
            respuesta.setFecha(fecha);
            respuesta.setPregunta(preg);
            respuesta.setUsuario(user);
            System.out.println("Respuesta:"+idPregunta);
            
            sesion.save(respuesta);
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
   
   /**Permite eliminar una respuesta de la base de datos a través de su id
    * @param id es el id de la respuesta que se desea eliminar de la base de datos
    * @return regreso es el resultado al ser eliminada la repsuesta
    * true en caso de éxito y false de lo contrario.
    */
  public boolean borraRespuesta(int id){
      boolean regreso;
      Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        try {
            tr = sesion.beginTransaction();
            Respuesta res= (Respuesta)sesion.get(Respuesta.class,id);
            sesion.delete(res);
            tr.commit();
            regreso=true;
        }
        catch(HibernateException he) {
            if (tr != null)
                tr.rollback();
            regreso=false;
            
        }
        finally {
            sesion.close();
        }
        return regreso;
      
      
  }
   
   /**
    * Devuelve una lista de respuestas que pertenecen a una pregunta
    * @param idPregunta la pregunta en la que aparecen todas las respuestas
    * @return respuestas es la lista de respuestas en la pregunta
    */
    public List<Respuesta> misRespuestas(int idPregunta){
         Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        List<Respuesta> respuestas = null;
        try {
            tr = sesion.beginTransaction();
            respuestas = sesion.createQuery("FROM Respuesta where id_pregunta="+ idPregunta).list();
            tr.commit();
        }catch(HibernateException he){
            if (tr != null) 
                tr.rollback();
        }
        finally{
            sesion.close();
        }
        return respuestas;
    }

    /**
    * Permite regresar una representacion en cadena del objeto
    * respuesta
    * @param r la respuesta que se planea pasar a cadena
    * @return la cadena de representacion
    */
    public String toString(Respuesta r){
        return r.getContenido()+" user: "+r.getUsuario().getNombre();
    }
}
