/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.Categoria;
import modelo.Pregunta;
import modelo.Usuario;

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
    private String nombreCategoria;
    /* Guarda el nombre de la categoria seleccionada. */
    private List preguntas;
    private Pregunta pregunta;
    private String mensajeError;

    /**
     * Creates a new instance of PreguntaBean
     */
    public PreguntaBean() {
    }

    /* Getters y Setters. */
    /**
     *
     * @return
     */
    public Categoria getCategoria() {
        return this.categoria;
    }

    /**
     *
     * @param categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Usuario getUsuario() {
        return this.usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     *
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return
     */
    public String getContenido() {
        return this.contenido;
    }

    /**
     *
     * @param contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return this.fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @param pregunta
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     *
     * @param nombre
     */
    public void setNombreCategoria(String nombre) {
        nombreCategoria = nombre;
        asignarCategoria(nombreCategoria);

    }

    /**
     *
     * @return
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    private void asignarCategoria(String nombre) {
        ManagerCategoria manager = new ManagerCategoria();
        this.categoria = manager.getCategoria(nombre);
    }

    /**
     *
     * @param preguntas
     */
    public void setPreguntas(List preguntas) {
        this.preguntas = preguntas;
    }

    /**
     *
     * @return @throws IOException
     */
    public List<Pregunta> getPreguntas() throws IOException {
        cargaPreguntas();
        return preguntas;
    }

    /**
     *
     * @param mensajeError
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     *
     * @return
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Método que verifica los campos de una pregunta.
     *
     * @return true si es válida, false si no lo es.
     */
    public boolean verificarPregunta() {
        if (categoria == null) {
            return false;
        }
        if (this.titulo == null || this.titulo.isEmpty()) {
            mensajeError = "Es necesario poner un título.\n"
                    + "No se ha insertado la pregunta.";
            return false;
        }
        return true;
    }

    /**
     *
     * @param id
     * @throws IOException
     */
    public void mostrarPregunta(int id) throws IOException {
        this.id = id;
        ManagerPregunta manager = new ManagerPregunta();
        this.pregunta = manager.getPreguntas(id).get(0);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                        + "/restringido/Pregunta.xhtml?id_pregunta=" + id);
    }

    /**
     *
     * @throws IOException
     */
    public void eliminarPregunta() throws IOException {
        String param = obtenerParametroUrl("id_pregunta");
        ManagerPregunta manager = new ManagerPregunta();

        boolean eliminado = manager.eliminarPregunta(id);
        if (eliminado) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                            + "/restringido/principal.xhtml");
        } else {
            mensajeError = "No se ha eliminado tu pregunta.\n"
                    + "Inténtalo más tarde.";
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                            + "/restringido/principal.xhtml");
        }
    }

    /**
     *
     * @throws IOException
     */
    public void insertarPregunta() throws IOException {
        ManagerPregunta manager = new ManagerPregunta();
        if (verificarPregunta()) {
            Date fechaPregunta = new Date();
            Usuario sessionUser = manager.getUsuario(UtilidadHTTP.obtenerIdUsuario());
            Serializable serial = manager.addPregunta(categoria, sessionUser, titulo, contenido, fechaPregunta);
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                            + "/restringido/Pregunta.xhtml?id_pregunta=" + serial);
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                            + "/restringido/mensajes/err/ErrorPreguntaIH.xhtml");
        }
        limpiarAtributos();
    }

    private void limpiarAtributos() {
        categoria = null;
        usuario = null;
        fecha = null;
        titulo = "";
        contenido = "";
        id = 0;
    }

    /**
     *
     * @param parametro
     * @return
     */
    public String obtenerParametroUrl(String parametro) {
        HttpServletRequest req = (HttpServletRequest) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
        return req.getParameter(parametro);
    }

    /**
     *
     * @throws IOException
     */
    public void cargaPreguntas() throws IOException {
        ManagerPregunta manager = new ManagerPregunta();
        String parametro = obtenerParametroUrl("id_pregunta");
        if (parametro == null || parametro.isEmpty()) {
            preguntas = manager.getPreguntas();
        } else {
            preguntas = manager.getPreguntas(Integer.parseInt(parametro));
        }
    }

    /**
     *
     * @return
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
     *
     * @throws IOException
     */
    public void mostrarAgregarPregunta() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                        + "/restringido/AgregaPregunta.xhtml");
    }

    /**
     *
     * @throws IOException
     */
    public void mostrarInicio() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                        + "/restringido/principal.xhtml");
    }

    /**
     *
     * @param severidad
     * @param mensaje
     * @param detalles
     */
    public void muestraMensaje(FacesMessage.Severity severidad, String mensaje,
            String detalles) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severidad, mensaje, detalles));
    }

}
