/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.UsuarioSinValidar;

/**
 *
 * @author emmanuel
 */
@ManagedBean(name = "validaCuentaBean")
@SessionScoped
public class ValidaCuentaBean implements Serializable {

    private int id_validar;

    /**
     *
     * @return
     */
    public int getId_validar() {
        return id_validar;
    }

    /**
     *
     * @param id_validar
     */
    public void setId_validar(int id_validar) {
        this.id_validar = id_validar;
    }

    /**
     *
     * @return
     */
    public String valida_cuenta() {
        ManagerUsuarioSinValidar MUSV = new ManagerUsuarioSinValidar();
        ManagerUsuario MU = new ManagerUsuario();

        UsuarioSinValidar usv = MUSV.obtenUSV(id_validar);
        if (usv == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("No se pudo validar"));
            return "registro";
        } else {
            MUSV.eliminaUSV(usv);
            MU.agregaUsuario(usv.getNombre(), usv.getEmail(), usv.getPassword());
            return "cuenta_validada";
        }
    }
}
