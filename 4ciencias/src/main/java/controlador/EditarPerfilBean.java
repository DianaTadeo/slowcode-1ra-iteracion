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
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jantoniolr
 */


@ManagedBean(name = "editarPerfilBean")
@SessionScoped
public class EditarPerfilBean implements Serializable {
    
    private String nombre;
    private String email;
    private String password;
    private String password2;

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
    
    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = DigestUtils.sha256Hex(password2);
    }
    

    public String validaNombre() {
        ManagerUsuario manager = new ManagerUsuario();
        if (manager.nombreExiste(this.nombre)) {
            return "/restringido/mensajes/err/nombre1";
        }
        String uid = manager.getUserId(UtilidadHTTP.obtenUsuario());
        return this.actualizaNombre(uid, this.nombre);
    }

    public String validaPassword() {
        if (!this.password.equals(this.password2)) {
            return "/restringido/mensajes/err/pwd1";
        } else if (this.password.length() < 4) {
            return "/restringido/mensajes/err/pwd2";
        }
        ManagerUsuario manager = new ManagerUsuario();
        String uid = manager.getUserId(UtilidadHTTP.obtenUsuario());
        return this.actualizaPassword(uid, this.password);
    }
 
    public String validaEmail() {
        if (!this.email.contains("@ciencias.unam.mx")) {
            return "/restringido/mensajes/err/email1";
        }
        ManagerUsuario manager = new ManagerUsuario();
        String uid = manager.getUserId(UtilidadHTTP.obtenUsuario());
        return this.actualizaEmail(uid, this.email);
    }

    public String actualizaNombre(String uid, String nuevo) {
        ManagerUsuario manager = new ManagerUsuario();
        String queryResult = manager.editarNombre(uid, nuevo);
        if ( queryResult == "SUCCESS") {
            HttpSession sesion = UtilidadHTTP.obtenSesion();
            sesion.setAttribute("username", nuevo);
            return "/restringido/mensajes/exito";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(queryResult));
                return queryResult;
        }
        //return "/restringido/mensajes/err/consulta";
    }

    public String actualizaPassword(String uid, String nuevo) {
        ManagerUsuario manager = new ManagerUsuario();
        String queryResult = manager.editarPassword(uid, nuevo);
        if (queryResult == "SUCCESS") {
            return "/restringido/mensajes/exito";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(queryResult));
        }
        return "/restringido/mensajes/err/consulta";
    }

    public String actualizaEmail(String uid, String nuevo) {
        ManagerUsuario manager = new ManagerUsuario();
        String queryResult = manager.editarEmail(uid, nuevo);
        if (queryResult == "SUCCESS") {
            return "/restringido/mensajes/exito";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(queryResult));
        }
        return "/restringido/mensajes/err/consulta";
    }
}
