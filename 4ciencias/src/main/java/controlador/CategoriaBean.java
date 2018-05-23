/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
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

    /**
     *
     * @param categorias
     */
    public void setCategorias(List categorias) {
        this.categorias = categorias;
    }

    /**
     *
     * @return
     */
    public List getCategorias() {
        ManagerCategoria manager = new ManagerCategoria();
        categorias = manager.getCategorias();
        return categorias;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

}
