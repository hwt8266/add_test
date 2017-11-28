/**
 * 
 */
package com.siemens.ct.pes.powerload.auth.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for authentication
 * 
 * @author Hao Liu
 *
 */
public class AuthenticationFilter implements Filter {

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String requestUri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String url = requestUri.substring(contextPath.length());

        if (url.endsWith("api/auth/login") || url.endsWith("api/auth/logout") || url.endsWith("login.html")
                || url.endsWith(".css") || url.contains("/img/")) {
            chain.doFilter(request, response);
            return;
        } else {
            String sessionId = req.getSession().getId();
            if (null == SessionCache.getInstance().getCache().get(sessionId)) {
                // request.getRequestDispatcher("/views/login.html").forward(request, response);
                ((HttpServletResponse) response).sendRedirect(contextPath);
            } else {
                long curTime = System.currentTimeMillis();
                // long lastTime = SessionCache.getInstance().getCache().get(sessionId);

                // // Timeout clear session cache and logout
                // if ((curTime - lastTime) > 1000) {
                // SessionCache.getInstance().getCache().remove(sessionId);
                // // request.getRequestDispatcher("/views/login.html").forward(request, response);
                // ((HttpServletResponse) response).sendRedirect(contextPath);
                // } else {
                // Update session last operate time
                SessionCache.getInstance().getCache().put(sessionId, curTime);
                chain.doFilter(request, response);
                // }
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }
}