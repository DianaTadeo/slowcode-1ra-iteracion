/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.HibernateUtil;
import modelo.UsuarioSinValidar;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;

/**
 *
 * @author emmanuel
 */
public class ManagerUsuarioSinValidar {

    static int nombre_max_size = 100, email_max_size = 100;

    //Inserta una fila a UsuarioSinValidar
    //Regresa el ID del nuevo USV
    private int insertaUSV(String nombre, String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer usv_id = -1;

        try {
            tx = session.beginTransaction();
            UsuarioSinValidar usv = new UsuarioSinValidar(nombre, email, password);
            usv_id = (Integer) session.save(usv);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return usv_id;
    }

    public int agregaUSV(String nombre, String email, String password,
            String conf_password) {
        if (!comparaPass(password, conf_password)) {
            return -1;
        }
        email = formateaEmail(email);
        if (email == null) {
            return -1;
        }
        if (nombre.length() > nombre_max_size || email.length() > email_max_size) {
            return -1;
        }
        ManagerUsuario MU = new ManagerUsuario();
        if (existeUSV(email) >= 0 || MU.existeUsuario(email) >= 0) {
            return -1;
        }

        return insertaUSV(nombre, email, password);
    }

    //Checa si el correo especificado ya existe en la tabla. Si existe devuelve
    //su ID, si no, devuelve -1
    public int existeUSV(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List resultado = null;
        int id = 0;

        try {
            tx = session.beginTransaction();
            String query = "from UsuarioSinValidar USV where"
                    + "USV.email = '" + email + "'";
            resultado = session.createQuery(query).list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        if(resultado == null || resultado.isEmpty()) return -1;
        
        id = ((UsuarioSinValidar) resultado.get(0)).getId();
        return id;
    }

    //Intenta dar un formato correcto al correo proporcionado. Si no es parte
    //del dominio @ciencias.unam.mx devuelve null
    public String formateaEmail(String email) {
        email = email.trim();
        if (email.endsWith("@ciencias.unam.mx")) {
            return email;
        } else {
            return null;
        }
    }
    
    public boolean comparaPass(String password1, String password2){
        return password1.equals(password2);
    }
}
