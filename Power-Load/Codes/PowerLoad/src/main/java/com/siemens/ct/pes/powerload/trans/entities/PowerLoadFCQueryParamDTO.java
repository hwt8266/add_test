/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for query transformer power load forecast by industry development level
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadFCQueryParamDTO {

    private int id;

    private List<IndustryParamDTO> industryParam;

    /**
     * Default constructor
     */
    public PowerLoadFCQueryParamDTO() {
        id = CommonDefine.INIT_VALUE;
        industryParam = new ArrayList<IndustryParamDTO>();
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
     * @return the industryParam
     */
    public List<IndustryParamDTO> getIndustryParam() {
        return industryParam;
    }

    /**
     * @param industryParam
     *            the industryParam to set
     */
    public void setIndustryParam(List<IndustryParamDTO> industryParam) {
        this.industryParam = industryParam;
    }
}