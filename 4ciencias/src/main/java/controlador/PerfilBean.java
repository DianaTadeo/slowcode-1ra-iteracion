/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jantoniolr
 */
@ManagedBean(name = "perfilBean")
@SessionScoped
public class PerfilBean implements Serializable {

    /**
     *
     * @return
     */
    public String fetchNombre() {
        return UtilidadHTTP.obtenUsuario();
    }

    /**
     *
     * @return
     */
    public String fetchPasswordLength() {
        String nombre = UtilidadHTTP.obtenUsuario();
        ManagerUsuario manager = new ManagerUsuario();
        int len = manager.getUserPwdLength(nombre);
        String res = "";
        while (len > 0) {
            res += "*";
            len--;
        }
        return res;
    }

    /**
     *
     * @return
     */
    public String fetchEmail() {
        String nombre = UtilidadHTTP.obtenUsuario();
        ManagerUsuario manager = new ManagerUsuario();
        return manager.getUserEmail(nombre);
    }

    /**
     *
     * @return
     */
    public StreamedContent fetchPhoto() {
        ManagerUsuario manager = new ManagerUsuario();
        byte[] foto = manager.getUserPhoto(UtilidadHTTP.obtenerIdUsuario());
        if (foto != null) {
            return new ByteArrayContent(foto);
        } else {
            return null;
        }
    }

}
