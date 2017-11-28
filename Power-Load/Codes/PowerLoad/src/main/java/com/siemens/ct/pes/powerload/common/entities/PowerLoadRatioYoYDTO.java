/**
 * 
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for query power load radio year on year
 * 
 * @author Hao Liu
 */
public class PowerLoadRatioYoYDTO {

    private int id;

    private String name;

    private BigDecimal capacity;

    private String impactName;

    private BigDecimal impactValue;

    private List<LinePointDTO> lastYearLine;

    private List<LinePointDTO> thisYearLine;

    /**
     * Default constructor
     */
    public PowerLoadRatioYoYDTO() {
        id = CommonDefine.INIT_VALUE;
        name = CommonDefine.EMPTY;
        capacity = new BigDecimal(0);
        impactName = CommonDefine.EMPTY;
        impactValue = new BigDecimal(CommonDefine.INIT_VALUE);
        lastYearLine = new ArrayList<LinePointDTO>();
        thisYearLine = new ArrayList<LinePointDTO>();
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
     * @return the impactName
     */
    public String getImpactName() {
        return impactName;
    }

    /**
     * @param impactName
     *            the impactName to set
     */
    public void setImpactName(String impactName) {
        this.impactName = impactName;
    }

    /**
     * @return the impactValue
     */
    public BigDecimal getImpactValue() {
        return impactValue;
    }

    /**
     * @param impactValue
     *            the impactValue to set
     */
    public void setImpactValue(BigDecimal impactValue) {
        this.impactValue = impactValue;
    }

    /**
     * @return the lastYearLine
     */
    public List<LinePointDTO> getLastYearLine() {
        return lastYearLine;
    }

    /**
     * @param lastYearLine
     *            the lastYearLine to set
     */
    public void setLastYearLine(List<LinePointDTO> lastYearLine) {
        this.lastYearLine = lastYearLine;
    }

    /**
     * @return the thisYearLine
     */
    public List<LinePointDTO> getThisYearLine() {
        return thisYearLine;
    }

    /**
     * @param thisYearLine
     *            the thisYearLine to set
     */
    public void setThisYearLine(List<LinePointDTO> thisYearLine) {
        this.thisYearLine = thisYearLine;
    }
}