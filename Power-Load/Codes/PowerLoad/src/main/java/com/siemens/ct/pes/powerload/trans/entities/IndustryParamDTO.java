/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for industry and development level parameter
 * 
 * @author Hao Liu
 *
 */
public class IndustryParamDTO {

    private int industryID;

    private int devLevel;

    /**
     * Default constructor
     */
    public IndustryParamDTO() {
        industryID = CommonDefine.INIT_VALUE;
        devLevel = CommonDefine.INIT_VALUE;
    }

    /**
     * @return the industryID
     */
    public int getIndustryID() {
        return industryID;
    }

    /**
     * @param industryID
     *            the industryID to set
     */
    public void setIndustryID(int industryID) {
        this.industryID = industryID;
    }

    /**
     * @return the devLevel
     */
    public int getDevLevel() {
        return devLevel;
    }

    /**
     * @param devLevel
     *            the devLevel to set
     */
    public void setDevLevel(int devLevel) {
        this.devLevel = devLevel;
    }
}