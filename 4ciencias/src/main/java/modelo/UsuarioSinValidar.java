package modelo;
// Generated Apr 10, 2018 8:32:22 PM by Hibernate Tools 4.3.1



/**
 * UsuarioSinValidar generated by hbm2java
 */
public class UsuarioSinValidar  implements java.io.Serializable {


     private int id;
     private String nombre;
     private String email;
     private String password;

    public UsuarioSinValidar() {
    }

    public UsuarioSinValidar(int id, String nombre, String email, String password) {
       this.id = id;
       this.nombre = nombre;
       this.email = email;
       this.password = password;
    }
    
    public UsuarioSinValidar(String nombre, String email, String password){
        this.nombre = nombre;
        this.email = email;
        this.password = password;
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
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }




}


