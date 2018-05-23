/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rrios
 */
public class UtilidadHTTP {

    /**
     *
     * @return
     */
    public static HttpSession obtenSesion() {
        return (HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
    }

    /**
     *
     * @return
     */
    public static HttpServletRequest obtenSolicitud() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }

    /**
     *
     * @return
     */
    public static String obtenUsuario() {
        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().
                        getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    /**
     *
     * @return
     */
    public static String getUserId() {
        HttpSession session = obtenSesion();
        if (session != null) {
            return (String) session.getAttribute("userid");
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public static Integer obtenerIdUsuario() {
        HttpSession session = obtenSesion();
        if (session != null) {
            return (Integer) session.getAttribute("userid");
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public static boolean esAdmin() {
        HttpSession session = obtenSesion();
        if (session != null) {
            return (boolean) session.getAttribute("admin");
        } else {
            return false;
        }
    }
}
