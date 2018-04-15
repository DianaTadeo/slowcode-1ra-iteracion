/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author palmerin
 */
@ManagedBean(name = "categoriaBean")
@Dependent
public class CategoriaBean {

    private int id;
    private String nombre;
    private List categorias;
    
    /**
     * Creates a new instance of CategoriaBean
     */
    public CategoriaBean() {        
    }
    
    public void setCategorias(List categorias) {
        this.categorias = categorias;
    }
    
    public List getCategorias() {
        ManagerCategoria manager = new ManagerCategoria();
        categorias = manager.getCategorias();
        return categorias;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
}
