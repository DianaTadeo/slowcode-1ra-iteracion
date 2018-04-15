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
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;

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
    
    /* Variables para manejar eventos en la interfaz gráfica. */
    private String nombreCategoria; /* Guarda el nombre de la categoria seleccionada. */
    private List preguntas;
    
    /**
     * Creates a new instance of PreguntaBean
     */
    public PreguntaBean() {
    }                
       
    
    /** Método que verifica los campos de una pregunta.
    * @return true si es válida, false si no lo es.
    */
    public boolean verificarPregunta() {
        /**if (this.categoria == null)
            return false;
        if (this.usuario == null)
            return false;
        if (this.titulo == null || this.titulo.isEmpty())
            return false;
        if (this.fecha == null)
            return false;
        */
        return true;
    }
    
    public void eliminarPregunta() throws IOException {
        ManagerPregunta manager = new ManagerPregunta();
        HttpServletRequest req = (HttpServletRequest)FacesContext
                                                .getCurrentInstance()
                                                .getExternalContext()
                                                .getRequest();
        String id = req.getParameter("id_pregunta");  
        boolean eliminado = manager.eliminarPregunta(Integer.parseInt(id));
        if (eliminado) 
            FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/faces/ListadoPreguntas.xhtml");
        else return; //Aqui hace tra cosa.
    }
    
    public void insertarPregunta() throws IOException {        
        ManagerPregunta manager = new ManagerPregunta();
        if (verificarPregunta()) {
            Date fecha = new Date();
            Serializable serial = manager.addPregunta(categoria, null, titulo, contenido, fecha);  
            int id = manager.getPreguntaId(serial);
            FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/faces/Pregunta.xhtml?id_pregunta=" + serial);            
        }
        System.out.println("No se grego :(");        
    }
    
    /* Getters y Setters. */    
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
    
    public List<Pregunta> getPreguntas() {  
        HttpServletRequest req = (HttpServletRequest)FacesContext
                                                .getCurrentInstance()
                                                .getExternalContext()
                                                .getRequest();
        String id = req.getParameter("id_pregunta");        
        ManagerPregunta manager = new ManagerPregunta();
        if (id == null || id.isEmpty())
            preguntas = manager.getPreguntas();
        else 
            preguntas = manager.getPreguntas(Integer.parseInt(id));       
        return preguntas;
    }       
}
