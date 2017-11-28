/**
 * 
 */
package com.siemens.ct.pes.powerload.vip.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * Industry cluster category entity
 * 
 * @author Hao Liu
 *
 */
public class IndustryClusterCategory {

    private String name;

    /**
     * Default constructor
     */
    public IndustryClusterCategory() {
        name = CommonDefine.EMPTY;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}