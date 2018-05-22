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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author palmerin
 */
@ManagedBean(name = "preguntaBean")
@SessionScoped
public class PreguntaBean {
    
    /* Columnas de tabla de Pregunta en BD. */
    private Categoria categoria;
    private Usuario usuario;
    private String titulo;
    private String contenido;
    private Date fecha;
    private int id;
    
    /* Variables para manejar eventos en la interfaz gráfica. */
    
    /* Guarda el nombre de la categoria seleccionada. */
    private String nombreCategoria; 
    
    /*Almacena las preguntas que se han publicado. */
    private List preguntas;      
    
    /*Instancia de una pregunta.*/
    private Pregunta pregunta;
    
    /* Mensaje de error cuando ocurre un problema en el CRUD.*/
    private String mensajeError;
   
    /**
     * Creates a new instance of PreguntaBean
     */
    public PreguntaBean() {
    }                              
    
    /**
     * Obtiene la categoría de la pregunta.
     * @return Categoria
     */   
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    /**
     * Asigna la categoría a la pregunta.
     * @param categoria 
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el id de la pregunta.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el id a la pregunta.
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Obtiene el Usuario de la pregunta.
     * @return Usuario
     */
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    /**
     * Asigna el Usuario a la pregunta.
     * @param usuario 
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Obtiene el título de la pregunta.
     * @return titulo
     */
    public String getTitulo() {
        return this.titulo;
    }
    
    /**
     * Asigna el título de la pregunta.
     * @param titulo 
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Obtiene el contenido de la pregunta.
     * @return contenido
     */
    public String getContenido() {
        return this.contenido;
    }
    
    /**
     * Asigna el contenido de la pregunta.
     * @param contenido 
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    /**
     * Obtiene la fecha de la pregunta.
     * @return fecha
     */
    public Date getFecha() {
        return this.fecha;
    }
    
    /**
     * Asigna la fecha de la pregunta.
     * @param fecha 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }  
    
    /**
     * Asigna una Pregunta a la variable pregunta.
     * @param pregunta 
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
    
    /**
     * Asigna el nombre de la categoria en la variable nombreCategoira
     * @param nombre 
     */
    public void setNombreCategoria(String nombre) {       
        nombreCategoria = nombre;
        asignarCategoria(nombreCategoria);
        
    }
    
    /**
     * Obtiene el nombre de la categoría.
     * @return nombre de la categoría.
     */
    public String getNombreCategoria() {        
        return nombreCategoria;
    }
    
    /**
     * Asigna la categoría dado el nombre de tal categoría.
     * @param nombre 
     */
    private void asignarCategoria(String nombre) {
        ManagerCategoria manager = new ManagerCategoria();
        this.categoria = manager.getCategoria(nombre);        
    }
    
    /**
     * Asigna las preguntas.
     * @param preguntas 
     */
    public void setPreguntas(List preguntas) {        
        this.preguntas = preguntas;
    }
    
    /**
     * Obtiene la lista de las preguntas.
     * @return lista de preguntas.
     * @throws IOException 
     */
    public List<Pregunta> getPreguntas() throws IOException {                                 
        cargaPreguntas();               
        return preguntas;
    }
    
    /**
     * Asigna un mensaje de error.
     * @param mensajeError 
     */
    public void setMensajeError(String mensajeError){
        this.mensajeError = mensajeError;
    }
    
    /**
     * Obtiene el mensaje de error.
     * @return mensaje de error.
     */
    public String getMensajeError(){
        return mensajeError;
    }
    
    /** Método que verifica los campos de una pregunta.
     * Es válida cuando siempre tiene título y categoría.
     * En otro caso es inválida.
     * @return true si es válida, false si no lo es.
     */
    public boolean verificarPregunta() {  
        if (categoria == null)
            return false;
        if (this.titulo == null || this.titulo.isEmpty())
        {         
            mensajeError = "Es necesario poner un título.\n"
                            + "No se ha insertado la pregunta.";
            return false;                          
        }            
        return true;
    }
    
    /**
     * Método que muestra la pregunta correspondiente a un id dado.
     * Se encarga de redirigir a la página de la pregunta correspondiente.
     * @param id
     * @throws IOException 
     */
    public void mostrarPregunta(int id) throws IOException {
        this.id = id;
        ManagerPregunta manager = new ManagerPregunta();
        this.pregunta = manager.getPreguntas(id).get(0);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/Pregunta.xhtml?id_pregunta=" + id);      
    }
    
    /**
     * Método que hace la petición de eliminar la pregunta que tiene
     * el id en el url de la página actual.
     * Si la elimina correctamente redirige a la página principal. 
     * En otro caso, manda a una página de error ecplicando lo que sucedió.
     * @throws IOException 
     */
    public void eliminarPregunta() throws IOException {  
        String param = obtenerParametroUrl("id_pregunta");        
        ManagerPregunta manager = new ManagerPregunta();
        
        boolean eliminado = manager.eliminarPregunta(id);
        if (eliminado) {
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/principal.xhtml");
        }
        else {
            mensajeError = "No se ha eliminado tu pregunta.\n"
                                + "Inténtalo más tarde.";
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/principal.xhtml");
        }
    }
    
    /**
     * Método que hace la petición a ManagerPregunta para insertar una pregunta.
     * Si se cumplen los campos solicitados, entonces se insertará en la BDD.
     * En otro caso, mandará a una página de error.
     * @throws IOException 
     */
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
        else {                 
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/mensajes/err/ErrorPreguntaIH.xhtml");  
        }
        limpiarAtributos();
    }
    
    /**
     * Limpia los atributos de la clase, esto con el fin 
     * de mantener la sesión limpia.
     */
    private void limpiarAtributos() {
        categoria = null;
        usuario   = null;
        fecha     = null;
        titulo    = "";
        contenido = "";
        id        = 0;
    }
    
    /**
     * Obtiene el parámetro con el nombre que se recibe de la url actual.
     * @param parametro
     * @return valor del parametro
     */
    public String obtenerParametroUrl(String parametro) {
        HttpServletRequest req = (HttpServletRequest)FacesContext
                                                .getCurrentInstance()
                                                .getExternalContext()
                                                .getRequest();
        return req.getParameter(parametro);
    }
         
    /**
     * Obtiene todas las preguntas de la base de datos y las guarda en 
     * una variable, de esta forma estarán disponibles cuando se pidan.
     * @throws IOException 
     */
    public void cargaPreguntas() throws IOException {
        ManagerPregunta manager = new ManagerPregunta();
        String parametro = obtenerParametroUrl("id_pregunta");         
        if (parametro == null || parametro.isEmpty())
            preguntas = manager.getPreguntas();
        else 
            preguntas = manager.getPreguntas(Integer.parseInt(parametro));
    }
    
    /**
     * Obtiene la pregunta 
     * @return pregunta pedida
     */
     public Pregunta getPregunta() {
        String parametro = obtenerParametroUrl("id_pregunta");                
        if (parametro != null && !parametro.isEmpty()) {
          this.id = Integer.parseInt(parametro);
          ManagerPregunta manager = new ManagerPregunta();
          pregunta = manager.getPreguntas(id).get(0);   
        }       
        return pregunta;
    }
    
     /**
      * Método para redirigir al formulario para agregar una pregunta.
      * @throws IOException 
      */
    public void mostrarAgregarPregunta() throws IOException {
         FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/AgregaPregunta.xhtml"); 
     }
    
    /**
     * Método para redirigir a la página principal de la página.
     * @throws IOException 
     */
    public void mostrarInicio() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/principal.xhtml"); 
     }       
}
