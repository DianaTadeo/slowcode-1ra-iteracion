/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Pregunta;

/**
 *
 * @author rrios
 */
@ManagedBean(name = "generalBean")
@SessionScoped
public class GeneralBean implements Serializable {

    private List<Pregunta> top; 
    
    public List<Pregunta> dameTop() {
        return new ManagerPregunta().todas();
    }
    
    public List<Pregunta> getTop() {
        return top;
    }

    public void setTop(List<Pregunta> top) {
        this.top = top;
    }
}
