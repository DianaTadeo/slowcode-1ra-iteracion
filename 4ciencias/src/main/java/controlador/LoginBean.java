/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.HibernateUtil;
import modelo.Usuario;
import org.hibernate.internal.SessionImpl;
import org.apache.commons.codec.digest.DigestUtils;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }
    
    public void muestraMensaje(FacesMessage.Severity severidad, String mensaje, 
                               String detalles) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severidad, mensaje, detalles));
    }
    
    public void resultadoLogin() throws IOException {
        if (!this.hayConexion()) {
            muestraMensaje(FacesMessage.SEVERITY_FATAL, "No hay Conexion!!!",
                           "Intenta mas tarde...");
            return;                                
        }
        ManagerU MU = new ManagerU();
        Usuario usuario = MU.dameUsuario(email, password);
        if (usuario != null) {
            HttpSession sesion = UtilidadHTTP.obtenSesion();
            sesion.setAttribute("email", usuario.getEmail());
            sesion.setAttribute("username", usuario.getNombre());
            sesion.setAttribute("userid", usuario.getId());
            FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/restringido/principal.xhtml");
        }
        muestraMensaje(FacesMessage.SEVERITY_WARN, 
                       "Usuario/Contraseña invalid@(s)", "Intenta de Nuevo!");
    }
    
    public void resultadoLogout() throws IOException {
        HttpSession sesion = UtilidadHTTP.obtenSesion();
        sesion.removeAttribute("username");
        sesion.removeAttribute("email");
        sesion.removeAttribute("userid");
        sesion.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/login.xhtml");
    }  
    
    public boolean hayConexion() {
        try {
            return ((SessionImpl) HibernateUtil.getSessionFactory().openSession()).
                    connection().isValid(0);
        } 
        catch (SQLException ex) {
            return false;
        }
    }
}
