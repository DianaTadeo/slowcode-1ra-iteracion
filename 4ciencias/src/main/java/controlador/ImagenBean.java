/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rrios
 */
@ManagedBean(name = "imagenBean")
@ApplicationScoped
public class ImagenBean implements Serializable {
    
    public StreamedContent getImagen() throws IOException, NumberFormatException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE)
            return new DefaultStreamedContent();
        else {
            String id = context.getExternalContext().getRequestParameterMap().get("imgid");
            // Ya no es fase de rendereo aqui, NO QUITAR (se rompe todo componente command)
            if (id == null)
                return new DefaultStreamedContent();
            byte[] foto = new ManagerUsuario().getUserPhoto(Integer.valueOf(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(foto));
        }
    }
}
