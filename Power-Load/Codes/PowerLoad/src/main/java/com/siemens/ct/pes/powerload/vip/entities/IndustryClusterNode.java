/**
 * 
 */
package com.siemens.ct.pes.powerload.vip.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * Industry cluster node entity
 * 
 * @author Hao Liu
 *
 */
public class IndustryClusterNode {

    private String name;

    private int value;

    private int category;

    /**
     * Default constructor
     */
    public IndustryClusterNode() {
        name = CommonDefine.EMPTY;
        value = CommonDefine.INIT_VALUE;
        category = CommonDefine.INIT_VALUE;
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

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the category
     */
    public int getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(int category) {
        this.category = category;
    }
}