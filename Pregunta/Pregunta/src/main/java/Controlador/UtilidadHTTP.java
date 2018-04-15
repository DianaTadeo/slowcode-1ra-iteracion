/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rrios
 */
public class UtilidadHTTP {
    
    public static HttpSession obtenSesion() {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
       
      public static HttpServletRequest obtenSolicitud() {
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().getRequest();
      }
 
      public static String obtenUsuario() {
        HttpSession session = 
                (HttpSession) FacesContext.getCurrentInstance().
                        getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
      }
       
      public static String getUserId() {
        HttpSession session = obtenSesion();
        if ( session != null )
            return (String) session.getAttribute("userid");
        else
            return null;
      }
}