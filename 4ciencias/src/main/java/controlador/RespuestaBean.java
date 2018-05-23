/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Pregunta;
import modelo.Usuario;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import modelo.Respuesta;
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
  
/**
 * Clase Bean para el objeto Respuesta y la
 * vista Pregunta.xhtml
 * @author diana
 * @version 1.0
 */
@ManagedBean(name = "respuestaBean")
@SessionScoped
@ViewScoped
public class RespuestaBean implements Serializable {
    private List<Respuesta> respuestas;
    private Respuesta respuesta;  
    private int idPregunta;
    private Pregunta pregunta;
    private Usuario usuario;
    private String contenido;
    private Date fecha;
    private ManagerRespuesta myConect = new ManagerRespuesta();
 
    /**
     * Permite eliminar una de las respuestas mostradas en la
     * página y a su vez de la base de datos
     * @param respuesta la respuesta que se pretende eliminar
     */
    public void eliminaRespuesta(Respuesta respuesta) {
        int id=respuesta.getId();
         respuestas.remove(respuesta);  
         boolean resultado= myConect.borraRespuesta(id);
           if(resultado==false)
                 FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                 "Error",
                                 "No se pudo eliminar el mensaje"));
            else 
              FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                 "Éxito",
                                 "La respuesta fue eliminada exitosamente"));
        }
    
    /**Método que permite agregar una respuesta aln presionar 
     * el boton enviar dentro de la página pregunta.xhtml
     * Muestra un mensaje en caso de éxito o error.
     */
    public void agregaRespuesta() {
        if(contenido==null || contenido.equals("")){
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                "Contenido vacío", "Vuelve a intentarlo"));
        }else{
           fecha= new Date();
            if (myConect.addRespuesta(contenido, idPregunta, fecha)){
                HttpSession sesion = UtilidadHTTP.obtenSesion();
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                 "¡Éxito!",
                                 "La respuesta fue agregada exitosamente")); 
            }else
                  FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                                 "No se pudo agregar la respuesta",
                                 "Ocurrió un error en la conexión al momento de agregar la respuesta")); 
        }
      }
    
      /**
       * Permite crear una respuesta y mostrarla
       */
      public void generaRespuesta() {
          Respuesta e= new Respuesta();
          e.setContenido(contenido);
          respuestas.add(e);
          
      }
   
    /**
     * Regresa el ID de la pregunta en la
     * que nos encontramos
     * @return idPregunta la pregunta
     */
    public int getIdPregunta(){
        return idPregunta;
    }
    
    /**
     * Cambia el Id de la pregunta en donde nos
     * encontramos.
     * @param id el nuevo id
     */
    public void setIdPregunta(int id){
        this.idPregunta= id;
    }
    
    /**
     * Regresa la pregunta que deseamos responder
     * @return this.pregunta
     */
    public Pregunta getPregunta() {
        return this.pregunta;
    }
    
    /**
     * Permite cambiar la pregunta que se
     * desea responder
     * @param pregunta es la nueva pregunta
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
  
    /**
     * Regresa el usuario que respondera
     * @return this.usuario
     */
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    /**
     * Cambia el usuario que esta respondiendo
     * la pregunta.
     * @param usuario es el nuevo usuraio
     */ 
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
  
    /**
     * Regresa el contenido de la respuesta
     * @return this.contenido
     */
    public String getContenido() {
        return this.contenido;
    }
    
    /**
     * Cambia el contenido de la respuesta
     * @param contenido es el nuevo contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
  
    /**
     * Regresa la fecha en que se escribio la respuesta
     * @return this.fecha
     */
    public Date getFecha() {
        return this.fecha;
    }
    
    /**
     * Cambia la fecha en que escribio la respuesta
     * @param fecha es la nueva fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la lista de las respuestas de la
     * pregunta quee se esta contestando.
     * @return this.respuestas 
     */
    public List getRespuestas() {
        respuestas= myConect.misRespuestas(idPregunta);
        return this.respuestas;
    }
    
    /**
     * Cambbia la lista de respuestas por una nueva
     * @param respuestas la nueva lista
     */
    public void setRespuestas(List respuestas) {
        this.respuestas = respuestas;
    }    
    
    /**
     * Regresa el objeto de la clase ManagerRespuesta
     * usado en esta clase para conectarla con
     * la base de datos.
     * @return this.myConect
     */
    public ManagerRespuesta getMyConect(){
        return this.myConect;
    }
  
    /**
     * Cambia el elemento de la clase ManagerRespuesta
     * por otro.
     * @param newManager el nuevo manejador myConect
     */
    public void setMyConect(ManagerRespuesta newManager){
        this.myConect= newManager;
    }   
}
