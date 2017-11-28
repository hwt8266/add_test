package com.siemens.ct.pes.powerload.auth.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
        @NamedQuery(name = "Account.findByName", query = "SELECT a FROM Account a WHERE a.username = :name") })
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    private String password;

    public Account() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}