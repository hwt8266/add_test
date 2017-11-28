/**
 * 
 */
package com.siemens.ct.pes.powerload.auth.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for Authentication
 * 
 * @author Hao Liu
 *
 */
public class AuthDTO {

    private String username;

    private String password;

    /**
     * Default constructor
     */
    public AuthDTO() {
        username = CommonDefine.EMPTY;
        password = CommonDefine.EMPTY;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}