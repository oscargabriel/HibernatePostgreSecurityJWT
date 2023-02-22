package com.example.HibernatePostgreSecurityJWT.security.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filtro para las caveceras de los mensajes
 */
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            //establece desde donde se puede acceder
            response.setHeader("Access-Control-Allow-Origin", "*");
            //establece las redenciales para permitir los llamdos
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //tipos de solicitudes que se pueden hacer
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            //
            response.setHeader("Access-Control-Max-Age", "3600");
            //
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");

            chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
