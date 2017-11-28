/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load forecast by default
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadDefaultFCEntityDTO {

    /**
     * Transformer ID
     */
    private int id;

    /**
     * Transformer name
     */
    private String name;

    /**
     * Industry user count data
     */
    private List<IndustryUserCountDTO> industryUserData;

    /**
     * Power load forecast line by default(3 lines)
     */
    private List<PowerLoadFCLineDTO> defaultFCLine;

    /**
     * Default constructor
     */
    public PowerLoadDefaultFCEntityDTO() {
        id = CommonDefine.INIT_VALUE;
        name = CommonDefine.EMPTY;
        industryUserData = new ArrayList<IndustryUserCountDTO>();
        defaultFCLine = new ArrayList<PowerLoadFCLineDTO>();
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
     * @return the industryUserData
     */
    public List<IndustryUserCountDTO> getIndustryUserData() {
        return industryUserData;
    }

    /**
     * @param industryUserData
     *            the industryUserData to set
     */
    public void setIndustryUserData(List<IndustryUserCountDTO> industryUserData) {
        this.industryUserData = industryUserData;
    }

    /**
     * @return the defaultFCLine
     */
    public List<PowerLoadFCLineDTO> getDefaultFCLine() {
        return defaultFCLine;
    }

    /**
     * @param defaultFCLine
     *            the defaultFCLine to set
     */
    public void setDefaultFCLine(List<PowerLoadFCLineDTO> defaultFCLine) {
        this.defaultFCLine = defaultFCLine;
    }
}