/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.HibernateUtil;
import modelo.Usuario;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author rrios
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String nombre;
    private String email;
    private String password;

    /**
     * Obtiene el nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna Nombre.
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asigna email.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtine password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Asigna Password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    /**
     * Muestra mensaje.
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

    /**
     * Regresa resultado login.
     *
     * @throws IOException
     */
    public void resultadoLogin() throws IOException {
        if (!this.hayConexion()) {
            muestraMensaje(FacesMessage.SEVERITY_FATAL, "No hay Conexion!!!",
                    "Intenta mas tarde...");
            return;
        }
        ManagerUsuario MU = new ManagerUsuario();
        Usuario usuario = MU.dameUsuario(email, password);
        if (usuario != null) {
            HttpSession sesion = UtilidadHTTP.obtenSesion();
            sesion.setAttribute("email", usuario.getEmail());
            sesion.setAttribute("username", usuario.getNombre());
            sesion.setAttribute("userid", usuario.getId());
            sesion.setAttribute("admin", usuario.isEsAdmin());
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                            + "/restringido/principal.xhtml");
        }
        muestraMensaje(FacesMessage.SEVERITY_WARN,
                "Usuario/Contraseña invalid@(s)", "Intenta de Nuevo!");
    }

    /**
     * Resultado logout.
     *
     * @throws IOException
     */
    public void resultadoLogout() throws IOException {
        HttpSession sesion = UtilidadHTTP.obtenSesion();
        sesion.removeAttribute("username");
        sesion.removeAttribute("email");
        sesion.removeAttribute("userid");
        sesion.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                        + "/login.xhtml");
    }

    /**
     * Nos dice si hay conexion.
     *
     * @return conexionValida
     */
    public boolean hayConexion() {
        try {
            return ((SessionImpl) HibernateUtil.getSessionFactory().openSession()).
                    connection().isValid(0);
        }
        catch (Exception ex) {
            return false;
        }
    }

    /**
     * Redirige.
     *
     * @param direccion
     * @throws IOException
     */
    public void redirige(String direccion) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath()
                        + direccion);
    }
}
