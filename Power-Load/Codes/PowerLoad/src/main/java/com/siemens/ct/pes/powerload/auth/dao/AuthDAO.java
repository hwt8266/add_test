/**
 * 
 */
package com.siemens.ct.pes.powerload.auth.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ct.pes.powerload.auth.entities.Account;

/**
 * DAO for authentication
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class AuthDAO {

    @PersistenceContext(unitName = "siemens.ct.pes.powerload")
    private EntityManager em;

    /**
     * Query account entity by username
     * 
     * @param username
     * @return
     */
    public Account queryAccountByName(final String username) {
        return em.createNamedQuery("Account.findByName", Account.class).setParameter("name", username)
                .getSingleResult();
    }
}