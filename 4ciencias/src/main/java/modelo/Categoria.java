package modelo;
// Generated Apr 10, 2018 7:46:24 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Categoria generated by hbm2java
 */
public class Categoria  implements java.io.Serializable {


     private int id;
     private String nombre;
     private Set preguntas = new HashSet(0);

    public Categoria() {
    }

	
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Categoria(int id, String nombre, Set preguntas) {
       this.id = id;
       this.nombre = nombre;
       this.preguntas = preguntas;
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
    public Set getPreguntas() {
        return this.preguntas;
    }
    
    public void setPreguntas(Set preguntas) {
        this.preguntas = preguntas;
    }




}


