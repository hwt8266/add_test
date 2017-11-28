/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for industry count
 * 
 * @author Hao Liu
 *
 */
public class IndustryUserCountDTO {

    private int industryID;

    private String industryName;

    private int userCount;

    /**
     * Default constructor
     */
    public IndustryUserCountDTO() {
        industryID = CommonDefine.INIT_VALUE;
        industryName = CommonDefine.EMPTY;
        userCount = CommonDefine.INIT_VALUE;
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
     * @return the industryName
     */
    public String getIndustryName() {
        return industryName;
    }

    /**
     * @param industryName
     *            the industryName to set
     */
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    /**
     * @return the userCount
     */
    public int getUserCount() {
        return userCount;
    }

    /**
     * @param userCount
     *            the userCount to set
     */
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}