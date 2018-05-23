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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

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

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    /**
     *
     * @return
     */
    public String getPassword2() {
        return password2;
    }

    /**
     *
     * @param password2
     */
    public void setPassword2(String password2) {
        this.password2 = DigestUtils.sha256Hex(password2);
    }

    /**
     *
     * @return
     */
    public String validaNombre() {
        ManagerUsuario manager = new ManagerUsuario();
        if (manager.nombreExiste(this.nombre)) {
            return "/restringido/mensajes/err/nombre1";
        }
        String uid = manager.getUserId(UtilidadHTTP.obtenUsuario());
        return this.actualizaNombre(uid, this.nombre);
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public String validaEmail() {
        if (!this.email.contains("@ciencias.unam.mx")) {
            return "/restringido/mensajes/err/email1";
        }
        ManagerUsuario manager = new ManagerUsuario();
        String uid = manager.getUserId(UtilidadHTTP.obtenUsuario());
        return this.actualizaEmail(uid, this.email);
    }

    /**
     *
     * @param uid
     * @param nuevo
     * @return
     */
    public String actualizaNombre(String uid, String nuevo) {
        ManagerUsuario manager = new ManagerUsuario();
        String queryResult = manager.editarNombre(uid, nuevo);
        if (queryResult == "SUCCESS") {
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

    /**
     *
     * @param uid
     * @param nuevo
     * @return
     */
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

    /**
     *
     * @param uid
     * @param nuevo
     * @return
     */
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

    /**
     *
     * @param e
     * @throws IOException
     */
    public void actualizaFoto(FileUploadEvent e) throws IOException {
        e.getComponent().setTransient(false);
        ManagerUsuario manager = new ManagerUsuario();
        if (manager.editaFoto(UtilidadHTTP.obtenerIdUsuario(), IOUtils.toByteArray(e.getFile().getInputstream()))) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!",
                            "Se cambio la foto."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                            "No se pudo cambiar la foto."));
        }
    }

    /**
     *
     */
    public void borraFoto() {
        ManagerUsuario manager = new ManagerUsuario();
        if (manager.editaFoto(UtilidadHTTP.obtenerIdUsuario(), null)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!",
                            "Se borro la foto."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                            "No se pudo borrar la foto."));
        }
    }
}
