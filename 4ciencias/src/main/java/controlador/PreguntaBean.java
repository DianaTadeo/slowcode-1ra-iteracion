/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Categoria;
import modelo.Pregunta;
import modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author palmerin
 */
@ManagedBean(name = "preguntaBean")
@Dependent
public class PreguntaBean {
    
    /* Columnas de tabla de Pregunta en BD. */
    private Categoria categoria;
    private Usuario usuario;
    private String titulo;
    private String contenido;
    private Date fecha;
    private int id;
    
    /* Variables para manejar eventos en la interfaz gráfica. */
    private String nombreCategoria; /* Guarda el nombre de la categoria seleccionada. */
    private List preguntas;      
    private Pregunta pregunta;
   
    /**
     * Creates a new instance of PreguntaBean
     */
    public PreguntaBean() {
    }                              
    
    /* Getters y Setters. */    
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
    
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
    
    public void setNombreCategoria(String nombre) {       
        nombreCategoria = nombre;
        asignarCategoria(nombreCategoria);
        
    }
    
    public String getNombreCategoria() {        
        return nombreCategoria;
    }
    
    private void asignarCategoria(String nombre) {
        ManagerCategoria manager = new ManagerCategoria();
        this.categoria = manager.getCategoria(nombre);        
    }
    
    public void setPreguntas(List preguntas) {        
        this.preguntas = preguntas;
    }
    
    public List<Pregunta> getPreguntas() throws IOException {                          
       
        cargaPreguntas();               
        return preguntas;
    }
    
     /** Método que verifica los campos de una pregunta.
    * @return true si es válida, false si no lo es.
    */
    public boolean verificarPregunta() {
        if (this.categoria == null)
            return false;        
        if (this.titulo == null || this.titulo.isEmpty())
            return false;                          
        return true;
    }
    
    public void mostrarPregunta(int id) throws IOException {
        this.id = id;
        ManagerPregunta manager = new ManagerPregunta();
        this.pregunta = manager.getPreguntas(id).get(0);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/Pregunta.xhtml?id_pregunta=" + id);       
    }
    
    public void eliminarPregunta() throws IOException {  
        String param = obtenerParametroUrl("id_pregunta");        
        ManagerPregunta manager = new ManagerPregunta();
        
        boolean eliminado = manager.eliminarPregunta(id);
        if (eliminado) {
            muestraMensaje(FacesMessage.SEVERITY_INFO, "Se ha eliminado la pregunta.",
                           "");
        }
        else muestraMensaje(FacesMessage.SEVERITY_FATAL, "No se ha eliminado la pregunta.",
                           "Intenta mas tarde...");
    }
    
    public void insertarPregunta() throws IOException {        
        ManagerPregunta manager = new ManagerPregunta();
        if (verificarPregunta()) {           
            Date fechaPregunta = new Date();            
            Usuario sessionUser = manager.getUsuario(UtilidadHTTP.obtenerIdUsuario());
            Serializable serial = manager.addPregunta(categoria, sessionUser, titulo, contenido, fechaPregunta);              
            FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/Pregunta.xhtml?id_pregunta=" + serial);            
        }
        else muestraMensaje(FacesMessage.SEVERITY_FATAL, "No se ha publicado la pregunta.",
                           "Verifica los campos.");  
    }
    
    public String obtenerParametroUrl(String parametro) {
        HttpServletRequest req = (HttpServletRequest)FacesContext
                                                .getCurrentInstance()
                                                .getExternalContext()
                                                .getRequest();
        return req.getParameter(parametro);
    }
         
    
    public void cargaPreguntas() throws IOException {
        ManagerPregunta manager = new ManagerPregunta();
        String parametro = obtenerParametroUrl("id_pregunta");         
        if (parametro == null || parametro.isEmpty())
            preguntas = manager.getPreguntas();
        else 
            preguntas = manager.getPreguntas(Integer.parseInt(parametro));
    }
    
     public Pregunta getPregunta() {
        String parametro = obtenerParametroUrl("id_pregunta");                
        if (parametro != null && !parametro.isEmpty()) {
          this.id = Integer.parseInt(parametro);
          ManagerPregunta manager = new ManagerPregunta();
          pregunta = manager.getPreguntas(id).get(0);   
        }       
        return pregunta;
    }
     
    /**Nuevo: */
     public void mostrarAgregarPregunta() throws IOException {
         FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/AgregaPregunta.xhtml"); 
     }
     
     public void mostrarInicio() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/principal.xhtml"); 
     }
         
     public void muestraMensaje(FacesMessage.Severity severidad, String mensaje, 
                               String detalles) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severidad, mensaje, detalles));
    }
    
}
