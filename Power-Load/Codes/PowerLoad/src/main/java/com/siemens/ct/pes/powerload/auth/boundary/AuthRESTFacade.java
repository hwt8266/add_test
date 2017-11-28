/**
 * 
 */
package com.siemens.ct.pes.powerload.auth.boundary;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.siemens.ct.pes.powerload.auth.controller.SessionCache;
import com.siemens.ct.pes.powerload.auth.dao.AuthDAO;
import com.siemens.ct.pes.powerload.auth.entities.Account;
import com.siemens.ct.pes.powerload.auth.entities.AuthDTO;

/**
 * RESTful API for authentication
 * 
 * @author Hao Liu
 *
 */
@Path("auth")
public class AuthRESTFacade {

    @EJB
    private AuthDAO dao;

    @POST
    @Path("login")
    public boolean login(@Context HttpServletRequest req, final AuthDTO auth) {
        String name = auth.getUsername();
        String pwd = auth.getPassword();

        Account acc = dao.queryAccountByName(name);

        if (null == acc || !acc.getPassword().equals(pwd)) {
            return false;
        } else {
            String sessionId = req.getSession().getId();
            long time = System.currentTimeMillis();

            SessionCache.getInstance().getCache().put(sessionId, time);

            return true;
        }
    }

    @POST
    @Path("logout")
    public void logout(@Context HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        SessionCache.getInstance().getCache().remove(sessionId);
    }
}