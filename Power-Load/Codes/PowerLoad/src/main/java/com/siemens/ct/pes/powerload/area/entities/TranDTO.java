/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.math.BigDecimal;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for transformer in area
 * 
 * @author Hao Liu
 *
 */
public class TranDTO {

    private int id;

    private String name;

    private String type;

    private String vLevel;

    private BigDecimal capacity;

    private BigDecimal increase;

    private int lowPeriod;

    private int heavyPeriod;

    private int overPeriod;

    private String deptName;

    /**
     * Default constructor
     */
    public TranDTO() {
	id = CommonDefine.INIT_VALUE;
	name = CommonDefine.EMPTY;
	type = CommonDefine.EMPTY;
	vLevel = CommonDefine.EMPTY;
	capacity = new BigDecimal(CommonDefine.INIT_VALUE);
	increase = new BigDecimal(CommonDefine.INIT_VALUE);
	lowPeriod = CommonDefine.INIT_VALUE;
	heavyPeriod = CommonDefine.INIT_VALUE;
	overPeriod = CommonDefine.INIT_VALUE;
	deptName = CommonDefine.EMPTY;
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
     * @return the type
     */
    public String getType() {
	return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @return the vLevel
     */
    public String getvLevel() {
	return vLevel;
    }

    /**
     * @param vLevel
     *            the vLevel to set
     */
    public void setvLevel(String vLevel) {
	this.vLevel = vLevel;
    }

    /**
     * @return the capacity
     */
    public BigDecimal getCapacity() {
	return capacity;
    }

    /**
     * @param capacity
     *            the capacity to set
     */
    public void setCapacity(BigDecimal capacity) {
	this.capacity = capacity;
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
     * @return the lowPeriod
     */
    public int getLowPeriod() {
	return lowPeriod;
    }

    /**
     * @param lowPeriod
     *            the lowPeriod to set
     */
    public void setLowPeriod(int lowPeriod) {
	this.lowPeriod = lowPeriod;
    }

    /**
     * @return the heavyPeriod
     */
    public int getHeavyPeriod() {
	return heavyPeriod;
    }

    /**
     * @param heavyPeriod
     *            the heavyPeriod to set
     */
    public void setHeavyPeriod(int heavyPeriod) {
	this.heavyPeriod = heavyPeriod;
    }

    /**
     * @return the overPeriod
     */
    public int getOverPeriod() {
	return overPeriod;
    }

    /**
     * @param overPeriod
     *            the overPeriod to set
     */
    public void setOverPeriod(int overPeriod) {
	this.overPeriod = overPeriod;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
	return deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
	this.deptName = deptName;
    }
}