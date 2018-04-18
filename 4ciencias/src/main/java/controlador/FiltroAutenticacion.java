/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rrios
 */
public class FiltroAutenticacion implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) 
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse res = (HttpServletResponse) sr1;
        HttpSession ses = req.getSession(false);
        String direccion = req.getRequestURI();
        if (direccion.contains("/login") || direccion.contains("/publico") ||
            (direccion.contains("/restringido") && ses != null && 
             ses.getAttribute("username") != null))
            fc.doFilter(sr, sr1);
        else
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {
    }
}
