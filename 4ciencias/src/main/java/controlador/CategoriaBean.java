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
     * Asigna una lista de categorías.
     * @param categorias 
     */
    public void setCategorias(List categorias) {
        this.categorias = categorias;
    }
    
    /**
     * Obtiene la lista de categorías almacenada.
     * @return lista de categorías.
     */
    public List getCategorias() {
        ManagerCategoria manager = new ManagerCategoria();
        categorias = manager.getCategorias();
        return categorias;
    }
    
    /**
     * Asigna el id de la categoría.
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Asigna el nombre de la categoría.
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el id de la categoría.
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene el nombre de la categoría.
     * @return 
     */
    public String getNombre() {
        return nombre;
    }
    
}
