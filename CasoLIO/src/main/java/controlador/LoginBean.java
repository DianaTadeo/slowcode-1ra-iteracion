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
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
        this.password = password;
    }
    
    public String resultadoLogin() {
        ManagerU usuario = new ManagerU();
        if (usuario.exists(nombre, password)) {
            HttpSession sesion = UtilidadHTTP.obtenSesion();
            sesion.setAttribute("username", nombre);
            return "/restringido/principal";
        }
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                 "Usuario/Contraseña invalid@(s)",
                                 "Intenta de Nuevo!"));
        return "login";
    }
    
    public void resultadoLogout() throws IOException{
        HttpSession sesion = UtilidadHTTP.obtenSesion();
        sesion.removeAttribute("username");
        sesion.invalidate();
        //String red = UtilidadHTTP.obtenSolicitud().getContextPath() + 
          //           "/faces/login.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().
                redirect(UtilidadHTTP.obtenSolicitud().getContextPath() + 
                         "/login.xhtml");
    }
    
    public String insertaU() {
        ManagerU usuario = new ManagerU();
        if (usuario.addUsuario(nombre, password, email)) {
            HttpSession sesion = UtilidadHTTP.obtenSesion();
            sesion.setAttribute("username", nombre);
            return "/restringido/principal";
        }
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                 "No inserte usuario",
                                 "alv"));
        return "../login";
    }
}
