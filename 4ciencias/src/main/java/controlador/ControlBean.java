/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import modelo.Usuario;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rrios
 */
@ManagedBean(name = "controlBean")
@SessionScoped
public class ControlBean implements Serializable {
    
    private List<Usuario> usuarios;

    public List<Usuario> getUsuarios() {
        usuarios = new ManagerUsuario().dameUsuarios();
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public void eliminaUsuario(Usuario u) throws IOException {
        ManagerUsuario manager = new ManagerUsuario();
        if (!manager.borraUsuario(u.getId()))
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                            "No se pudo eliminar al Usuario"));
        else
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!",
                            "Usuario Eliminado"));
    }
    
    public void cambiaEstadoAdmin(Usuario u) {
        ManagerUsuario manager = new ManagerUsuario();
        if (!manager.cambiaValorAdmin(u))
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                            "No se pudieron cambiar los derechos de administrador."));
        else
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Derechos de Administracion cambiados.",
                            "Los cambios se efectuaran en el siguiente inicio de sesión."));
    }
    
    
    
}