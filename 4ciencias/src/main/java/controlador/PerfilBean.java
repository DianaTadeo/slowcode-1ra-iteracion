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
 * @author jantoniolr
 */


@ManagedBean(name = "perfilBean")
@SessionScoped
public class PerfilBean implements Serializable {
    public String fetchNombre() {
        return UtilidadHTTP.obtenUsuario();
    }

    public String fetchPasswordLength() {
        String nombre = UtilidadHTTP.obtenUsuario();
        ManagerU manager = new ManagerU();
        int len = manager.getUserPwdLength(nombre);
        String res = "";
        while (len > 0) {
            res += "*";
            len--;
        }
        return res;
    }

    public String fetchEmail() {
        String nombre = UtilidadHTTP.obtenUsuario();
        ManagerU manager = new ManagerU();
        return manager.getUserEmail(nombre);
    }
}
