/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import modelo.HibernateUtil;
import modelo.Usuario;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author rrios
 */
public class ManagerUsuario {

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public Usuario dameUsuario(String email, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "FROM Usuario U where U.email = '" + email
                + "' AND U.password = '" + password + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (HibernateException he) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            sesion.close();
        }
        if (resultado != null && !resultado.isEmpty()) {
            return (Usuario) resultado.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @param nombre
     * @param password
     * @return
     */
    public boolean exists(String nombre, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.id FROM Usuario U where U.nombre = '" + nombre
                + "' AND U.password = '" + password + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (HibernateException he) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            sesion.close();
        }
        return (resultado != null && !resultado.isEmpty());
    }

    /**
     *
     * @param nombre
     * @return
     */
    public boolean nombreExiste(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.id FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (HibernateException he) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            sesion.close();
        }
        return (resultado != null && !resultado.isEmpty());
    }

    /**
     *
     * @param uid
     * @param nombre
     * @return
     */
    public String editarNombre(String uid, String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "UPDATE Usuario U SET U.nombre = '" + nombre + "' WHERE U.id = " + uid;
        int resultado = 0;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            return e.toString();
        } finally {
            sesion.close();
        }
        return "SUCCESS";
    }

    /**
     *
     * @param uid
     * @param password
     * @return
     */
    public String editarPassword(String uid, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "UPDATE Usuario U SET U.password = '" + password + "' WHERE U.id = " + uid;
        int resultado = 0;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            return e.toString();
        } finally {
            sesion.close();
        }
        return "SUCCESS";
    }

    /**
     *
     * @param uid
     * @param email
     * @return
     */
    public String editarEmail(String uid, String email) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "UPDATE Usuario U SET U.email = '" + email + "' WHERE U.id = " + uid;
        int resultado = 0;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            return e.toString();
        } finally {
            sesion.close();
        }
        return "SUCCESS";
    }

    /**
     *
     * @param nombre
     * @return
     */
    public String getUserId(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.id FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            return e.toString();
        } finally {
            sesion.close();
        }
        return (resultado.get(0).toString());
    }

    /**
     *
     * @param nombre
     * @return
     */
    public int getUserPwdLength(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.password FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            return -1;
        } finally {
            sesion.close();
        }
        return resultado.get(0).toString().length();
    }

    /**
     *
     * @param nombre
     * @return
     */
    public String getUserEmail(String nombre) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "Select U.email FROM Usuario U where U.nombre = '" + nombre + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            return e.toString();
        } finally {
            sesion.close();
        }
        return resultado.get(0).toString();
    }

    /**
     *
     * @param id
     * @return
     */
    public byte[] getUserPhoto(Integer id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        String hql = "FROM Usuario U where U.id = '" + id.toString() + "'";
        List resultado = null;
        try {
            tr = sesion.beginTransaction();
            resultado = sesion.createQuery(hql).list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            sesion.close();
        }
        if (resultado != null && !resultado.isEmpty()) {
            return ((Usuario) resultado.get(0)).getFoto();
        } else {
            return null;
        }
    }

    /**
     *
     * @param email
     * @return
     */
    public int existeUsuario(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List resultado = null;
        int id = 0;

        try {
            tx = session.beginTransaction();
            String query = "from Usuario U where U.email = '" + email + "'";
            resultado = session.createQuery(query).list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        if (resultado == null || resultado.isEmpty()) {
            return -1;
        }

        id = ((Usuario) resultado.get(0)).getId();
        return id;
    }

    /**
     *
     * @param nombre
     * @param email
     * @param password
     * @return
     */
    public int agregaUsuario(String nombre, String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer usuario_id = -1;

        try {
            tx = session.beginTransaction();
            //Esto está mal
            Usuario usuario = new Usuario(nombre, email, password,
                    new Date(System.currentTimeMillis()), false);
            usuario_id = (Integer) session.save(usuario);
            tx.commit();

        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return usuario_id;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean borraUsuario(Integer id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean resultado = true;
        String hql = "DELETE FROM Usuario U where U.id = '" + id.toString() + "'";
        try {
            tx = sesion.beginTransaction();
            sesion.createQuery(hql).executeUpdate();
            tx.commit();
        } catch (HibernateException he) {
            resultado = false;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            sesion.close();
        }
        return resultado;
    }

    /**
     *
     * @param usr
     * @return
     */
    public boolean cambiaValorAdmin(Usuario usr) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean resultado = true;
        String hql = "UPDATE Usuario SET es_admin = '" + String.valueOf(!usr.isEsAdmin())
                + "' WHERE id = '" + String.valueOf(usr.getId()) + "'";
        try {
            tx = sesion.beginTransaction();
            sesion.createQuery(hql).executeUpdate();
            tx.commit();
        } catch (HibernateException he) {
            resultado = false;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            sesion.close();
        }
        return resultado;
    }

    /**
     *
     * @return
     */
    public List<Usuario> dameUsuarios() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Usuario> usuarios = new LinkedList<>();
        try {
            tx = sesion.beginTransaction();
            Criteria cr = sesion.createCriteria(Usuario.class);
            cr.addOrder(Order.asc("nombre"));
            usuarios = cr.list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            sesion.close();
        }
        return usuarios;
    }

    /**
     *
     * @param id
     * @param imagen
     * @return
     */
    public boolean editaFoto(Integer id, byte[] imagen) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean resultado = true;
        try {
            tx = session.beginTransaction();
            Usuario usuario = (Usuario) session.get(Usuario.class, id);
            usuario.setFoto(imagen);
            session.update(usuario);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }
}
