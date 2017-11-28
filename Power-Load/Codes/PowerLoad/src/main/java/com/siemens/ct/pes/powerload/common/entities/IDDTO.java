/**
 * 
 */
package com.siemens.ct.pes.powerload.common.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for id
 * 
 * @author Hao Liu
 *
 */
public class IDDTO {

    private int id;

    /**
     * Default constructor
     */
    public IDDTO() {
        id = CommonDefine.INIT_VALUE;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}