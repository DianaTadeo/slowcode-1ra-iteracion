package modelo;
// Generated May 8, 2018 9:46:55 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Carrera generated by hbm2java
 */
public class Carrera  implements java.io.Serializable {


     private int id;
     private String nombre;
     private Set usuarios = new HashSet(0);

    public Carrera() {
    }

	
    public Carrera(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Carrera(int id, String nombre, Set usuarios) {
       this.id = id;
       this.nombre = nombre;
       this.usuarios = usuarios;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }




}


