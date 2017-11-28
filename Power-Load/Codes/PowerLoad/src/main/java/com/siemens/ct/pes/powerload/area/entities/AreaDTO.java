/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.math.BigDecimal;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for area
 * 
 * @author Hao Liu
 *
 */
public class AreaDTO {

    private int id;

    private String name;

    private BigDecimal increase;

    private String remark;

    /**
     * Default constructor
     */
    public AreaDTO() {
	id = CommonDefine.INIT_VALUE;
	name = CommonDefine.EMPTY;
	increase = new BigDecimal(0);
	remark = CommonDefine.EMPTY;
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
     * @return the increase
     */
    public BigDecimal getIncrease() {
	return increase;
    }

    /**
     * @param increase
     *            the increase to set
     */
    public void setIncrease(BigDecimal increase) {
	this.increase = increase;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
	return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
	this.remark = remark;
    }
}