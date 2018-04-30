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
 *
 * @author diana
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

    
    @ManagedProperty("#{managerRespuesta}")
    private ManagerRespuesta myConect;
 
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
    
      public void generaRespuesta() {
          Respuesta e= new Respuesta();
          e.setContenido(contenido);
          respuestas.add(e);
          
      }
   
    
    public int getIdPregunta(){
        return idPregunta;
    }
    
    public void setIdPregunta(int id){
        this.idPregunta= id;
    }
      
      
    public Pregunta getPregunta() {
        return this.pregunta;
    }
    
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getContenido() {
        return this.contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List getRespuestas() {
        respuestas= myConect.misRespuestas(idPregunta);
        return this.respuestas;
    }
    
    public void setRespuestas(List respuestas) {
        this.respuestas = respuestas;
    }    
    
    public ManagerRespuesta getMyConect(){
        return this.myConect;
    }
    public void setMyConect(ManagerRespuesta newManager){
        this.myConect= newManager;
    }   
    
}