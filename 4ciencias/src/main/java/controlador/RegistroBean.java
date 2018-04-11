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
    
    public String registraUSV(){
        ManagerUsuarioSinValidar MUSV = new ManagerUsuarioSinValidar();
        //password = DigestUtils.sha256Hex(password);
        //conf_password = DigestUtils.sha256Hex(conf_password);
        if(MUSV.agregaUSV(nombre, email, password, conf_password) >= 0){
            return "valida_correo";
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Vales verga krnal"));
            return "registro";
        }
    }
}
