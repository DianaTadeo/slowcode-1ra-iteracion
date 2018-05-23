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

    /**
     * Init.
     *
     * @param fc
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    /**
     * Filtro.
     *
     * @param sr
     * @param sr1
     * @param fc
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse res = (HttpServletResponse) sr1;
        HttpSession ses = req.getSession(false);
        String direccion = req.getRequestURI();
        if (direccion.contains("administrador")) {
            if (ses != null && (boolean) ses.getAttribute("admin")) {
                fc.doFilter(sr, sr1);
            } else {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } else if (direccion.contains("restringido") && !direccion.contains("principal")
                && !direccion.contains("/Pregunta")
                && ses != null && ses.getAttribute("username") == null) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        } else {
            fc.doFilter(sr, sr1);
        }
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
    }
}
