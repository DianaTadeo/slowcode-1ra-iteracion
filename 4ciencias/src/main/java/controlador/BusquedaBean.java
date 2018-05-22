/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Pregunta;
import modelo.Usuario;

/**
 *
 * @author rrios
 */
@ManagedBean(name = "busquedaBean")
@SessionScoped
public class BusquedaBean {
    private String contenido = "";
    private List<Pregunta> resultados;

    public List<Pregunta> getResultados() {
        return resultados;
    }

    public void setResultados(List<Pregunta> resultados) {
        this.resultados = resultados;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public void cargaResultados() {
        ManagerPregunta mp = new ManagerPregunta();
        resultados = mp.getPreguntas(contenido);        
    }
    
}
