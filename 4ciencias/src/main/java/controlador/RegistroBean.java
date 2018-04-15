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
 * @author emmanuel
 */
@ManagedBean(name = "registroBean")
@SessionScoped
public class RegistroBean implements Serializable {

    private String nombre, email, password, conf_password;
    /**
     * Creates a new instance of RegistroBean
     */
    public RegistroBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConf_password() {
        return conf_password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    public void setConf_password(String conf_password) {
        this.conf_password = DigestUtils.sha256Hex(conf_password);
    }

    public String registraUSV() {
        ManagerUsuarioSinValidar MUSV = new ManagerUsuarioSinValidar();
        ManagerUsuario MU = new ManagerUsuario();

        if (!MUSV.comparaPass(password, conf_password)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Las contraseñas no coinciden"));
            return "registro";
        } else if(password.equals(DigestUtils.sha256Hex(""))){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("La contraseña no puede quedar vacia"));
            return "registro";
        } else if (MUSV.formateaEmail(email) == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("El correo proporcionado no pertenece "
                            + "al dominio @ciencias.unam.mx"));
            return "registro";
        } else if (MUSV.existeUSV(email) >= 0 || MU.existeUsuario(email) >= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("El correo proporcionado ya está "
                            + "registrado en el sistema"));
            return "registro";
        } else {
            int id_USV = MUSV.agregaUSV(nombre, email, password, conf_password);
            if (id_USV >= 0) {
                EmailSender.mandaValidacion(email, id_USV);
                return "valida_correo";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Datos incorrectos. Por favor intente "
                                + "nuevamente"));
                return "registro";
            }
        }
    }
}
